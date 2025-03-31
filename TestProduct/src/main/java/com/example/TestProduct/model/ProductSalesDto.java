package com.example.TestProduct.model;


public class ProductSalesDto {

    private String productName;
    private long toalItem;

    public ProductSalesDto(String productName, long toalItem) {
        this.productName = productName;
        this.toalItem = toalItem;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long gettoalItem() {
        return toalItem;
    }

    public void settoalItem(long toalItem) {
        this.toalItem = toalItem;
    }
}
