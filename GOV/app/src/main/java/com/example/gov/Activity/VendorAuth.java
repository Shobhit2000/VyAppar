package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gov.Adapter.viewpagerAdapter;
import com.example.gov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.robertlevonyan.components.picker.ItemModel;
import com.robertlevonyan.components.picker.OnPickerCloseListener;
import com.robertlevonyan.components.picker.PickerDialog;
import com.squareup.picasso.Picasso;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class VendorAuth extends AppCompatActivity implements OnPickerCloseListener, BottomNavigationView.OnNavigationItemSelectedListener {

    Intent intent;
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
    String service;
    Button button1;
    String uri1;
    BottomNavigationView bottomNavigationView;
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
        setContentView(R.layout.activity_vendor_auth);
        intent = getIntent();


        //   user=(user) intent.getSerializableExtra("values");
        auth = FirebaseAuth.getInstance();
        user1 = auth.getCurrentUser();
        spinner = (Spinner) findViewById(R.id.spinner);

        button1=findViewById(R.id.button);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        storageReference = FirebaseStorage.getInstance().getReference();


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.homebottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        if (intent != null) {
            if (Integer.parseInt(intent.getStringExtra("value")) == 1) {
                FirebaseFirestore firestore1 = FirebaseFirestore.getInstance();
                firestore1.collection("Vendor").document(user1.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        if (documentSnapshot.getString("companyName") != null) {
                            name.setText(documentSnapshot.getString("companyName"));
                            description.setText(documentSnapshot.getString("description"));
                            try {
                                File file = File.createTempFile("image", "jpg");
                                StorageReference reference = storageReference.child("Images/" + user1.getUid() + ".jpg");
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        Log.e("Vendor Auth", uri.toString());
//                                            Glide.with(getApplicationContext())
//                                                    .load(new File(uri.toString())) // Uri of the picture
//                                                    .into(saveImage);
                                        Picasso.with(getApplicationContext()).load(uri.toString()).into(saveImage);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"no iamge url provided",Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                });

            }

        }


        cv = findViewById(R.id.cv);
        name = findViewById(R.id.name);
        name2 = findViewById(R.id.name2);
        description2 = findViewById(R.id.description2);
        //name.setText(user.getName());
        description = findViewById(R.id.description);

        button = findViewById(R.id.bt);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new viewpagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        linear = findViewById(R.id.linear);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(VendorAuth.this,MapsActivity2.class);
                startActivity(intent);
            }
        });


        saveImage = findViewById(R.id.saveImage);

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImagePicker.Builder(VendorAuth.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(120, 120)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });


        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cv.setVisibility(View.VISIBLE);
                cv.setClickable(true);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        service = services[i];

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        service = "No service";
                    }
                });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name2.getText().toString() == null || description2.getText().toString() == null) {
                    Toast.makeText(getApplicationContext(), "Please fill all the entries", Toast.LENGTH_LONG).show();

                } else {
                    name.setText(name2.getText().toString());
                    description.setText(description2.getText().toString());
                    setData();

                    cv.setVisibility(View.INVISIBLE);
                    cv.setClickable(false);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            Uri file = Uri.fromFile(new File(mPaths.get(0)));
            final StorageReference reference = storageReference.child("Images/" + user1.getUid() + ".jpg");
            reference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            firestore.collection("Vendor").document(user1.getUid()).update("ImageUrl", uri.toString());
                            Log.e("Vendor_Auth",uri.toString());
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


            saveImage.setImageBitmap(BitmapFactory.decodeFile(mPaths.get(0)));
            Log.e("yeah", mPaths.get(0));
            //Your Code
        }
    }


    public void setData() {

        Map<String, Object> user2 = new HashMap<>();
        user2.put("companyName", name.getText());
        user2.put("description", description.getText());
        user2.put("services", service);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Vendor").document(user1.getUid()).update("companyName", name.getText());
        firebaseFirestore.collection("Vendor").document(user1.getUid()).update("description", description.getText());
        firebaseFirestore.collection("Vendor").document(user1.getUid()).update("Category", service);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickerDialog.onPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onPickerClosed(int type, Uri uri) {

        if (type == ItemModel.ITEM_CAMERA || type == ItemModel.ITEM_GALLERY) {

        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()) {


            case (R.id.searchbottom):
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                item.setChecked(true);

                startActivity(intent);
                return true;
            case (R.id.cartbottom):
                Intent intent1 = new Intent(getApplicationContext(), CartActivity.class);

                startActivity(intent1);
                return true;
            case (R.id.profilebottom):
                Intent intent2 = new Intent(getApplicationContext(), SettingActivity.class);

                startActivity(intent2);
                return true;


        }

        return false;
    }

    @Override
    public void onBackPressed() {


        if(cv.isClickable())
        {
            cv.setVisibility(View.INVISIBLE);
            cv.setClickable(false);
        }

    }
}