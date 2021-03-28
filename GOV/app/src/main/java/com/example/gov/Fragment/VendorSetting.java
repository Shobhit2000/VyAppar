package com.example.gov.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import com.example.gov.Activity.Activity_MAIN;
import com.example.gov.Activity.LoginScreen;
import com.example.gov.Activity.MainActivity;
import com.example.gov.Activity.RegisterActivity;
import com.example.gov.Activity.VendorAuth;
import com.example.gov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class VendorSetting extends PreferenceFragmentCompat {
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.vendorsetting);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        findPreference("switch_preference_1").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent=new Intent(getContext(), LoginScreen.class);
                startActivity(intent);
                return false;
            }
        });

        findPreference("vendor").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                firestore.collection("Customer").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot=task.getResult();

                            if(documentSnapshot.exists())
                            {
                                Intent intent=new Intent(getContext(), Activity_MAIN.class);

                                startActivity(intent);
                            }
                            else
                            {

                                Intent intent=new Intent(getContext(), RegisterActivity.class);
                                Toast.makeText(getContext(),"Register Yourself as Customer First",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }


                        }


                    }
                });




                return false;
            }
        });




    }
}
