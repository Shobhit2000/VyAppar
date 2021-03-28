package com.example.gov.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gov.Activity.APPLICATION_CLASS;
import com.example.gov.Activity.VendorProfileCustomerSide;
import com.example.gov.Adapter.Adapter_Chips;
import com.example.gov.Adapter.Adapter_Search;
import com.example.gov.Adapter.ServiceAdapter;
import com.example.gov.Interface.OnItemClickListner;
import com.example.gov.ModalClasses.Class_Chips;
import com.example.gov.ModalClasses.Class_Search_Categories;
import com.example.gov.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kotlin.jvm.internal.MagicApiIntrinsics;

public class Fragment_Search extends Fragment implements OnItemClickListner {

    View view;
    Adapter_Search adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView l1,l2;
    SearchView sw;
    FirebaseUser fbuser;
    FirebaseAuth auth;
    Map<String, Object> document = new HashMap<>();
    Chip chiprice,chipcat;
    BottomSheetDialog dialogadd;
    List<String> vendorId=new ArrayList<>();
    TextView head;

    List<Class_Search_Categories> search_categories = new ArrayList<>();


    public Fragment_Search() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        fbuser = auth.getCurrentUser();
        chipcat=view.findViewById(R.id.chipcat);
        chiprice=view.findViewById(R.id.chipprice);
        chipcat.setText(setCat());
        chiprice.setText(setPrice());

        l1 = view.findViewById(R.id.l1);
        l1.setHasFixedSize(true);
        adapter = new Adapter_Search(this.getActivity(), search_categories);
        linearLayoutManager = new LinearLayoutManager(this.getActivity(), RecyclerView.VERTICAL, false);
adapter.setClickListener(this);

        l1.setAdapter(adapter);
        l1.setLayoutManager(linearLayoutManager);
        getData();

        sw = (SearchView) view.findViewById(R.id.sw);
        sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });



        chipcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogadd=new BottomSheetDialog(getContext());
                dialogadd.setContentView(R.layout.dialog_search_chip);
                dialogadd.setCanceledOnTouchOutside(true);
                head=dialogadd.findViewById(R.id.head);
                head.setText("SELECT CATEGORY");
                l2=dialogadd.findViewById(R.id.choose);
                RecyclerView.LayoutManager templ=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                RecyclerView.Adapter tempadapt= new Adapter_Chips(getContext(),APPLICATION_CLASS.CATEGORIES);
                l2.setLayoutManager(templ);
                l2.setAdapter(tempadapt);
                dialogadd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        chipcat.setText(setCat());
                        search_categories.clear();
                        getData();

                    }
                });
                dialogadd.show();
            }
        });

        chiprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogadd=new BottomSheetDialog(getContext());
                dialogadd.setContentView(R.layout.dialog_search_chip);
                dialogadd.setCanceledOnTouchOutside(true);
                head=dialogadd.findViewById(R.id.head);
                head.setText("SORT BY PRICE");
                l2=dialogadd.findViewById(R.id.choose);
                RecyclerView.LayoutManager templ=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                RecyclerView.Adapter tempadapt= new Adapter_Chips(getContext(),APPLICATION_CLASS.PRICES);
                l2.setLayoutManager(templ);
                l2.setAdapter(tempadapt);
                dialogadd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        chiprice
                                .setText(setPrice());
                    }
                });
                dialogadd.show();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        setHasOptionsMenu(true);

        return view;


    }

    public void getData() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Vendor").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                int i = 0;
                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot : snapshots) {
                    document = documentSnapshot.getData();

                    if(document.get("companyName").toString()!=null&&document.get("description").toString()!=null&&document.get("ImageUrl").toString()!=null&&document.get("Category").toString()!=null&&document.get("address").toString()!=null)
                    {
                        Log.e("fragment_Search",chipcat.getText().toString().trim());
                        Log.e("fragment_Search2",document.get("Category").toString().trim());
                        if(document.get("Category").toString().trim().equals(chipcat.getText().toString().trim())){


                        i++;
                        Class_Search_Categories class_search_categories = new Class_Search_Categories(document.get("companyName").toString(), document.get("description").toString(), "Alandar", "4/5", document.get("ImageUrl").toString(), document.get("Category").toString(), document.get("address").toString());
                        search_categories.add(class_search_categories);

                        vendorId.add(document.get("userId").toString());
                            Log.e("fragment_Search3","yes");
                        adapter.notifyDataSetChanged();}
                        else
                        {
                            adapter.notifyDataSetChanged();
                        }
                    }


//                    for (Map.Entry<String, Object> entry : document.entrySet()) {
//                        Map<String, Objects> map = (Map<String, Objects>) entry.getValue();
//
//                        Log.e("Fragment_Serach2345", entry.getKey().toString()+"="+entry.getValue().toString());
//
//
//
//
////                        Class_Search_Categories class_search_categories = new Class_Search_Categories(map.get("companyName").toString(), map.get("description").toString(), "Alandar", "4/5", map.get("ImageUrl").toString(),map.get("Category").toString(),map.get("address").toString());
////
////                        adapter.notifyDataSetChanged();
//
//                    }
                }


            }
        });

    }


    @Override
    public void onClick(View view, int position) {

        Intent intent=new Intent(getContext(), VendorProfileCustomerSide.class);
        intent.putExtra("userId",vendorId.get(position));
        startActivity(intent);

    }


    private String setCat()
    {
        for(Class_Chips temp:APPLICATION_CLASS.CATEGORIES)
        {
            if(temp.getCurrent()==1)
            {
                return(temp.getItem());
            }
        }
        return ("");
    };

    private String setPrice()
    {
        for(Class_Chips temp:APPLICATION_CLASS.PRICES)
        {
            if(temp.getCurrent()==1)
            {
                return(temp.getItem());
            }
        }
        return ("");
    };
}