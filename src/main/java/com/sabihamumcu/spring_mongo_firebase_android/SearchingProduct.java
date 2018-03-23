package com.sabihamumcu.spring_mongo_firebase_android;

public class SearchingProduct {

    private String product;

    public SearchingProduct(String product){
        this.product=product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
