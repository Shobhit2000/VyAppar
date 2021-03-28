package com.example.gov.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName="cartItem")
public class CartItem {
//    private String title,desc,price,quantity,userId;
//    private String iwl;

      public String title;
      public String desc;
      public String price;
      public String quantity;
      @NonNull
      @PrimaryKey
      public String id;
      public String userId;

    @ColumnInfo(name = "imageUrl")
    public String iwl;
    public CartItem(String title, String desc, String price, String quantity, String iwl,String userId,String id) {
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.id=id;
        this.iwl = iwl;
        this.userId=userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIwl() {
        return iwl;
    }

    public void setIwl(String iwl) {
        this.iwl = iwl;
    }




}
