package com.example.consolefindergis;

import android.widget.ImageView;

public class SalesInfo {
    public SalesInfo(String title, String description, String sellerName, String itemPrice, String sellerNumber,String city,String url) {
        this.title = title;
        this.description = description;
        this.sellerName = sellerName;
        this.itemPrice = itemPrice;
        this.city=city;
        this.sellerNumber = sellerNumber;
        this.url = url;
    }

    public SalesInfo() {
    }
    String title;
    String city;
    String description;
    String sellerName;
    String itemPrice;
    String sellerNumber;
    String url;
}
