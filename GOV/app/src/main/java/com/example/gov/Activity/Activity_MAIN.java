package com.example.gov.Activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.example.gov.Adapter.Adapter_Cart;
import com.example.gov.Adapter.Adapter_Home_Bottom;
import com.example.gov.Adapter.Adapter_Home_Top;
import com.example.gov.Adapter.Adapter_Search;
import com.example.gov.Fragment.Fragment_Cart;
import com.example.gov.Fragment.Fragment_Home;
import com.example.gov.Fragment.Fragment_Search;
import com.example.gov.Fragment.Fragment_Settings_Preference;
import com.example.gov.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_MAIN extends AppCompatActivity implements Adapter_Home_Top.sendonitemclicktop, Adapter_Home_Bottom.sendonItemclickbottom, Adapter_Cart.CountandPrice {

    FragmentManager fragmentManager;
    BottomNavigationView bnw;
    FrameLayout fl;
    SharedPreferences preferences;
    FirebaseAuth firebaseAuth;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        fl = findViewById(R.id.main_frame);
        bnw=findViewById(R.id.bottomnw);
        fragmentManager = this.getSupportFragmentManager();
        SetFragment(new Fragment_Home());
        bnw.setOnNavigationItemSelectedListener(navListener);
        preferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences.edit().putBoolean("switch_preference_1",false).commit();
        bnw.inflateMenu(R.menu.menu_bottom_nav_bar);

        APPLICATION_CLASS.NAME=preferences.getString("Name","Blank");
        APPLICATION_CLASS.ADDRESS=preferences.getString("address","Blank");
        Toast.makeText(Activity_MAIN.this,"Welcome Back "+APPLICATION_CLASS.NAME,Toast.LENGTH_SHORT).show();

    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case (R.id.homebottom):
                            SetFragment(new Fragment_Home());
                            break;

                        case (R.id.searchbottom):
                            SetFragment(new Fragment_Search());
                            break;
                        case (R.id.cartbottom):
                            SetFragment(new Fragment_Cart());
                            break;
                        case (R.id.profilebottom):
                            SetFragment(new Fragment_Settings_Preference());
                            break;


                    }

                    return true;
                }
            };

    @Override
    public void sendonclicktop(int i) {
        bnw.setSelectedItemId(R.id.searchbottom);}

    @Override
    public void sendonclickbottom(int i) {
            bnw.setSelectedItemId(R.id.searchbottom);

    }

    private void SetFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(fl.getId(), fragment).commit();

    }


    @Override
    public void getTotalCountandPrice(String totalcount, String totalprice) {
        TextView tc1 = findViewById(R.id.tvtotalprice);
        TextView tq1 = findViewById(R.id.total);
        tc1.setText("Total Price: Rs."+totalprice);
        tq1.setText(totalcount+" items");
        APPLICATION_CLASS.TOTAL_PRICE=totalprice;
        APPLICATION_CLASS.TOTAL_QUANTITY=totalcount;

    }

    @Override
    public void onBackPressed() {

    }
}