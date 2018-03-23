package com.sabihamumcu.spring_mongo_firebase_android;

public class SubCategory {

    private String title;
    private String price;
    private String imageLocation;
    private String detailPage;


    public SubCategory(String title, String price, String imageLocation,String detailPage) {
        this.title = title;
        this.price = price;
        this.imageLocation = imageLocation;
        this.detailPage=detailPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getDetailPage() {
        return detailPage;
    }

    public void setDetailPage(String detailPage) {
        this.detailPage = detailPage;
    }
}
