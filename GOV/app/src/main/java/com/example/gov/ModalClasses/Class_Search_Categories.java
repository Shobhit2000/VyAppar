package com.example.gov.ModalClasses;

public class Class_Search_Categories {
    private String Title,Desc,Misc,Rating;
    private String iwDisp;
    private String userId;
    private  String price;

    public Class_Search_Categories(String title, String desc, String misc, String rating, String iwDisp) {
        this.Title = title;
        this.Desc = desc;
        this.Misc = misc;
        this.Rating = rating;
        this.iwDisp = iwDisp;

    }

    public Class_Search_Categories(String title, String desc, String misc, String rating, String iwDisp, String userId) {
        Title = title;
        Desc = desc;
        Misc = misc;
        Rating = rating;
        this.iwDisp = iwDisp;
        this.userId = userId;
    }

    public Class_Search_Categories(String title, String desc, String misc, String rating, String iwDisp, String userId, String price) {
        Title = title;
        Desc = desc;
        Misc = misc;
        Rating = rating;
        this.iwDisp = iwDisp;
        this.userId = userId;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getMisc() {
        return Misc;
    }

    public void setMisc(String misc) {
        Misc = misc;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getIwDisp() {
        return iwDisp;
    }

    public void setIwDisp(String iwDisp) {
        this.iwDisp = iwDisp;
    }
}
