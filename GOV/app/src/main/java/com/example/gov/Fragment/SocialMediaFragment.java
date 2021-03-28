package com.example.gov.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gov.Activity.VendorProfileCustomerSide;
import com.example.gov.Adapter.PostAdapter;
import com.example.gov.ModalClasses.PostModalClasses;
import com.example.gov.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SocialMediaFragment extends Fragment {


    RecyclerView recyclerView;
    List<PostModalClasses> modal;
    PostAdapter adapter;
    VendorProfileCustomerSide customerSide;




    public SocialMediaFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_social_media, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        customerSide= (VendorProfileCustomerSide) getActivity();
        modal=new ArrayList<>();
        recyclerView.hasFixedSize();
        getdata();
        adapter=new PostAdapter(getContext(),modal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);





    return view;
    }

    public void getdata(){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference reference
                =firestore.collection("Vendor").document(customerSide.getUserID().trim());
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