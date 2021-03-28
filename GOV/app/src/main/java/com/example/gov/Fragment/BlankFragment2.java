package com.example.gov.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gov.Activity.AddingPost;
import com.example.gov.Adapter.PostAdapter;
import com.example.gov.Adapter.ServiceAdapter;
import com.example.gov.ModalClasses.PostModalClasses;
import com.example.gov.ModalClasses.ServiceModalClasses;
import com.example.gov.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BlankFragment2 extends Fragment {

RecyclerView recyclerView;
FloatingActionButton floating;
List<PostModalClasses> modal;
PostAdapter adapter;
FirebaseAuth auth;
FirebaseUser fbuser;


    public BlankFragment2() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank2, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        floating=view.findViewById(R.id.floating);
        auth=FirebaseAuth.getInstance();
        fbuser=auth.getCurrentUser();
        modal=new ArrayList<>();
        getdata();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new PostAdapter(getActivity(),modal);
        recyclerView.setAdapter(adapter);

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), AddingPost.class);
                startActivity(intent);
            }
        });



        return view;
    }


    public void getdata(){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference reference
                =firestore.collection("Vendor").document(fbuser.getUid());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Map<String, Object> UserDocument=new HashMap<>();
                if(documentSnapshot.get("Posts")!=null)
                {
                    UserDocument.put("Posts",documentSnapshot.get("Posts"));
                    for(Map.Entry<String, Object> details: UserDocument.entrySet())
                    {
                        Log.e("Values2",details.getKey());
                        for(Map.Entry<String, Object> details2: UserDocument.entrySet())
                        {
                            Map<String, Object> UserDocument1= (Map<String, Object>) details2.getValue();

                            Log.e("BlankFragmen2",UserDocument1.toString());
                            for(Map.Entry<String, Object> details3: UserDocument1.entrySet())
                            {
                                Map<String, Object> UserDocument2= (Map<String, Object>) details3.getValue();
                                PostModalClasses classes;
                                classes=new PostModalClasses(UserDocument2.get("description").toString(),UserDocument2.get("postImage").toString());

                                modal.add(classes);
                                adapter.notifyDataSetChanged();
                            }

                        }


                    }}



            }
        });
    }



}