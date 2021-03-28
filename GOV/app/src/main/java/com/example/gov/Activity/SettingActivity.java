package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.gov.Fragment.Fragment_Home;
import com.example.gov.Fragment.VendorSetting;
import com.example.gov.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navigationView;
    FragmentManager fragmentManager;
    FrameLayout fl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        fl=findViewById(R.id.fl);

        navigationView=findViewById(R.id.bottomNavigation);
        navigationView.setSelectedItemId(R.id.profilebottom);
        fragmentManager=this.getSupportFragmentManager();
        SetFragment(new VendorSetting());

        navigationView.setOnNavigationItemSelectedListener(this);


    }
    private void SetFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(fl.getId(), fragment).commit();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.homebottom):
                Intent intent=new Intent(getApplicationContext(), VendorAuth.class);
                intent.putExtra("value","1");

                startActivity(intent);

                break;

            case (R.id.searchbottom):
                Intent intent2=new Intent(getApplicationContext(), SearchActivity.class);

                startActivity(intent2);
                break;
            case (R.id.cartbottom):
                Intent intent1=new Intent(getApplicationContext(), CartActivity.class);

                startActivity(intent1);
                break;



        }

        return true;
    }
}