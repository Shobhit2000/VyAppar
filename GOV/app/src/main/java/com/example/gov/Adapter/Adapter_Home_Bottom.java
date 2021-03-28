package com.example.gov.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gov.ModalClasses.Class_Home_Category;
import com.example.gov.R;

import java.util.ArrayList;

public class Adapter_Home_Bottom extends RecyclerView.Adapter<Adapter_Home_Bottom.newViewHolder> {

    private ArrayList<Class_Home_Category> cats;
    int width;

    sendonItemclickbottom activity;

    public interface sendonItemclickbottom{
        void sendonclickbottom(int i);
    }

    public Adapter_Home_Bottom(Context context, ArrayList<Class_Home_Category> list){
        cats=list;
        activity=(sendonItemclickbottom) context;
    }

    public class newViewHolder extends RecyclerView.ViewHolder {
        public ImageView iwdisp;
        public TextView title;

        public newViewHolder(@NonNull View itemView) {
            super(itemView);

            iwdisp=itemView.findViewById(R.id.iw1);
            title=itemView.findViewById(R.id.tv1);
            WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
            layoutParams.width=(int)width/5;
            itemView.setLayoutParams(layoutParams);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.sendonclickbottom(cats.indexOf(view.getTag()));
                }
            });

        }

    }


    @NonNull
    @Override
    public Adapter_Home_Bottom.newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_bottom,parent,false);
        width=parent.getContext().getResources().getDisplayMetrics().widthPixels;
        return new newViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Home_Bottom.newViewHolder holder, int position) {

        holder.itemView.setTag(cats.get(position));
        holder.iwdisp.setImageResource(cats.get(position).getIwDisp());

        holder.title.setText(cats.get(position).getCat_title());
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }
}

