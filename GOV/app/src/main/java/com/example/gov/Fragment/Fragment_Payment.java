package com.example.gov.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.gov.Activity.Activity_MAIN;
import com.example.gov.ModalClasses.Class_Cart;
import com.example.gov.R;
import com.example.gov.RoomDatabase.AppDatabase;
import com.example.gov.RoomDatabase.CartItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Fragment_Payment extends Fragment {
    View view;
    ImageView back;
    Button button3;
    BottomNavigationView bnw;
    String price;
    ArrayList<Class_Cart> carts;
    FirebaseAuth auth;
    FirebaseUser fbuser;


    public Fragment_Payment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b= getArguments();
        price= (String) b.get("totalPrice");
        carts= (ArrayList<Class_Cart>) b.get("cart_item_list");
        auth=FirebaseAuth.getInstance();
        fbuser=auth.getCurrentUser();

        Log.e("payment",price+"");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_payment, container, false);
        back=view.findViewById(R.id.payment_back);
        button3=view.findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<carts.size();i++)
                {
                    final String uuid = UUID.randomUUID().toString().replace("-", "");
                    Map<String, Object> map=new HashMap<>();
                    map.put("productName",carts.get(i).getTitle());
                    map.put("VendorId",carts.get(i).getUserId().trim());
                    map.put("totalprice",Integer.parseInt(carts.get(i).getPrice())*Integer.parseInt(carts.get(i).getQuantity()));
                    map.put("productUrl",carts.get(i).getIwl());


                    final FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                    final int finalI = i;
                    final int finalI1 = i;
                    firestore.collection("Customer").document(fbuser.getUid()).update("Orders."+uuid,map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Map<String, Object> map1=new HashMap<>();
                            map1.put("productName",carts.get(finalI).getTitle());
                            map1.put("CustomerId",fbuser.getUid());
                            map1.put("totalprice",Integer.parseInt(carts.get(finalI).getPrice())*Integer.parseInt(carts.get(finalI).getQuantity()));
                            map1.put("productUrl",carts.get(finalI).getIwl());

                            firestore.collection("Vendor").document(carts.get(finalI).getUserId().trim()).update("Orders."+uuid,map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    CartItem cartItem=new CartItem(carts.get(finalI1).getTitle(), carts.get(finalI1).getDesc(),carts.get(finalI1).getPrice(),carts.get(finalI1).getQuantity(),carts.get(finalI1).getIwl(),carts.get(finalI1).getUserId(),carts.get(finalI1).getId());
                                    AppDatabase db= Room.databaseBuilder(getContext(),AppDatabase.class,"cart5").allowMainThreadQueries().build();
                                    db.cartDao().deleteUser(cartItem);

                                    Toast.makeText(getContext(),"Order Sucessfully Placed",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(getContext(), Activity_MAIN.class);
                                    startActivity(intent);

                                }
                            });




                        }
                    });
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bnw=getActivity().findViewById(R.id.bottomnw);
                bnw.setSelectedItemId(R.id.cartbottom);
            }
        });
        return view;
    }
}