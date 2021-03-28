package com.example.gov.Fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.gov.Activity.APPLICATION_CLASS;
import com.example.gov.Adapter.Adapter_Cart;
import com.example.gov.ModalClasses.Class_Cart;
import com.example.gov.R;
import com.example.gov.RoomDatabase.AppDatabase;
import com.example.gov.RoomDatabase.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Cart extends Fragment implements Adapter_Cart.CountandPrice {
    FragmentManager fragmentManager;
    Toolbar toolbar;
    Button btn;
    View view;
    BottomNavigationView bnw;
    RecyclerView l1;
    Adapter_Cart adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView tp,tc;
    List<CartItem> items;
    List<Class_Cart> addItems;

    public Fragment_Cart() {
        //empty constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       addItems=new ArrayList<>();
        view= inflater.inflate(R.layout.fragment_cart, container, false);
        fragmentManager=getActivity().getSupportFragmentManager();
        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        btn=view.findViewById(R.id.button2);
        tc=view.findViewById(R.id.total);
        tp=view.findViewById(R.id.tvtotalprice);
        l1=view.findViewById(R.id.l1);
        getValues();
        layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        adapter=new Adapter_Cart(getContext(), addItems,this);
        l1.setLayoutManager(layoutManager);
        l1.setAdapter(adapter);

        checkNull();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addItems.size()==0)
                {
                    Toast.makeText(getContext(),"Please select atleast one item!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int totalprice=0;

                    for(int i=0;i<addItems.size();i++)
                    {
                        totalprice=totalprice+Integer.parseInt(addItems.get(i).getQuantity())*Integer.parseInt(addItems.get(i).getPrice());
                    }
                    Log.e("Fragment_cart",totalprice+"");
                    Bundle bundle=new Bundle();
                    bundle.putString("totalPrice",totalprice+"");

                    ArrayList<Class_Cart> list= (ArrayList<Class_Cart>) addItems;
                    bundle.putParcelableArrayList("cart_item_list",
                            (ArrayList<? extends Parcelable>) list);
                   Fragment_Payment payment= new Fragment_Payment();
                   payment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,payment).commit();

                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bnw=getActivity().findViewById(R.id.bottomnw);
                bnw.setSelectedItemId(R.id.searchbottom);

            }
        });


        return view;
    }









    public void getValues()
    {
        AppDatabase db= Room.databaseBuilder(getContext(),AppDatabase.class,"cart5").allowMainThreadQueries().build();
        items=db.cartDao().loadAllCartItem();

        for(int i=0;i<items.size();i++)
        {
            CartItem cartItem=items.get(i);
            Class_Cart cart=new Class_Cart(cartItem.getTitle(),cartItem.getDesc(),cartItem.getPrice(),cartItem.getQuantity(),cartItem.getIwl(),cartItem.getUserId(),cartItem.getId());
            addItems.add(cart);

        }


    }





    @Override
    public void getTotalCountandPrice(String totalcount, String totalprice) {
        tc.setText(totalcount+" items");
        tp.setText("Total Price: Rs." + totalprice);
        if(tc.getText().toString().equals("0 items"))
        {
            tp.setText("");
        }
        APPLICATION_CLASS.TOTAL_PRICE=totalprice;
        APPLICATION_CLASS.TOTAL_QUANTITY=totalcount;
    }

    private boolean checkNull(){
        if(tc.getText().equals("Null"))
        {
            tp.setText("");
            tc.setText("0 items");
            APPLICATION_CLASS.TOTAL_PRICE="";
            APPLICATION_CLASS.TOTAL_QUANTITY="";

            return true;
        }
        else
        {
            return false;
        }
    }
}