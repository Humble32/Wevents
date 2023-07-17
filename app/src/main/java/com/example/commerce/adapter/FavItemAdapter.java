package com.example.commerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.commerce.R;
import com.example.commerce.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FavItemAdapter extends RecyclerView.Adapter<FavItemAdapter.ViewHolder>{
    List<Items> itemsList;
    FirebaseFirestore Store;
    FirebaseAuth Auth;
    public FavItemAdapter(List<Items> itemsList){
        this.itemsList=itemsList;
        Store=FirebaseFirestore.getInstance();
        Auth=FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favName.setText(itemsList.get(position).getName());
        holder.favDate.setText(itemsList.get(position).getDate());
        holder.favPrice.setText(itemsList.get(position).getPrice()+"€");
        Glide.with(holder.itemView.getContext()).load(itemsList.get(position).getImg_url()).into(holder.favImage);
        holder.removeFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Store.collection("Users").document(Auth.getCurrentUser().getUid())
                        .collection("Fav").document(itemsList.get(position).getDocId1()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    itemsList.remove(itemsList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(holder.itemView.getContext(), "Removed to favorite", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(holder.itemView.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favImage;
        TextView favName;
        TextView favPrice;
        TextView favDate;
        TextView removeFav;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favImage=itemView.findViewById(R.id.fav_image);
            favName=itemView.findViewById(R.id.fav_name);
            favPrice=itemView.findViewById(R.id.fav_price);
            favDate=itemView.findViewById(R.id.fav_date);
            removeFav=itemView.findViewById(R.id.remove_fav);
        }
    }
}
