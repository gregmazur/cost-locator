package org.open.budget.costlocator.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.api.AddressAPI;
import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.entity.*;
import org.open.budget.costlocator.mapper.TenderMapperAPI;
import org.open.budget.costlocator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Builder
@Slf4j
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

    private List<Region> regions;

    public TenderServiceImpl(TenderRepository tenderRepository, AddressRepository addressRepository,
                             StreetRepository streetRepository, ClassificationRepository classificationRepository,
                             TenderIssuerRepository tenderIssuerRepository, ListPathRepository listPathRepository,
                             UnsuccessfulItemRepository unsuccessfulItemRepository, RegionRepository regionRepository,
                             TenderDetailRepository tenderDetailRepository, List<Region> regions) {
        this.tenderRepository = tenderRepository;
        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
        this.classificationRepository = classificationRepository;
        this.tenderIssuerRepository = tenderIssuerRepository;
        this.listPathRepository = listPathRepository;
        this.unsuccessfulItemRepository = unsuccessfulItemRepository;
        this.regionRepository = regionRepository;
        this.regions = regions;
        this.tenderDetailRepository = tenderDetailRepository;
    }

    @PostConstruct
    private void init() {
        regions = regionRepository.findAll();
    }

    @Override
    @Transactional
    public void save(TenderAPI tenderAPI) {
        if (tenderAPI.getStatus().startsWith("active") &&
                (!tenderAPI.getProcurementMethod().equals("reporting") ||
                !tenderAPI.getProcurementMethod().equals("belowThreshold"))) {
            log.warn("Tender is in active status {} will be saved to unsuccessful ", tenderAPI.getId());
            unsuccessfulItemRepository.save(new UnsuccessfulItem(tenderAPI.getId(), true));
            return;
        }
        Tender tender = TenderMapperAPI.INSTANCE.tenderApiToTender(tenderAPI);
        saveAddresses(tenderAPI, tender);
        if (tender.getAddresses().isEmpty()) {
            log.warn("Could not find address for {} will be saved to unsuccessful ", tenderAPI.getId());
            unsuccessfulItemRepository.save(new UnsuccessfulItem(tenderAPI.getId(), false));
            return;
        }
        saveTenderIssuer(tender);
        saveClassification(tender);
        saveTenderDetail(tender);
        tenderRepository.save(tender);
    }

    private void saveAddresses(TenderAPI tenderAPI, Tender tender) {
        List<Address> addresses = findOrCreateAddresses(tenderAPI.getItemAPIS().get(0).getDeliveryAddress());
        if (addresses.isEmpty())
            addresses = findOrCreateAddresses(tenderAPI.getIssuer().getAddress());
        tender.getAddresses().addAll(addresses);
    }

    private List<Address> findOrCreateAddresses(AddressAPI addressAPI) {
        Collection<Street> potentialStreets = getPotentialStreets(addressAPI);
        return getAdddresses(addressAPI, potentialStreets);
    }

    Collection<Street> getPotentialStreets(AddressAPI addressAPI) {
        Optional<String> index = findInAddressAPI(addressAPI, this::getValidPostIndex);
        Set<Street> potentialStreets = new HashSet<>();
        if (index.isPresent()) {
            potentialStreets.addAll(streetRepository.findByIndex(index.get()));
        }
        Optional<Region> region = findInAddressAPI(addressAPI, this::getValidRegionName);
        if (region.isPresent()) {
            potentialStreets.addAll(streetRepository.findByRegion(region.get()));
        }
        return potentialStreets;
    }

    Optional<Region> getValidRegionName(String regionRaw) {
        for (Region region : regions) {
            if (regionRaw.toLowerCase().contains(region.getName()))
                return Optional.of(region);
        }
        return Optional.empty();
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

    List<Address> getAdddresses(AddressAPI addressAPI, Collection<Street> potentialStreets) {
        String[] splitedBySemiColumn = addressAPI.getStreetAddress().split(";");
        List<Address> addresses = new ArrayList<>();
        for (String streetAddress : splitedBySemiColumn) {
            String[] splitedByComma = streetAddress.split(",");
            Address.AddressBuilder addressBuilder = null;
            for (int i = 0; i < splitedByComma.length; i++) {
                String splitedByCommaText = splitedByComma[i];
                Optional<Street> foundStreet = potentialStreets.stream().filter(s -> splitedByCommaText.contains(s.getName())).findFirst();
                if (foundStreet.isPresent()) {
                    if (addressBuilder != null)
                        addresses.add(saveAddressIfNeeded(addressBuilder.houseNumber("N/A").build()));

                    addressBuilder = Address.builder();

                    addressBuilder.street(foundStreet.get());
                    addressBuilder.city(foundStreet.get().getCity());
                    if (i == splitedByComma.length - 1) {
                        addresses.add(saveAddressIfNeeded(addressBuilder.houseNumber("N/A").build()));
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
            for (Street streetInList : potentialStreets) {
                Optional<City> city = findInAddressAPI(addressAPI, s -> {
                    if (s.contains(streetInList.getCity().getName()))
                        return Optional.of(streetInList.getCity());
                    return Optional.empty();
                });

                if (!city.isPresent())
                    continue;
                Street street = saveIfNeeded(Street.builder().city(city.get()).index("N/A").name("N/A").build(), city.get());
                Address.AddressBuilder addressBuilder = Address.builder().city(city.get()).street(street).houseNumber("N/A");
                addresses.add(saveAddressIfNeeded(addressBuilder.build()));
            }
        }
        return addresses;
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
        return splited[0];
    }

    Street saveIfNeeded(Street street, City city) {
        Optional<Street> streetFromDB = streetRepository.find(city, street.getName(), street.getIndex());
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
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) != '0'){
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
