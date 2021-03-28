package com.example.gov.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gov.Activity.APPLICATION_CLASS;
import com.example.gov.Activity.MapsActivity;
import com.example.gov.Adapter.Adapter_Home_Bottom;
import com.example.gov.Adapter.Adapter_Home_Top;
import com.example.gov.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Fragment_Home extends Fragment {

    public RecyclerView l1,l2,l3;
    public ImageView iwPromo;
    RecyclerView.Adapter adapter1,adapter2,adapter3;
    RecyclerView.LayoutManager layoutManager2,layoutManager3,layoutManager1;
    View view;
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    BottomNavigationView bnw;

    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        iwPromo=view.findViewById(R.id.iwPromo);
        iwPromo.setImageResource(R.drawable.banner);

        l1=view.findViewById(R.id.lw1);
        l2=view.findViewById(R.id.lw2);
        l3=view.findViewById(R.id.lw3);
        l1.setHasFixedSize(true);
        l2.setHasFixedSize(true);
        l3.setHasFixedSize(true);
        adapter1=new Adapter_Home_Top(this.getActivity(), APPLICATION_CLASS.top);
        adapter2=new Adapter_Home_Bottom(this.getActivity(),APPLICATION_CLASS.bottom);
        adapter3=new Adapter_Home_Bottom(this.getActivity(),APPLICATION_CLASS.bottom);
        layoutManager1=new GridLayoutManager(this.getActivity(),2, RecyclerView.HORIZONTAL,false);
        layoutManager2=new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL,false);
        layoutManager3=new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL,false);
        l1.setAdapter(adapter1);
        l1.setLayoutManager(layoutManager1);
        l2.setAdapter(adapter2);
        l2.setLayoutManager(layoutManager2);
        l3.setAdapter(adapter3);
        l3.setLayoutManager(layoutManager3);
        floatingActionButton= view.findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);


            }
        });

        toolbar= view.findViewById(R.id.toolbarhome);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_location_on_24);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.cart)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_frame,new Fragment_Cart()).commit();
                    bnw=getActivity().findViewById(R.id.bottomnw);
                    bnw.setSelectedItemId(R.id.cartbottom);
                }
                return false;
            }
        });


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        return view;
    }

}