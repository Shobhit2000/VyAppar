package com.example.gov.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.gov.Activity.APPLICATION_CLASS;
import com.example.gov.Activity.AddingPost;
import com.example.gov.Activity.VendorAuth;
import com.example.gov.Activity.VendorProfileCustomerSide;
import com.example.gov.Interface.OnItemClickListner;
import com.example.gov.ModalClasses.Class_Cart;
import com.example.gov.ModalClasses.Class_Search_Categories;
import com.example.gov.R;
import com.example.gov.RoomDatabase.AppDatabase;
import com.example.gov.RoomDatabase.CartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Adapter_Search extends RecyclerView.Adapter<Adapter_Search.newViewHolder> implements Filterable {

    private List<Class_Search_Categories> cats=new ArrayList<>();
    private List<Class_Search_Categories> FULL_LIST=new ArrayList<>();
    int height;

    Context contextthis;
    OnItemClickListner clickListner;



    public void setClickListener(OnItemClickListner itemClickListener) {
        this.clickListner = itemClickListener;
    }

    public Adapter_Search(Context context, List<Class_Search_Categories> list){
        cats = list;
        FULL_LIST=list;

        contextthis=context;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Class_Search_Categories> filteredlist= new ArrayList<Class_Search_Categories>();

            if(charSequence.toString().isEmpty())
            {filteredlist.addAll(FULL_LIST);}
            else
            {
                for(Class_Search_Categories temp: FULL_LIST)
                {
                    if(SearchAlgo(temp.getTitle().toLowerCase(),charSequence.toString()))
                    {
                        filteredlist.add(temp);
                    }
                }
            }
            filteredlist=SortAlgo(filteredlist,charSequence.toString());
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredlist;

            return filterResults;
        }


        private List<Class_Search_Categories> SortAlgo(List<Class_Search_Categories> example, String seq)
        {
            seq=seq.toLowerCase().trim();

            for(int i=0;i<example.size();i++)
            {
                for(int j=i+1;j<example.size();j++)
                {
                    int[] var1=new int[seq.length()];
                    int[] var2=new int[seq.length()];

                    for(int l=0;l<seq.length();l++)
                    {
                        var1[l]=example.get(i).getTitle().indexOf(seq.substring(l,l+1));
                        var2[l]=example.get(j).getTitle().indexOf(seq.substring(l,l+1));

                    }
                    if(variance(var1,seq.length())>variance(var2,seq.length()))
                    {
                        Class_Search_Categories temp= example.get(i);
                        example.set(i,example.get(j));
                        example.set(j,temp);

                    }

                }


            }
            return example;


        }


    @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            cats= (List<Class_Search_Categories>) filterResults.values;
            notifyDataSetChanged();
            Log.d("The Publish","Of results ");

        }
    };

    public class newViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iwdisp;
        public ImageButton btnadd;
        public TextView title,desc,misc,rating;


        public newViewHolder(@NonNull final View itemView) {
            super(itemView);

            WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
            layoutParams.height=height/8;
            itemView.setLayoutParams(layoutParams);
            iwdisp=itemView.findViewById(R.id.iw1);
            title=itemView.findViewById(R.id.tv1);
            desc=itemView.findViewById(R.id.twDesc);
            misc=itemView.findViewById(R.id.tvMisc);
            rating=itemView.findViewById(R.id.tvrating);
            btnadd=itemView.findViewById(R.id.btnadd);

            itemView.setOnClickListener(this);

//            btnadd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //AddToCart(itemView);
//                    activity.updatecart(cats.indexOf(itemView.getTag()));
//
//
//
//                }
//            });



        }

        @Override
        public void onClick(View view) {
            clickListner.onClick(view,getPosition());


        }
    }




    @NonNull
    @Override
    public Adapter_Search.newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search,parent,false);
        height=parent.getContext().getResources().getDisplayMetrics().heightPixels;

        return new newViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Search.newViewHolder holder, final int position) {

        Class_Search_Categories categories= FULL_LIST.get(position);




        holder.title.setText(categories.getTitle());
        holder.desc.setText(categories.getDesc());
        holder.misc.setText(categories.getMisc());
        holder.rating.setText(categories.getRating());

        Picasso.with(contextthis).load(categories.getIwDisp()).into(holder.iwdisp);

        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db= Room.databaseBuilder(contextthis,AppDatabase.class,"cart5").allowMainThreadQueries().build();
                Log.e("Adapter_search", String.valueOf(FULL_LIST.get(position)));
                Class_Search_Categories categories=FULL_LIST.get(position);
                Log.e("Adapter_search", categories.getPrice()+"");


                CartItem item=new CartItem(categories.getTitle(),categories.getDesc(),categories.getMisc(),"1",categories.getIwDisp(),categories.getUserId(),categories.getUserId()+categories.getTitle());
                try{db.cartDao().insertCartItem(item);
                Toast.makeText(contextthis,categories.getUserId(),Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    Log.e("Adapter Search",e.toString());
                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return FULL_LIST.size();
    }








    private double variance(int a[], int n)
    {
        // Compute mean (average of elements)
        int sum = 0;
        for (int i = 0; i < n; i++)
            sum += a[i];
        double mean = (double)sum /
                (double)n;

        // Compute sum squared
        // differences with mean.
        double sqDiff = 0;
        for (int i = 0; i < n; i++)
            sqDiff += (a[i] - mean) *
                    (a[i] - mean);
        return sqDiff / n;
    }
    private boolean SearchAlgo(String example, String seq)

    {
        example = example.toLowerCase().trim();
        seq = seq.toLowerCase().trim();
        int i;
        if(seq.length()==1)
        {
            if(example.contains(seq))
                return true;
            else
                return false;
        }
        else
        {

            if (example.contains(seq.substring(0,1)))
            {
                return (SearchAlgo(example.substring(example.indexOf(seq.substring(0,1))), seq.substring(1)));
            }
            else
            {
                return (false);
            }
        }
    }








//    private void AddToCart(View itemview){
//        int i=cats.indexOf(itemview.getTag()),FLAG=0;
//
//        //
//        String title=cats.get(i).getTitle();
//        String desc=cats.get(i).getDesc();
//        String disp=cats.get(i).getIwDisp();
//        //
//        for(Class_Cart temp: APPLICATION_CLASS.cart)
//        {
//            if(temp.getTitle().equals(title))
//            {  FLAG=1;
//               APPLICATION_CLASS.cart.get(APPLICATION_CLASS.cart.indexOf(temp)).setQuantity(Integer.toString(Integer.parseInt(temp.getQuantity())+1));
//                Toast.makeText(contextthis,"Item Added to Cart",Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        if(FLAG==0)
//        {
//            APPLICATION_CLASS.cart.add(new Class_Cart(title,desc,"Rs.690","1",disp));
//            Toast.makeText(contextthis,"Item Added to Cart",Toast.LENGTH_SHORT).show();
//        }
//        if(APPLICATION_CLASS.TOTAL_QUANTITY==null)
//        {
//            APPLICATION_CLASS.TOTAL_QUANTITY="1";
//        }
//        else if (APPLICATION_CLASS.TOTAL_QUANTITY.isEmpty())
//        {APPLICATION_CLASS.TOTAL_QUANTITY="1";
//
//        }
//        else{
//            APPLICATION_CLASS.TOTAL_QUANTITY=Integer.toString(Integer.parseInt(APPLICATION_CLASS.TOTAL_QUANTITY.trim())+1);
//        }
//    }

}


