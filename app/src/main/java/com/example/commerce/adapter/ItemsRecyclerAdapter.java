package com.example.commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.commerce.DetailActivity;
import com.example.commerce.HomeActivity;
import com.example.commerce.R;
import com.example.commerce.domain.Items;

import java.util.List;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {

    Context applicationContext;
    List<Items> ItemsList;
    public ItemsRecyclerAdapter(Context applicationContext, List<Items> ItemsList) {
        this.applicationContext=applicationContext;
        this.ItemsList=ItemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(applicationContext).inflate(R.layout.single_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.Cost.setText(ItemsList.get(position).getPrice()+"€");
        holder.Name.setText(ItemsList.get(position).getName());
        if(!(applicationContext instanceof HomeActivity)){
            Glide.with(applicationContext).load(ItemsList.get(position).getImg_url()).into(holder.ItemImage);

        }else {
            holder.ItemImage.setVisibility(View.GONE);
        }
        holder.ItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(applicationContext, DetailActivity.class);
                intent.putExtra("detail",ItemsList.get(position));
                applicationContext.startActivity(intent);
            }
        });
        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(applicationContext, DetailActivity.class);
                intent.putExtra("detail",ItemsList.get(position));
                applicationContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ItemImage;
        private TextView Cost;
        private TextView Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemImage=itemView.findViewById(R.id.item_image);
            Cost=itemView.findViewById(R.id.item_cost);
            Name=itemView.findViewById(R.id.item_nam);
        }
    }
}
