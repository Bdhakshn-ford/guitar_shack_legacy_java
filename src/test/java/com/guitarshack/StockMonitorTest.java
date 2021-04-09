package com.guitarshack;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StockMonitorTest {

    @Test
    public void alertSent() {
        Alert alert = mock(Alert.class);
        Request request = mock(Request.class);
        when(request.executeRequest(any(), any())).thenReturn("{\"id\": 811,\"stock\": 29,\"leadTime\": 14}", "{\"total\":60}");
        StockMonitor stockMonitor = new StockMonitor(alert, request, new ProductSalesHistory(request), new Today());
        stockMonitor.productSold(811, 1);
        verify(alert).send(any());
    }

    @Test
    public void alertNotSent() {
        Alert alert = mock(Alert.class);
        Request request = mock(Request.class);
        when(request.executeRequest(any(), any())).thenReturn("{\"id\": 811,\"stock\": 50,\"leadTime\": 14}", "{\"total\":60}");
        StockMonitor stockMonitor = new StockMonitor(alert, request, new ProductSalesHistory(request), new Today());
        stockMonitor.productSold(811, 1);
        verify(alert, never()).send(any());
    }

    @Test
    public void startDateIsTodayDateInPreviousYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.APRIL, 9);
        Date today = calendar.getTime();
        calendar.set(2019, Calendar.APRIL, 9);
        Date expectedDate = calendar.getTime();
        Alert alert = mock(Alert.class);
        Request request = mock(Request.class);
        when(request.executeRequest(any(), any())).thenReturn("{\"id\": 811,\"stock\": 50,\"leadTime\": 14}", "{\"total\":60}");
        SalesHistory salesHistory = mock(SalesHistory.class);
        when(salesHistory.getSalesTotal(any(), any(), any())).thenReturn(new SalesTotal(10));
        DateProvider dateProvider = mock(DateProvider.class);
        when(dateProvider.date()).thenReturn(today);
        StockMonitor stockMonitor = new StockMonitor(alert, request, salesHistory, dateProvider);
        stockMonitor.productSold(811, 1);
        verify(salesHistory).getSalesTotal(any(), eq(expectedDate), any());
    }
}
