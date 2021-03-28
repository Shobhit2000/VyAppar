package com.example.gov.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.robertlevonyan.components.picker.PickerDialog;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddingPost extends AppCompatActivity {

    TextView text;
    ImageView image;
    FloatingActionButton floatingActionButton;
    FirebaseAuth auth;
    FirebaseUser fbuser;

    PickerDialog pickerDialog;
    StorageReference storageReference;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_post);

        floatingActionButton=findViewById(R.id.floating);
        image=findViewById(R.id.image);
        text=findViewById(R.id.text);
        auth = FirebaseAuth.getInstance();
        fbuser = auth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImagePicker.Builder(AddingPost.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(400, 200)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uuid = UUID.randomUUID().toString().replace("-", "");

                if (text.getText().toString()!=null && url != null) {

                    final Map<String, Object> map = new HashMap<>();

                    map.put("description", text.getText().toString());
                    map.put("postImage", url);


                    final FirebaseFirestore firestore = FirebaseFirestore.getInstance();


                    firestore.collection("Vendor").document(fbuser.getUid()).update("Posts." + uuid, map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            firestore.collection("Vendor").document(fbuser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    String uuid1 = UUID.randomUUID().toString().replace("-", "");
                                    Map<String, Object> map2 = new HashMap<>();
                                    final Map<String, Object> map3 = new HashMap<>();
                                    Map<String, Object> map1 = new HashMap<>();

                                    map1.put("description", text.getText().toString());
                                    map1.put("productImage", url);
                                    map1.put("userService", fbuser.getUid());
                                    map2.put(uuid, map1);
                                    map3.put("Post", map2);


                                    final DocumentSnapshot snapshot = task.getResult();
                                    firestore.collection("Post").document(fbuser.getUid()).update("Post." + uuid, map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(AddingPost.this, VendorAuth.class);
                                            intent.putExtra("value", "1");
                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("Adding Event", e.toString());
                                            FirebaseFirestore firestorei = FirebaseFirestore.getInstance();
                                            firestorei.collection("Post").document(fbuser.getUid()).set(map3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent intent = new Intent(AddingPost.this, VendorAuth.class);
                                                    intent.putExtra("value", "1");
                                                    startActivity(intent);
                                                }
                                            });


                                        }
                                    });

                                }
                            });
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Pls enter all the details", Toast.LENGTH_SHORT).show();
                }

            }});








            }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            Uri file = Uri.fromFile(new File(mPaths.get(0)));
            final String uuid = UUID.randomUUID().toString().replace("-", "");
            final StorageReference reference = storageReference.child("Post/" + uuid + ".jpg");
            reference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
//                    url=downloadUrl.toString();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url=uri.toString();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


            image.setImageBitmap(BitmapFactory.decodeFile(mPaths.get(0)));
            Log.e("yeah", mPaths.get(0));
            //Your Code
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        pickerDialog.onPermissionsResult(requestCode, permissions, grantResults);
    }
}