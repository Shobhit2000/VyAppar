package com.example.gov.ModalClasses;

public class Class_Chips {
    private String Item;
    private int current;

    public Class_Chips(String item, int current) {
        Item = item;
        this.current = current;
    }

    public Class_Chips(String item) {
        Item = item;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}