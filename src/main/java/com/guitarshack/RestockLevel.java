package com.guitarshack;

import java.util.Calendar;
import java.util.Date;

public class RestockLevel {
    private final SalesHistory salesHistory;
    private final DateProvider today;

    public RestockLevel(SalesHistory salesHistory, DateProvider today) {
        this.salesHistory = salesHistory;
        this.today = today;
    }

    int getRestockLevel(Product product) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today.date());
        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime();

        calendar.add(Calendar.DATE, 30);
        Date endDate = calendar.getTime();
        SalesTotal total = salesHistory.getSalesTotal(product, startDate, endDate);
        int restockLevel = (int) ((double) (total.getTotal() / 30) * product.getLeadTime());
        return restockLevel;
    }
}
