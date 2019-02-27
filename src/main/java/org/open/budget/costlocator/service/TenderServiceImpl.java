package org.open.budget.costlocator.service;

import lombok.Builder;
import org.open.budget.costlocator.api.*;
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

    private Set<String> regionCache;

    public TenderServiceImpl(TenderRepository tenderRepository, AddressRepository addressRepository,
                             StreetRepository streetRepository, ClassificationRepository classificationRepository,
                             TenderIssuerRepository tenderIssuerRepository, ListPathRepository listPathRepository,
                             UnsuccessfulItemRepository unsuccessfulItemRepository, Set<String> regionCache) {
        this.tenderRepository = tenderRepository;
        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
        this.classificationRepository = classificationRepository;
        this.tenderIssuerRepository = tenderIssuerRepository;
        this.listPathRepository = listPathRepository;
        this.unsuccessfulItemRepository = unsuccessfulItemRepository;
        this.regionCache = regionCache;
    }

    @PostConstruct
    private void init() {
        regionCache = streetRepository.getRegions();
        regionCache.remove("київ");
    }

    @Override
    @Transactional
    public Tender save(Tender tender) {
        saveAddresses(tender);
        if (tender.getAddresses().isEmpty()){
            log.warn("Could not find address for {} will be saved to unsuccessful ", tender.getId());
        }
        saveTenderIssuer(tender);
        saveClassification(tender);
        tenderRepository.save(tender);
        return tender;
    }

    private void saveAddresses(Tender tender) {
        AddressAPI addressAPI = tender.getItem().getDeliveryAddress();
        List<Street> potentialStreets = getPotentialStreets(addressAPI);
        List<Address> addresses = getAdddresses(addressAPI, potentialStreets);
        tender.getAddresses().addAll(addresses);
    }

    List<Street> getPotentialStreets(AddressAPI addressAPI) {
        Optional<String> region = findInAddressAPI(addressAPI, this::getValidRegionName);
        List<Street> potentialStreets = new ArrayList<>();
        if (region.isPresent())
            potentialStreets = streetRepository.findByRegionContainingIgnoreCase(region.get());
        if (potentialStreets.isEmpty()) {
            Optional<String> index = findInAddressAPI(addressAPI, this::getValidPostIndex);
            if (index.isPresent()) {
                List<Address> addressesByIndex = addressRepository.findByIndex(index.get());
                for (Address address : addressesByIndex) {
                    potentialStreets.add(address.getStreet());
                }
            }
        }
        return potentialStreets;
    }

    Optional<String> getValidRegionName(String region) {
        for (String regionName : regionCache) {
            if (region.toLowerCase().contains(regionName))
                return Optional.of(regionName);
        }
        return Optional.empty();
    }

    Optional<String> findInAddressAPI(AddressAPI addressAPI, Function<String, Optional<String>> function) {
        Optional<String> result = function.apply(addressAPI.getPostalCode());
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
            for (String street : splitedByComma) {

                Optional<Street> foundStreet = potentialStreets.stream().filter(s -> street.contains(s.getName())).findFirst();
                if (foundStreet.isPresent()) {
                    if (addressBuilder != null)
                        addresses.add(saveAddressIfNeeded(addressBuilder.build()));

                    addressBuilder = Address.builder();
                    Optional<String> index = findInAddressAPI(addressAPI, this::getValidPostIndex);
                    if (index.isPresent())
                        addressBuilder.index(index.get());

                    addressBuilder.street(foundStreet.get());
                } else {
                    if (addressBuilder != null) {
                        addressBuilder.houseNumber(street);
                        addresses.add(saveAddressIfNeeded(addressBuilder.build()));
                        addressBuilder = null;
                    }
                }
            }
        }
        return addresses;
    }

    Address saveAddressIfNeeded(Address address){
        Optional<Address> addressFromDB = addressRepository.find(address.getStreet(),address.getHouseNumber(), address.getIndex());
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

    private void saveTenderIssuer(Tender tender) {
        TenderIssuer tenderIssuer = tender.getIssuer();
        Optional<TenderIssuer> tenderIssuerFromDb = tenderIssuerRepository.findByIdentifier(tenderIssuer.getIdentifier());
        if (!tenderIssuerFromDb.isPresent()){
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

    private void saveClassification(Tender tender) {
        Classification classification = tender.getItem().getClassification();
        Optional<Classification> classificationFromDB = classificationRepository.findById(classification.getId());
        if (!classificationFromDB.isPresent()) {
            classificationRepository.save(classification);
        } else {
            tender.getItem().setClassification(classificationFromDB.get());
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
