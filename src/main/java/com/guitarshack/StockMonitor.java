package com.guitarshack;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class StockMonitor {
    private final Alert alert;
    private final Request request;
    private final SalesHistory salesHistory;
    private final DateProvider today;

    public StockMonitor(Alert alert, Request request, SalesHistory salesHistory, DateProvider dateProvider) {
        this.alert = alert;
        this.request = request;
        this.salesHistory = salesHistory;
        this.today = dateProvider;
    }

    public void productSold(int productId, int quantity) {
        String baseURL = "https://6hr1390c1j.execute-api.us-east-2.amazonaws.com/default/product";
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String paramString = "?";

        for (String key : params.keySet()) {
            paramString += key + "=" + params.get(key).toString() + "&";
        }
        String result = request.executeRequest(baseURL, paramString);
        Product product = new Gson().fromJson(result, Product.class);
        int restockLevel = new RestockLevel(salesHistory, today).getRestockLevel(product);
        if (product.getStock() - quantity <= restockLevel)
            alert.send(product);
    }

}
