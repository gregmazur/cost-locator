package org.open.budget.costlocator.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.open.budget.costlocator.api.GsonTest;
import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.api.TenderWrapper;
import org.open.budget.costlocator.entity.Tender;

import java.io.InputStreamReader;
import java.io.Reader;

import static org.junit.Assert.*;

public class TenderMapperTest {

    TenderWrapper tenderWrapper;

    @Before
    public void setUp() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        try(Reader reader = new InputStreamReader(
                GsonTest.class.getResourceAsStream("/TenderWrapper.json"))){
            tenderWrapper = gson.fromJson(reader, TenderWrapper.class);
        }
    }

    @Test
    public void tenderApiToTender() {
        TenderAPI tenderAPI = tenderWrapper.getTender();
        Tender tender = TenderMapper.INSTANCE.tenderApiToTender(tenderAPI);
        assertEquals(tenderAPI.getId(), tender.getId());
        assertEquals(tenderAPI.getItems().get(0).getItemId(), tender.getTenderDetail().getId());
    }
}