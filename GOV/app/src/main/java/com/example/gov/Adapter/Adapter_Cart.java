package com.example.gov.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.gov.ModalClasses.Class_Cart;
import com.example.gov.R;
import com.example.gov.RoomDatabase.AppDatabase;
import com.example.gov.RoomDatabase.CartItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.newViewHolder> {

    private List<Class_Cart> carts=new ArrayList<>();
    int height;
    CountandPrice fragment;
    Context context1;
    int i=0;


    public interface CountandPrice
    {
      void getTotalCountandPrice(String totalcount, String totalprice);

    }


    public Adapter_Cart(Context context,List<Class_Cart> list, CountandPrice reference){
        carts = list;
        context1=context;
        fragment=(CountandPrice) reference;



    }


    public class newViewHolder extends RecyclerView.ViewHolder {

        public ImageView iwdisp;
        public ImageButton add,remove;
        public TextView title,desc,price,quantity;

        public newViewHolder(@NonNull final View itemView) {
            super(itemView);

            WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
            layoutParams.height=height/6;
            itemView.setLayoutParams(layoutParams);
            add=itemView.findViewById(R.id.add);
            remove=itemView.findViewById(R.id.subtract);
            iwdisp=itemView.findViewById(R.id.imageView2);
            title=itemView.findViewById(R.id.tvTitle);
            desc=itemView.findViewById(R.id.tvDesc);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.numberofitems);


            //fragment.getTotalCountandPrice(getCount(),getPrice());


        }

    }


    @NonNull
    @Override
    public Adapter_Cart.newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart,parent,false);
        height=parent.getContext().getResources().getDisplayMetrics().heightPixels;
        return new newViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_Cart.newViewHolder holder, final int position) {
        final Class_Cart cart=carts.get(position);


       // holder.iwdisp.setImageResource(carts.get(position).getIwl());
        holder.title.setText(cart.getTitle());
        holder.desc.setText(cart.getDesc());
        holder.price.setText(String.valueOf(Integer.parseInt(cart.getPrice())*Integer.parseInt(cart.getQuantity())));
        holder.quantity.setText(cart.getQuantity());

        Picasso.with(context1).load(cart.getIwl()).into(holder.iwdisp);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppDatabase db= Room.databaseBuilder(context1,AppDatabase.class,"cart5").allowMainThreadQueries().build();
                cart.setQuantity(""+(Integer.parseInt(cart.getQuantity())+1));
                CartItem cartItem=new CartItem(cart.getTitle(), cart.getDesc(),cart.getPrice(),cart.getQuantity(),cart.getIwl(),cart.getUserId(),cart.getId());
                db.cartDao().changeQuantity(cartItem);
                holder.price.setText(String.valueOf(Integer.parseInt(cart.getPrice())*Integer.parseInt(cart.getQuantity())));
                Log.e("Add Button",cart.getQuantity());
                holder.quantity.setText(cart.getQuantity());
                i++;





            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db= Room.databaseBuilder(context1,AppDatabase.class,"cart5").allowMainThreadQueries().build();
                cart.setQuantity(""+(Integer.parseInt(cart.getQuantity())-1));
                CartItem cartItem=new CartItem(cart.getTitle(), cart.getDesc(),cart.getPrice(),cart.getQuantity(),cart.getIwl(),cart.getUserId(),cart.getId());
                if(Integer.parseInt(cart.getQuantity())>0)
                {

                db.cartDao().changeQuantity(cartItem);
                    holder.price.setText(String.valueOf(Integer.parseInt(cart.getPrice())*Integer.parseInt(cart.getQuantity())));
                    holder.quantity.setText(cart.getQuantity());
                    Log.e("Add Button",cart.getQuantity());i--;
                }
                else {
                    db.cartDao().deleteUser(cartItem);
                    Log.e("Add Button","Delete");
                    carts.remove(position);
                    notifyDataSetChanged();

                }
            }
        });


        }






    @Override
    public int getItemCount() {
        return carts.size();
    }

//    private String getCount(){
//     int totalcount=0;
//        for(Class_Cart temp:carts)
//        {
//            totalcount+=Integer.parseInt(temp.getQuantity().trim());
//
//        }
//        return Integer.toString(totalcount);
//    }
//    private String getPrice(){
//        int totalprice=0;
//        for(Class_Cart temp:carts)
//        {
//
//            int count=Integer.parseInt(temp.getQuantity().trim());
//            totalprice+=Integer.parseInt(temp.getPrice().trim().substring(3))*count;
//
//        }
//        return Integer.toString(totalprice);
//    }


}
