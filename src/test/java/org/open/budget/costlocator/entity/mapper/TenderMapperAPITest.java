package org.open.budget.costlocator.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.open.budget.costlocator.api.GsonTest;
import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.api.TenderWrapper;
import org.open.budget.costlocator.entity.Tender;
import org.open.budget.costlocator.mapper.TenderMapperAPI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.junit.Assert.*;

public class TenderMapperAPITest {


    Gson gson;

    @Before
    public void setUp() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Test
    public void tenderApiToTender() throws IOException {
        TenderWrapper tenderWrapper;
        try(Reader reader = new InputStreamReader(
                GsonTest.class.getResourceAsStream("/TenderWrapper.json"))){
            tenderWrapper = gson.fromJson(reader, TenderWrapper.class);
        }
        TenderAPI tenderAPI = tenderWrapper.getTender();
        Tender tender = TenderMapperAPI.INSTANCE.tenderApiToTender(tenderAPI);
        assertEquals(tenderAPI.getId(), tender.getId());
        assertEquals(tenderAPI.getItemAPIS().get(0).getItemId(), tender.getTenderDetail().getId());
    }

    @Test
    public void tenderApiToTender3() throws IOException {
        TenderWrapper tenderWrapper;
        try(Reader reader = new InputStreamReader(
                GsonTest.class.getResourceAsStream("/tenderWrapper3.json"))){
            tenderWrapper = gson.fromJson(reader, TenderWrapper.class);
        }
        TenderAPI tenderAPI = tenderWrapper.getTender();
        Tender tender = TenderMapperAPI.INSTANCE.tenderApiToTender(tenderAPI);
        assertEquals(tenderAPI.getId(), tender.getId());
        assertEquals(tenderAPI.getItemAPIS().get(0).getItemId(), tender.getTenderDetail().getId());
    }
}