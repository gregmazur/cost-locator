package org.open.budget.costlocator.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;

import static org.junit.Assert.*;
public class GsonTest {

    private Gson gson;

    @Before
    public void setUp() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Test
    public void successTestForTenderListWrapper() throws IOException {
        TenderListWrapper tenderListWrapper;
        try(Reader reader = new InputStreamReader(
                GsonTest.class.getResourceAsStream("/tenderListWrapper.json"))){
            tenderListWrapper = gson.fromJson(reader, TenderListWrapper.class);
        }
        assertNotNull(tenderListWrapper);
        assertEquals(tenderListWrapper.getNextPage().getPath(), "/test");
        TenderListItem item = tenderListWrapper.getTenderList().get(0);
        assertEquals(item.getId(), "first");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getDateModified());
        assertEquals(calendar.get(Calendar.YEAR), 2015);
        assertEquals(tenderListWrapper.getPrevPage().getPath(), "/prev");
    }


    @Test
    public void failTestForTenderListWrapper(){
        TenderListWrapper tenderList = gson.fromJson("{\"wrong\": \"json\"} ", TenderListWrapper.class);
        assertNull(tenderList.getNextPage());
        assertNull(tenderList.getTenderList());
        assertNull(tenderList.getPrevPage());
    }

    @Test
    public void successTestForTenderWrapper() throws IOException {
        TenderWrapper tenderWrapper;
        try(Reader reader = new InputStreamReader(
                GsonTest.class.getResourceAsStream("/TenderWrapper.json"))){
            tenderWrapper = gson.fromJson(reader, TenderWrapper.class);
        }
        assertNotNull(tenderWrapper);
        assertEquals(tenderWrapper.getTender().getIssuer().getIdentifier().getId(), "26506412");
        assertEquals(tenderWrapper.getTender().getId(), "ca301663b4f7423f944411d22543a037");
        assertEquals(tenderWrapper.getTender().getItemAPIS().get(0).getDeliveryAddress().getStreetAddress(),
                "test street");
    }

    @Test
    public void successTestForTenderWrapper2() throws IOException {
        TenderWrapper tenderWrapper;
        try(Reader reader = new InputStreamReader(
                GsonTest.class.getResourceAsStream("/TenderWrapper2.json"))){
            tenderWrapper = gson.fromJson(reader, TenderWrapper.class);
        }
        assertNotNull(tenderWrapper);
    }
}