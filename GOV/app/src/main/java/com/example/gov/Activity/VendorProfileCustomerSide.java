package com.example.gov.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gov.Adapter.viewPageAdapter2;
import com.example.gov.Adapter.viewpagerAdapter;
import com.example.gov.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.robertlevonyan.components.picker.PickerDialog;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class VendorProfileCustomerSide extends AppCompatActivity {



    com.example.gov.ModalClasses.user user;
    RelativeLayout linear, ll;
    CardView cv;
    TextView name, description, category, name2, description2;
    Button button;
    ViewPager viewPager;
    FirebaseAuth auth;
    FirebaseUser user1;
    TabLayout tabLayout;
    PickerDialog pickerDialog;
    CircleImageView saveImage;
    public String userId;
    String uri1;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    Spinner spinner;
    private StorageReference storageReference;
    String[] services = {"EDUCATION",
            "FOOD",
            "ART AND DECOR",
            "FREELANCE",
            "GROCERIES",
            "FASHION",
            "RENTAL",
            "HOUSE SERVICES"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile_customer_side);
        intent=getIntent();
        userId=intent.getStringExtra("userId");

        Log.e("vendorProfileCustomerSi",userId);




        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new viewPageAdapter2(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        saveImage=findViewById(R.id.saveImage);
        getData();





    }




    private void getData()
    {
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("Vendor").document(userId.trim()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               name.setText(documentSnapshot.get("companyName").toString());
               description.setText(documentSnapshot.get("description").toString());
                Picasso.with(getApplicationContext()).load(documentSnapshot.get("ImageUrl").toString()).into(saveImage);



            }
        });
    }



    public String getUserID()
    {
        return userId;
    }


}