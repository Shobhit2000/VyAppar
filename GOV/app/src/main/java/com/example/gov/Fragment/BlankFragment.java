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

import com.example.gov.Activity.AddingEvent;
import com.example.gov.Adapter.ServiceAdapter;
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


public class BlankFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseAuth auth;
    FirebaseUser fbuser;
    ServiceModalClasses classes;
    List<ServiceModalClasses> modal;
    ServiceAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton addEvent;

    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        fbuser=auth.getCurrentUser();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank, container, false);
        modal=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recycler_View);
        getdata();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new ServiceAdapter(getActivity(),modal);
        recyclerView.setAdapter(adapter);


        addEvent=view.findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddingEvent.class);

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
                if(documentSnapshot.get("Services")!=null)
                {
                UserDocument.put("Services",documentSnapshot.get("Services"));
                for(Map.Entry<String, Object> details: UserDocument.entrySet())
                {
                    Log.e("Values",details.getKey());
                    for(Map.Entry<String, Object> details2: UserDocument.entrySet())
                    {
                        Map<String, Object> UserDocument1= (Map<String, Object>) details2.getValue();

                        Log.e("BlankFragmen",UserDocument1.toString());
                        for(Map.Entry<String, Object> details3: UserDocument1.entrySet())
                        {
                            Map<String, Object> UserDocument2= (Map<String, Object>) details3.getValue();
                            Log.e("BlankFragmen",UserDocument2.get("Price").toString());
                            classes=new ServiceModalClasses(UserDocument2.get("ServiceName").toString(),UserDocument2.get("Price").toString(),UserDocument2.get("description").toString(),UserDocument2.get("productImage").toString());

                            modal.add(classes);
                            adapter.notifyDataSetChanged();
                        }

                    }


                }}



            }
        });
    }





}