package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gov.Adapter.ChatContactAdapter;
import com.example.gov.ModalClasses.ChatContactModalClass;
import com.example.gov.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<ChatContactModalClass> list1=new ArrayList<>();
    ChatContactModalClass contactModalClass=new ChatContactModalClass("Joe's Bakery","Hey!","Yesterday");
    ChatContactModalClass contactModalClass1=new ChatContactModalClass("Joe's Bakery","Hey!","Yesterday");
    ChatContactModalClass contactModalClass2=new ChatContactModalClass("Joe's Bakery","Hey!","Yesterday");
    ChatContactModalClass contactModalClass3=new ChatContactModalClass("Joe's Bakery","Hey!","Yesterday");
    ChatContactModalClass contactModalClass4=new ChatContactModalClass("Joe's Bakery","Hey!","Yesterday");
    ChatContactModalClass contactModalClass5=new ChatContactModalClass("Joe's Bakery","Hey!","Yesterday");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=findViewById(R.id.recycler_View);
        bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.cartbottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        list1.add(contactModalClass);
        list1.add(contactModalClass1);
        list1.add(contactModalClass2);
        list1.add(contactModalClass3);
        list1.add(contactModalClass4);
        list1.add(contactModalClass5);
        ChatContactAdapter chatContactAdapter=new ChatContactAdapter(getApplicationContext(),list1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(chatContactAdapter);
        chatContactAdapter.notifyDataSetChanged();







    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.homebottom):
                Intent intent3=new Intent(getApplicationContext(), VendorAuth.class);
                intent3.putExtra("value","1");

                startActivity(intent3);

              break;

            case (R.id.searchbottom):
                Intent intent=new Intent(getApplicationContext(), SearchActivity.class);

                startActivity(intent);
                break;

            case (R.id.profilebottom):
                Intent intent2=new Intent(getApplicationContext(), SettingActivity.class);

                startActivity(intent2);
                break;


        }

        return true;

    }
}