package com.example.gov.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gov.Activity.VendorProfileCustomerSide;
import com.example.gov.Adapter.Adapter_Search;
import com.example.gov.Adapter.Adapter_Search2;
import com.example.gov.ModalClasses.Class_Search_Categories;
import com.example.gov.ModalClasses.ServiceModalClasses;
import com.example.gov.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listFragment extends Fragment {
    RecyclerView recyclerView;
    Adapter_Search2 search;
    List<Class_Search_Categories> list;
    VendorProfileCustomerSide customerSide;





    public listFragment() {
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
        View view= inflater.inflate(R.layout.fragment_list, container, false);
        customerSide= (VendorProfileCustomerSide) getActivity();
        list=new ArrayList<>();
         getData();
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        Log.e("BlankFragmen","jess");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search=new Adapter_Search2(getContext(),list);
        recyclerView.setAdapter(search);



        return view;
    }

    public void getData()
    {
        FirebaseFirestore firestore= FirebaseFirestore.getInstance();
        firestore.collection("Vendor").document(customerSide.getUserID().trim()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

                                Class_Search_Categories categories=new Class_Search_Categories(UserDocument2.get("ServiceName").toString(),UserDocument2.get("description").toString(),UserDocument2.get("Price").toString(),"4/5",UserDocument2.get("productImage").toString(),customerSide.getUserID());
                                        list.add(categories);
                                        search.notifyDataSetChanged();

                            }

                        }


                    }}


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("BlankFragmen",e.toString());
            }
        });


    }





}