package com.example.gov.Activity;

import android.app.Application;

import com.example.gov.ModalClasses.Class_Cart;
import com.example.gov.ModalClasses.Class_Chips;
import com.example.gov.ModalClasses.Class_Home_Category;
import com.example.gov.ModalClasses.Class_Search_Categories;
import com.example.gov.R;

import java.util.ArrayList;

public class APPLICATION_CLASS extends Application {
    public static ArrayList<Class_Cart> cart;
    public static ArrayList<Class_Home_Category> top,bottom;
    public static ArrayList<Class_Search_Categories> searchresults;
    public static ArrayList<Class_Chips> CATEGORIES,PRICES;
    public static String TOTAL_PRICE, TOTAL_QUANTITY,ADDRESS,NAME;


    @Override
    public void onCreate() {
        super.onCreate();

        cart=new ArrayList<Class_Cart>();
        top=new ArrayList<Class_Home_Category>();
        bottom=new ArrayList<Class_Home_Category>();
        CATEGORIES=new ArrayList<Class_Chips>();
        PRICES=new ArrayList<>();
        searchresults=new ArrayList<Class_Search_Categories>();

        // HOME PAGE DATA
        top.add(new Class_Home_Category("Food", R.drawable.foodplaceholder));
        top.add(new Class_Home_Category("Food", R.drawable.foodplaceholder));
        top.add(new Class_Home_Category("Food", R.drawable.foodplaceholder));
        top.add(new Class_Home_Category("Food", R.drawable.foodplaceholder));
        top.add(new Class_Home_Category("Food", R.drawable.foodplaceholder));
        top.add(new Class_Home_Category("Food", R.drawable.foodplaceholder));
        bottom.add(new Class_Home_Category("Mallu Joint", R.drawable.piza));
        bottom.add(new Class_Home_Category("Mallu Joint", R.drawable.piza));
        bottom.add(new Class_Home_Category("Mallu Joint", R.drawable.piza));
        bottom.add(new Class_Home_Category("Mallu Joint", R.drawable.piza));
        bottom.add(new Class_Home_Category("Mallu Joint", R.drawable.piza));
        bottom.add(new Class_Home_Category("Mallu Joint", R.drawable.piza));

        //CATEGORIES
        CATEGORIES.add(new Class_Chips("FOOD",1));
        CATEGORIES.add(new Class_Chips("FASHION",0));
        CATEGORIES.add(new Class_Chips("TUITION",0));
        CATEGORIES.add(new Class_Chips("GROCERIES",0));
        CATEGORIES.add(new Class_Chips("FREELANCE",0));
        CATEGORIES.add(new Class_Chips("ART AND DECOR",0));
        CATEGORIES.add(new Class_Chips("RENTAL",0));
        CATEGORIES.add(new Class_Chips("HOUSE SERVICES",0));

        //PRICES
        PRICES.add(new Class_Chips("HIGH TO LOW",0));
        PRICES.add(new Class_Chips("LOW TO HIGH",1));

        //SEARCH RESULTS DATA
//        searchresults.add(new Class_Search_Categories("Malayalam Foods","Tasty homemade Malayalam","Alandur","4/5", R.drawable.piza));
//        searchresults.add(new Class_Search_Categories("Thiru Foods","Tasty homemade Malayalam","Alandur","4/5", R.drawable.piza));
//        searchresults.add(new Class_Search_Categories("Amuthan Foods","Tasty homemade Malayalam","Alandur","4/5", R.drawable.piza));
//        searchresults.add(new Class_Search_Categories("Kanni Babu Foods","Tasty homemade Malayalam","Alandur","4/5", R.drawable.piza));
//        searchresults.add(new Class_Search_Categories("Bob dylan Foods","Tasty homemade Malayalam","Alandur","4/5",R.drawable.piza));
//        searchresults.add(new Class_Search_Categories("Malayalam Foods","Tasty homemade Malayalam","Alandur","4/5", R.drawable.piza));
//        searchresults.add(new Class_Search_Categories("Chinese Foods","Tasty homemade Malayalam","Alandur","4/5", R.drawable.piza));
//        searchresults.add(new Class_Search_Categories("Yo mamas Foods","Tasty homemade Malayalam","Alandur","4/5", R.drawable.piza));

        //CART DATA

    }
}
