package com.guitarshack;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;

public class DateRangeTest {

    private StockMonitor stockMonitor;
    private Date expectedStartDate;
    private Date expectedEndDate;
    private SalesHistory salesHistory;

    @Before
    public void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.APRIL, 9);
        Date today = calendar.getTime();
        calendar.set(2019, Calendar.APRIL, 9);
        expectedStartDate = calendar.getTime();
        calendar.set(2019, Calendar.MAY, 9);
        expectedEndDate = calendar.getTime();
        Alert alert = Mockito.mock(Alert.class);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.executeRequest(any(), any())).thenReturn("{\"id\": 811,\"stock\": 50,\"leadTime\": 14}", "{\"total\":60}");
        salesHistory = Mockito.mock(SalesHistory.class);
        Mockito.when(salesHistory.getSalesTotal(any(), any(), any())).thenReturn(new SalesTotal(10));
        DateProvider dateProvider = Mockito.mock(DateProvider.class);
        Mockito.when(dateProvider.date()).thenReturn(today);
        stockMonitor = new StockMonitor(alert, request, salesHistory, dateProvider);
    }

    @Test
    public void startDateIsTodayDateInPreviousYear() {
        stockMonitor.productSold(811, 1);
        Mockito.verify(salesHistory).getSalesTotal(any(), Mockito.eq(expectedStartDate), any());
    }

    @Test
    public void endDateIsThirtyDaysFromStartDate() {
        stockMonitor.productSold(811,1);
        Mockito.verify(salesHistory).getSalesTotal(any(),any(),Mockito.eq(expectedEndDate));
    }
}
