package com.example.gov.RoomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities={CartItem.class},version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CartDao cartDao();


}
