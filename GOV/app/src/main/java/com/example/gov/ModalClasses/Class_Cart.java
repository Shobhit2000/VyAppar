package com.example.gov.ModalClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Class_Cart implements Parcelable {
    private String title,desc,price,quantity;
    private String iwl,userId,id;



    public Class_Cart(String title, String desc, String price, String quantity, String iwl,String userId,String id) {
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        this.iwl = iwl;
        this.userId=userId;
        this.id=id;
    }
    public Class_Cart(Parcel in)
    {
        super();

    }
    public static final Parcelable.Creator<Class_Cart> CREATOR = new Parcelable.Creator<Class_Cart>() {
        public Class_Cart createFromParcel(Parcel in) {
            return new Class_Cart(in);
        }

        public Class_Cart[] newArray(int size) {

            return new Class_Cart[size];
        }

    };


    public void readFromParcel(Parcel in) {
        title = in.readString();
        desc = in.readString();
        price = in.readString();
        quantity = in.readString();
       iwl = in.readString();
        userId = in.readString();
       id = in.readString();



    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(price);
        dest.writeString(quantity);
        dest.writeString(iwl);
        dest.writeString(userId);
        dest.writeString(id);

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
