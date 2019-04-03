package org.open.budget.costlocator.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.open.budget.costlocator.api.AddressAPI;
import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.entity.*;
import org.open.budget.costlocator.mapper.TenderMapperAPI;
import org.open.budget.costlocator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TenderServiceImpl implements TenderService {

    @Autowired
    private TenderRepository tenderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private StreetRepository streetRepository;
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private TenderIssuerRepository tenderIssuerRepository;
    @Autowired
    private ListPathRepository listPathRepository;
    @Autowired
    private UnsuccessfulItemRepository unsuccessfulItemRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private TenderDetailRepository tenderDetailRepository;


    @Override
    @Transactional
    public Tender save(TenderAPI tenderAPI) {
        if (tenderAPI.getStatus().startsWith("active") &&
                (!tenderAPI.getProcurementMethod().equals("reporting") ||
                        !tenderAPI.getProcurementMethod().equals("belowThreshold"))) {
            log.warn("Tender is in active status {} will be saved to unsuccessful ", tenderAPI.getId());
            unsuccessfulItemRepository.save(new UnsuccessfulItem(tenderAPI.getId(), true));
            return null;
        }
        Tender tender = TenderMapperAPI.INSTANCE.tenderApiToTender(tenderAPI);
        saveAddresses(tenderAPI, tender);
        if (tender.getAddresses().isEmpty()) {
            log.warn("Could not find address for {} will be saved to unsuccessful ", tenderAPI.getId());
            unsuccessfulItemRepository.save(new UnsuccessfulItem(tenderAPI.getId(), false));
            return null;
        }
        saveTenderIssuer(tender);
        saveClassification(tender);
        saveTenderDetail(tender);
        return tenderRepository.save(tender);
    }

    private void saveAddresses(TenderAPI tenderAPI, Tender tender) {
        Set<Address> addresses = findOrCreateAddresses(tenderAPI.getItemAPIS().get(0).getDeliveryAddress());
        if (addresses.isEmpty())
            addresses = findOrCreateAddresses(tenderAPI.getIssuer().getAddress());
        tender.getAddresses().addAll(addresses);
    }

    private Set<Address> findOrCreateAddresses(AddressAPI addressAPI) {
        Collection<Street> potentialStreets = getPotentialStreets(addressAPI);
        return getAdddresses(addressAPI, potentialStreets);
    }

    Collection<Street> getPotentialStreets(AddressAPI addressAPI) {
        Optional<String> index = findInAddressAPI(addressAPI, this::getValidPostIndex);
        Set<Street> potentialStreets = new HashSet<>();
        if (index.isPresent()) {
            potentialStreets.addAll(getStreetsByIndex(index.get()));
        }
        Optional<Region> region = findInAddressAPI(addressAPI, this::getValidRegionName);
        if (region.isPresent()) {
            potentialStreets.addAll(getStreetsOfRegion(region.get()));
        }
        return potentialStreets;
    }

    @Cacheable("streetsByIndex")
    Collection<Street> getStreetsByIndex(String index) {
        return streetRepository.findByIndex(index);
    }

    @Cacheable("streetsByRegion")
    Collection<Street> getStreetsOfRegion(Region region) {
        return streetRepository.findByRegion(region);
    }

    Optional<Region> getValidRegionName(String regionRaw) {
        for (Region region : getRegions()) {
            if (regionRaw.toLowerCase().contains(region.getName()))
                return Optional.of(region);
        }
        return Optional.empty();
    }

    @Cacheable("regions")
    Collection<Region> getRegions() {
        return regionRepository.findAll();
    }

    <T> Optional<T> findInAddressAPI(AddressAPI addressAPI, Function<String, Optional<T>> function) {
        Optional<T> result = function.apply(addressAPI.getPostalCode());
        if (result.isPresent())
            return result;
        result = function.apply(addressAPI.getRegion());
        if (result.isPresent())
            return result;
        result = function.apply(addressAPI.getLocality());
        if (result.isPresent())
            return result;
        result = function.apply(addressAPI.getStreetAddress());
        if (result.isPresent())
            return result;
        return Optional.empty();
    }

    Set<Address> getAdddresses(AddressAPI addressAPI, Collection<Street> potentialStreets) {
        String[] splitedBySemiColumn = addressAPI.getStreetAddress().split(";");
        Set<Address> addresses = new HashSet<>();
        for (String streetAddress : splitedBySemiColumn) {
            String[] splitedByComma = streetAddress.split(",");
            Address.AddressBuilder addressBuilder = null;
            for (int i = 0; i < splitedByComma.length; i++) {
                String splitedByCommaText = splitedByComma[i];
                Optional<Street> foundStreet = findStreet(splitedByCommaText, potentialStreets);

                if (foundStreet.isPresent()) {
                    if (addressBuilder != null)
                        addresses.add(saveAddressIfNeeded(addressBuilder.houseNumber("n/a").build()));

                    addressBuilder = Address.builder();

                    addressBuilder.street(foundStreet.get());
                    addressBuilder.city(foundStreet.get().getCity());
                    if (i == splitedByComma.length - 1) {
                        addresses.add(saveAddressIfNeeded(addressBuilder.houseNumber("n/a").build()));
                    }
                } else {
                    if (addressBuilder != null) {
                        addressBuilder.houseNumber(getValidHouseNumber(splitedByCommaText));
                        addresses.add(saveAddressIfNeeded(addressBuilder.build()));
                        addressBuilder = null;
                    }
                }
            }
        }
        if (addresses.isEmpty()) {
            Collection<City> foundCities = potentialStreets.stream().map(street -> findInAddressAPI(addressAPI, s -> {
                if (s.contains(street.getCity().getName()))
                    return Optional.of(street.getCity());
                return Optional.empty();
            })).filter(o -> o.isPresent()).map(Optional::get).collect(Collectors.toSet());

            for (City city : foundCities) {
                Street street = saveIfNeeded(Street.builder().city(city).index("n/a").name("n/a").fullName("n/a").build(), city);
                Address.AddressBuilder addressBuilder = Address.builder().city(city).street(street).houseNumber("n/a");
                addresses.add(saveAddressIfNeeded(addressBuilder.build()));
            }
        }
        return addresses;
    }

    Optional<Street> findStreet(String splitedByCommaText, Collection<Street> potentialStreets) {
        Street foundStreet = null;
        splitedByCommaText = splitedByCommaText.toLowerCase();
        for (Street iterStreet : potentialStreets) {
            if (splitedByCommaText.contains(iterStreet.getName().toLowerCase())) {
                foundStreet = iterStreet;
                if (isSimillar(splitedByCommaText, iterStreet.getFullName())) {
                    return Optional.of(foundStreet);
                }
            }
        }
        return Optional.ofNullable(foundStreet);
    }

    boolean isSimillar(String left, String right) {
        float difference = LevenshteinDistance.getDefaultInstance()
                .apply(left.toLowerCase(), right.toLowerCase());
        return ((((float) left.length()) - difference) / ((float) left.length()) * 100) > 75;
    }

    Address saveAddressIfNeeded(Address address) {
        Optional<Address> addressFromDB;
        addressFromDB = addressRepository.find(address.getCity().getId(), address.getStreet().getId(), address.getHouseNumber());

        if (addressFromDB.isPresent())
            return addressFromDB.get();
        return addressRepository.save(address);
    }

    Optional<String> getValidPostIndex(String index) {
        if (index == null)
            return Optional.empty();
        Pattern p = Pattern.compile("((?!0000)(?!00000)\\d{4,5})");
        Matcher m = p.matcher(index);
        if (!m.find())
            return Optional.empty();
        return Optional.of(m.group(1));
    }

    String getValidHouseNumber(String text) {
        String[] splited = text.trim().split(" ");
        return splited[0].toLowerCase();
    }

    Street saveIfNeeded(Street street, City city) {
        Optional<Street> streetFromDB = streetRepository.find(city, street.getName(), street.getFullName(), street.getIndex());
        if (!streetFromDB.isPresent()) {
            street = Street.builder().name(street.getName()).index(street.getIndex()).city(city).build();
            return streetRepository.save(street);
        }
        return streetFromDB.get();
    }

    private void saveTenderIssuer(Tender tender) {
        TenderIssuer tenderIssuer = tender.getIssuer();
        Optional<TenderIssuer> tenderIssuerFromDb = tenderIssuerRepository.findById(getCorrectIssuerID(tenderIssuer.getId()));
        if (!tenderIssuerFromDb.isPresent()) {
            formalize(tenderIssuer);
            tenderIssuerRepository.save(tenderIssuer);
        } else {
            tender.setIssuer(tenderIssuerFromDb.get());
        }
    }

    String getCorrectIssuerID(String text) {
        if (text.length() == 8)
            return text;
        String result = null;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != '0') {
                result = text.substring(i);
                break;
            }
        }
        if (result == null || result.length() > 8)
            throw new IllegalStateException("Tender issuer ID can`t be bigger then 8 : " + text);
        return ("00000000" + result).substring(result.length());
    }

    void formalize(TenderIssuer tenderIssuer) {
        String legalName = tenderIssuer.getLegalName();
        if (legalName.length() > 500) {
            tenderIssuer.setLegalName(legalName.substring(0, 489));
        }
        tenderIssuer.setId(getCorrectIssuerID(tenderIssuer.getId()));
    }

    void saveClassification(Tender tender) {
        Classification classification = tender.getClassification();
        Optional<Classification> classificationFromDB = classificationRepository.findById(classification.getId());
        if (!classificationFromDB.isPresent()) {
            classificationRepository.save(classification);
        } else {
            tender.setClassification(classificationFromDB.get());
        }
    }

    void saveTenderDetail(Tender tender) {
        TenderDetail tenderDetail = tender.getTenderDetail();
        Optional<TenderDetail> tenderDetailFromDB = tenderDetailRepository.findById(tenderDetail.getId());
        if (!tenderDetailFromDB.isPresent()) {
            tenderDetailRepository.save(tenderDetail);
        } else {
            tender.setTenderDetail(tenderDetailFromDB.get());
        }
    }

    @Override
    public Map<String, Tender> search(SearchCriteria searchCriteria) {
        return tenderRepository.findByIds(searchCriteria.getTenderIds());
    }

    @Override
    @Transactional
    public String getLastListPath() {
        ListPath listPath = listPathRepository.getOne(1L);
        if (listPath == null)
            throw new IllegalStateException("there is no last path set in DB");
        return listPath.getLastPath();
    }

    @Override
    @Transactional
    public ListPath save(String path) {
        return listPathRepository.save(new ListPath(1L, path));
    }

    @Override
    public List<UnsuccessfulItem> getAllUnsuccessful() {
        return unsuccessfulItemRepository.findAll();
    }
}
