package com.guitarshack;

import java.util.Date;

public interface SalesHistory {
    SalesTotal getSalesTotal(Product product, Date startDate, Date endDate);
}
