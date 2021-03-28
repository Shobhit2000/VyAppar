package com.example.gov.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gov.R;
import com.example.gov.ModalClasses.ServiceModalClasses;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.Serviceviewholder> {

    private Context mct;
    private List<ServiceModalClasses> list;
    StorageReference storageReference;


    public ServiceAdapter(Context mct, List<ServiceModalClasses> list) {
        this.mct = mct;
        this.list = list;
    }

    @Override
    public Serviceviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mct);
        View view = inflater.inflate(R.layout.service, null);
        return new Serviceviewholder(view);
    }

    @Override
    public void onBindViewHolder(final Serviceviewholder holder, int position) {
        final ServiceModalClasses classes = list.get(position);
        holder.tv1.setText(classes.getServiceName());
        holder.twDesc.setText(classes.getDescription());
        holder.tvMisc.setText(classes.getPrice());

        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser fbuser = auth.getCurrentUser();

        Picasso.with(mct).load(classes.getUrl()).into(holder.iw1);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Serviceviewholder extends RecyclerView.ViewHolder {

        TextView tv1, twDesc, tvMisc;
        ImageView iw1;


        public Serviceviewholder(View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tv1);
            twDesc = itemView.findViewById(R.id.twDesc);
            tvMisc = itemView.findViewById(R.id.tvMisc);
            iw1 = itemView.findViewById(R.id.iw1);


        }


    }


}

