package com.example.gov.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gov.ModalClasses.ChatContactModalClass;
import com.example.gov.R;

import java.util.List;

public class ChatContactAdapter extends RecyclerView.Adapter<ChatContactAdapter.ChatContactviewHolder> {

    private Context mct;
    private List<ChatContactModalClass> list;

    public ChatContactAdapter(Context mct,List<ChatContactModalClass> list) {
        this.mct = mct;
        this.list = list;
    }

    @Override
    public ChatContactviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mct);
        View view = inflater.inflate(R.layout.chat_contact,null);
        return new ChatContactviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatContactviewHolder holder,  int position) {
        ChatContactModalClass classes=list.get(position);
        holder.tv1.setText(classes.getName());
        holder.twDesc.setText(classes.getLastmsg());
        holder.tvMisc.setText(classes.getTime());












    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatContactviewHolder extends RecyclerView.ViewHolder{

        TextView tv1,twDesc,tvMisc;





        public ChatContactviewHolder(View itemView) {
            super(itemView);

            tv1=itemView.findViewById(R.id.Name);
            twDesc=itemView.findViewById(R.id.text);
            tvMisc=itemView.findViewById(R.id.last);









        }



    }


}
