package com.guitarshack;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StockMonitorTest {

    @Test
    public void alertSent() {
        Alert alert = mock(Alert.class);
        Request request = mock(Request.class);
        when(request.executeRequest(any(), any())).thenReturn("{\"id\": 811,\"stock\": 29,\"leadTime\": 14}", "{\"total\":60}");
        StockMonitor stockMonitor = new StockMonitor(alert, request, new ProductSalesHistory(request));
        stockMonitor.productSold(811, 1);
        verify(alert).send(any());
    }

    @Test
    public void alertNotSent(){
        Alert alert = mock(Alert.class);
        Request request = mock(Request.class);
        when(request.executeRequest(any(), any())).thenReturn("{\"id\": 811,\"stock\": 50,\"leadTime\": 14}", "{\"total\":60}");
        StockMonitor stockMonitor = new StockMonitor(alert, request, new ProductSalesHistory(request));
        stockMonitor.productSold(811, 1);
        verify(alert, never()).send(any());
    }
}
