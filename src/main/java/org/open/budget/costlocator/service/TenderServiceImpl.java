package org.open.budget.costlocator.service;

import lombok.Builder;
import org.open.budget.costlocator.api.AddressAPI;
import org.open.budget.costlocator.entity.*;
import org.open.budget.costlocator.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TenderServiceImpl implements TenderService {

    private static final Logger log = LoggerFactory.getLogger(TenderServiceImpl.class);

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

    private List<Region> regions;

    public TenderServiceImpl(TenderRepository tenderRepository, AddressRepository addressRepository,
                             StreetRepository streetRepository, ClassificationRepository classificationRepository,
                             TenderIssuerRepository tenderIssuerRepository, ListPathRepository listPathRepository,
                             UnsuccessfulItemRepository unsuccessfulItemRepository, RegionRepository regionRepository,
                             List<Region> regions) {
        this.tenderRepository = tenderRepository;
        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
        this.classificationRepository = classificationRepository;
        this.tenderIssuerRepository = tenderIssuerRepository;
        this.listPathRepository = listPathRepository;
        this.unsuccessfulItemRepository = unsuccessfulItemRepository;
        this.regionRepository = regionRepository;
        this.regions = regions;
    }

    @PostConstruct
    private void init() {
        regions = regionRepository.findAll();
    }

    @Override
    @Transactional
    public Tender save(Tender tender) {
        saveAddresses(tender);
        if (tender.getAddresses().isEmpty()) {
            log.warn("Could not find address for {} will be saved to unsuccessful ", tender.getId());
        }
        saveTenderIssuer(tender);
        saveClassification(tender);
        tenderRepository.save(tender);
        return tender;
    }

    private void saveAddresses(Tender tender) {
        List<Address> addresses = findOrCreateAddresses(tender.getTenderDetail().getDeliveryAddress());
        if (addresses.isEmpty())
            addresses = findOrCreateAddresses(tender.getIssuer().getAddress());
        tender.getAddresses().addAll(addresses);
    }

    private List<Address> findOrCreateAddresses(AddressAPI addressAPI) {
        List<Street> potentialStreets = getPotentialStreets(addressAPI);
        return getAdddresses(addressAPI, potentialStreets);
    }

    List<Street> getPotentialStreets(AddressAPI addressAPI) {
        Optional<String> index = findInAddressAPI(addressAPI, this::getValidPostIndex);
        if (index.isPresent()) {
            return streetRepository.findByIndex(index.get());
        } else {
            Optional<Region> region = findInAddressAPI(addressAPI, this::getValidRegionName);
            if (index.isPresent()) {
                return streetRepository.findByRegion(region.get());
            }
        }
        return Collections.EMPTY_LIST;
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

    List<Address> getAdddresses(AddressAPI addressAPI, List<Street> potentialStreets) {
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
                        addresses.add(saveAddressIfNeeded(addressBuilder.build()));

                    addressBuilder = Address.builder();

                    addressBuilder.street(foundStreet.get());
                    addressBuilder.city(foundStreet.get().getCity());
                    if (i == splitedByComma.length - 1) {
                        addresses.add(saveAddressIfNeeded(addressBuilder.build()));
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
                Address.AddressBuilder addressBuilder = Address.builder().city(city.get());
                addresses.add(saveAddressIfNeeded(addressBuilder.build()));
            }
        }
        return addresses;
    }

    Address saveAddressIfNeeded(Address address) {
        Optional<Address> addressFromDB;
        if (address.getStreet() == null) {
            addressFromDB = addressRepository.find(address.getCity().getId());
        } else {
            addressFromDB = addressRepository.find(address.getCity().getId(), address.getStreet().getId(), address.getHouseNumber());
        }
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

    private void saveTenderIssuer(Tender tender) {
        TenderIssuer tenderIssuer = tender.getIssuer();
        Optional<TenderIssuer> tenderIssuerFromDb = tenderIssuerRepository.findByIdentifier(tenderIssuer.getIdentifier());
        if (!tenderIssuerFromDb.isPresent()) {
            formalize(tenderIssuer);
            tenderIssuerRepository.save(tenderIssuer);
        } else {
            tender.setIssuer(tenderIssuerFromDb.get());
        }
    }

    void formalize(TenderIssuer tenderIssuer) {
        String legalName = tenderIssuer.getIdentifier().getLegalName();
        if (legalName.length() > 500) {
            tenderIssuer.getIdentifier().setLegalName(legalName.substring(0, 489));
        }
    }

    void saveClassification(Tender tender) {
        Classification classification = tender.getTenderDetail().getClassification();
        Optional<Classification> classificationFromDB = classificationRepository.findById(classification.getId());
        if (!classificationFromDB.isPresent()) {
            classificationRepository.save(classification);
        } else {
            tender.getTenderDetail().setClassification(classificationFromDB.get());
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
}
