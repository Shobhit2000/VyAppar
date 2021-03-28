package com.example.gov.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gov.ModalClasses.Class_Chips;
import com.example.gov.R;

import java.util.ArrayList;

public class Adapter_Chips extends RecyclerView.Adapter<Adapter_Chips.newViewHolder>
{

    private ArrayList<Class_Chips> items;
    Context context;
    public TextView addrwhole;
    sendonItemclickaddr activity;

    public interface sendonItemclickaddr{
        void sendonclickaddr(int i);
    }

    public Adapter_Chips(Context context, ArrayList<Class_Chips> list){
        items=list;
        //activity=(sendonItemclickaddr) context;
        this.context=context;
    }

    public class newViewHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public RadioButton btn;

        public newViewHolder(@NonNull View itemView) {
            super(itemView);

            item=itemView.findViewById(R.id.itemholder);
            item.setAllCaps(true);
            btn=itemView.findViewById(R.id.rbtn);

            //WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
            //layoutParams.width=(int)width/4;
            //itemView.setLayoutParams(layoutParams);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // activity.sendonclickaddr(address.indexOf(view.getTag()));
                }
            });

        }

    }


    @NonNull
    @Override
    public Adapter_Chips.newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search_chips,parent,false);
        //width=parent.getContext().getResources().getDisplayMetrics().widthPixels;
        return new newViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Adapter_Chips.newViewHolder holder, final int position) {

        holder.itemView.setTag(items.get(position));
        holder.item.setText(items.get(position).getItem());

        if (items.get(position).getCurrent()==1) {
            holder.btn.setChecked(true);
            holder.item.setTextColor(context.getResources().getColor(R.color.lightpurple));
        }

        else
        {holder.btn.setChecked(false);
            holder.item.setTextColor(context.getResources().getColor(android.R.color.tab_indicator_text));}


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<items.size();i++)
                {
                    items.get(i).setCurrent(0);

                }
                items.get(position).setCurrent(1);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}



