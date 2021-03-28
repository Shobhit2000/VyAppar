package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.gov.Interface.OnItemClickListner;
import com.example.gov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser fbuser;
    String value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();







        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(auth.getCurrentUser()!=null)
                {
                    fbuser=auth.getCurrentUser();
                    check();




                }else{
                Intent intent= new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intent);}

            }
        },1000);


    }

    public void check()
    {
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Vendor").document(fbuser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        value="Vendor";

                        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                        firestore.collection(value).document(fbuser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Long a= (Long) documentSnapshot.get("type");
                                db.collection("Customer").document(fbuser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists())
                                            {
                                                value="Customer";
                                                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                                                firestore.collection(value).document(fbuser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                   AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this)
                                                                .setTitle("Where would you like to go")
                                                                .setMessage("Please select where you want to head")
                                                                .setPositiveButton("Vendor", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {


                                                                        Intent intent=new Intent(MainActivity.this,VendorAuth.class);
                                                                        intent.putExtra("value","1");
                                                                        startActivity(intent);

                                                                    }
                                                                }).setNegativeButton("Customer", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                Intent intent=new Intent(MainActivity.this,Activity_MAIN.class);
                                                                startActivity(intent);

                                                            }
                                                        });
                                                   AlertDialog dialog=builder.create();
                                                   dialog.show();


                                                    }
                                                });
                                            }else
                                            {
                                                Intent intent=new Intent(MainActivity.this,VendorAuth.class);
                                                intent.putExtra("value","1");
                                                startActivity(intent);
                                            }





                                        }


                                    }
                                });

                            }
                        });
                    }else         
                    {


                        db.collection("Customer").document(fbuser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists())
                                    {
                                        value="Customer";
                                        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                                        firestore.collection(value).document(fbuser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                Long a= (Long) documentSnapshot.get("type");
                                                if(a==0)
                                                {
                                                    Intent intent=new Intent(MainActivity.this,Activity_MAIN.class);
                                                    startActivity(intent);

                                                }else if(a==1){
                                                    Intent intent=new Intent(MainActivity.this,VendorAuth.class);
                                                    intent.putExtra("value","1");
                                                    startActivity(intent);
                                                }


                                            }
                                        });
                                    }





                                }


                            }
                        });



                    }
                    }






                






            }
        });







                        }
                    }