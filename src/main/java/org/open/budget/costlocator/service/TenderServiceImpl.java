package org.open.budget.costlocator.service;

import org.open.budget.costlocator.api.Address;
import org.open.budget.costlocator.api.Classification;
import org.open.budget.costlocator.api.Tender;
import org.open.budget.costlocator.api.TenderIssuer;
import org.open.budget.costlocator.repository.AddressRepository;
import org.open.budget.costlocator.repository.ClassificationRepository;
import org.open.budget.costlocator.repository.TenderIssuerRepository;
import org.open.budget.costlocator.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TenderServiceImpl implements TenderService{

    @Autowired
    TenderRepository tenderRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ClassificationRepository classificationRepository;
    @Autowired
    TenderIssuerRepository tenderIssuerRepository;

    @Override
    @Transactional
    public Tender save(Tender tender) {
        saveAddress(tender.getItem().getDeliveryAddress(), a -> {
            tender.getItem().setDeliveryAddress(a);
            return null;
        });
        saveTenderIssuer(tender);
        saveClassification(tender);
        tenderRepository.save(tender);
        return tender;
    }

    private void saveAddress(Address address, Function<Address,Void> function){
        Optional<Address> addressFromDB = addressRepository.findByStreetAddress(address.getStreetAddress());
        if (!addressFromDB.isPresent()){
            addressRepository.save(address);
        } else {
            function.apply(addressFromDB.get());
        }
    }

    private void saveTenderIssuer(Tender tender){
        TenderIssuer tenderIssuer = tender.getTenderIssuer();
        Optional<TenderIssuer> tenderIssuerFromDb = tenderIssuerRepository.findByIdentifier(tenderIssuer.getIdentifier());
        if (!tenderIssuerFromDb.isPresent()){
            saveAddress(tenderIssuer.getAddress(), a -> {
                tenderIssuer.setAddress(a);
                return null;
            });
            tenderIssuerRepository.save(tenderIssuer);
        } else {
            tender.setTenderIssuer(tenderIssuerFromDb.get());
        }
    }

    private void saveClassification(Tender tender){
        Classification classification = tender.getItem().getClassification();
        Optional<Classification> classificationFromDB = classificationRepository.findById(classification.getId());
        if (!classificationFromDB.isPresent()){
            classificationRepository.save(classification);
        } else {
            tender.getItem().setClassification(classificationFromDB.get());
        }
    }

    @Override
    public Map<String,Tender> search(SearchCriteria searchCriteria) {
        return tenderRepository.findByIds(searchCriteria.getTenderIds());
    }

    @Override
    public void flush() {
        tenderIssuerRepository.flush();
    }
}