package com.example.gov.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gov.ModalClasses.ChatContactModalClass;
import com.example.gov.ModalClasses.PostModalClasses;
import com.example.gov.R;
import com.squareup.picasso.Picasso;

import java.util.List;




    public class PostAdapter extends RecyclerView.Adapter<com.example.gov.Adapter.PostAdapter.PostviewHolder> {

        private Context mct;
        private List<PostModalClasses> list;

        public PostAdapter(Context mct, List<PostModalClasses> list) {
            this.mct = mct;
            this.list = list;
        }

        @Override
        public com.example.gov.Adapter.PostAdapter.PostviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mct);
            View view = inflater.inflate(R.layout.post_layout, null);
            return new com.example.gov.Adapter.PostAdapter.PostviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PostviewHolder holder, int position) {
            PostModalClasses classes = list.get(position);
            holder.tv1.setText(classes.getDescription());
            Picasso.with(mct).load(classes.getUrl()).into(holder.image);




        }



        @Override
        public int getItemCount() {
            return list.size();
        }

        public class PostviewHolder extends RecyclerView.ViewHolder {

            TextView tv1;
            ImageView image;


            public PostviewHolder(View itemView) {
                super(itemView);

                tv1 = itemView.findViewById(R.id.tv1);
                image=itemView.findViewById(R.id.image);




            }


        }


    }

