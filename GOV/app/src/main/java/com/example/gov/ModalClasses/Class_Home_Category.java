package com.example.gov.ModalClasses;

public class Class_Home_Category {
    private String cat_title;
    private int iwDisp;

    public Class_Home_Category(String cat_title, int iwDisp) {
        this.cat_title = cat_title;
        this.iwDisp = iwDisp;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }

    public int getIwDisp() {
        return iwDisp;
    }

    public void setIwDisp(int iwDisp) {
        this.iwDisp = iwDisp;
    }
}
