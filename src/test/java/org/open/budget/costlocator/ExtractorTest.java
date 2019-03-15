package org.open.budget.costlocator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.open.budget.costlocator.api.TenderAPI;
import org.open.budget.costlocator.entity.Tender;
import org.open.budget.costlocator.api.TenderListItem;
import org.open.budget.costlocator.api.TenderWrapper;
import org.open.budget.costlocator.service.TenderService;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.open.budget.costlocator.Extractor.TENDER_LINK;

public class ExtractorTest {

    private RestTemplate restTemplate;
    private Extractor extractor;
    private Map<String, Tender> cache;
    private TenderWrapper tenderWrapper;
    @Mock
    public TenderService tenderService;

    @Before
    public void setUp() throws Exception {
        restTemplate = mock(RestTemplate.class);
        cache = new HashMap<>();
        extractor = new Extractor(cache, tenderService, restTemplate);
        TenderAPI tender = mock(TenderAPI.class);
        tenderWrapper = mock(TenderWrapper.class);
        when(tenderWrapper.getTender()).thenReturn(tender);
    }

    @Test
    public void testGetLatestTenderImpl_ShouldReturnTender(){
        String id = "TEST";
        String testUrl = TENDER_LINK + id;
        when(restTemplate.getForObject(testUrl, TenderWrapper.class)).thenReturn(tenderWrapper);
        extractor.getLatestTenderImpl(TenderListItem.builder().id(id).build());
        verify(restTemplate).getForObject(ArgumentMatchers.eq(testUrl), ArgumentMatchers.eq(TenderWrapper.class));
    }

    @Test
    public void testGetLatestTender_ShouldReturnTender(){
        String id = "TEST";
        String testUrl = TENDER_LINK + id;
        when(restTemplate.getForObject(testUrl, TenderWrapper.class)).thenReturn(tenderWrapper);
        extractor.getLatestTender(TenderListItem.builder().id(id).build());
        verify(restTemplate).getForObject(ArgumentMatchers.eq(testUrl), ArgumentMatchers.eq(TenderWrapper.class));
    }

    @Test(expected = ResourceAccessException.class)
    public void testGetLatestTender_ShouldThrowException(){
        String id = "TEST";
        String testUrl = TENDER_LINK + id;
        when(restTemplate.getForObject(testUrl, TenderWrapper.class)).thenThrow(ResourceAccessException.class);
        extractor.getLatestTender(TenderListItem.builder().id(id).build());
        verify(restTemplate, times(3)).
                getForObject(ArgumentMatchers.eq(testUrl), ArgumentMatchers.eq(TenderWrapper.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void testGetLatestTender_ShouldReturn(){
        String id = "TEST";
        String testUrl = TENDER_LINK + id;
        when(restTemplate.getForObject(testUrl, TenderWrapper.class)).thenReturn(tenderWrapper);
        extractor.getLatestTender(TenderListItem.builder().id(id).build());
        verify(restTemplate, times(1)).
                getForObject(ArgumentMatchers.eq(testUrl), ArgumentMatchers.eq(TenderWrapper.class));
        verifyNoMoreInteractions(restTemplate);
    }
}