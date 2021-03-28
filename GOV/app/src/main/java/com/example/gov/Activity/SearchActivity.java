package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gov.Adapter.OrderDetailsVendorSideAdapter;
import com.example.gov.ModalClasses.ServiceModalClasses;
import com.example.gov.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

   

RecyclerView recyclerView;
OrderDetailsVendorSideAdapter orderDetailsVendorSideAdapter;
List<ServiceModalClasses> list;
FirebaseAuth auth;
FirebaseUser fbuser;


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        auth=FirebaseAuth.getInstance();
        fbuser=auth.getCurrentUser();

        bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.searchbottom);
        list=new ArrayList<>();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        recyclerView=findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        getdata();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        orderDetailsVendorSideAdapter = new OrderDetailsVendorSideAdapter(getApplication(),list);
        recyclerView.setAdapter(orderDetailsVendorSideAdapter);



    }


    public void getdata(){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference reference
                =firestore.collection("Vendor").document(fbuser.getUid());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Map<String, Object> UserDocument=new HashMap<>();
                if(documentSnapshot.get("Orders")!=null)
                {
                    UserDocument.put("Services",documentSnapshot.get("Orders"));
                    for(Map.Entry<String, Object> details: UserDocument.entrySet())
                    {
                        Log.e("Values",details.getKey());
                        for(Map.Entry<String, Object> details2: UserDocument.entrySet())
                        {
                            Map<String, Object> UserDocument1= (Map<String, Object>) details2.getValue();

                            Log.e("SearchActtivity",UserDocument1.toString());
                            for(Map.Entry<String, Object> details3: UserDocument1.entrySet())
                            {
                                Map<String, Object> UserDocument2= (Map<String, Object>) details3.getValue();
                               // Log.e("SearchActivty",UserDocument2.get("Price").toString());
                                ServiceModalClasses classes;
                                classes=new ServiceModalClasses(UserDocument2.get("CustomerId").toString(),UserDocument2.get("totalprice").toString(),UserDocument2.get("productName").toString(),UserDocument2.get("productUrl").toString());

                                list.add(classes);
                                orderDetailsVendorSideAdapter.notifyDataSetChanged();
                            }

                        }


                    }}



            }
        });
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.homebottom):
                Intent intent=new Intent(getApplicationContext(), VendorAuth.class);
                intent.putExtra("value","1");

                startActivity(intent);

                break;

            case (R.id.cartbottom):
                Intent intent1=new Intent(getApplicationContext(), CartActivity.class);

                startActivity(intent1);
                break;
            case (R.id.profilebottom):
                Intent intent2=new Intent(getApplicationContext(), SettingActivity.class);

                startActivity(intent2);
                break;


        }

        return true;
    }

}