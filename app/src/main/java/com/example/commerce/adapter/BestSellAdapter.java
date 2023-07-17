package com.example.commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.commerce.DetailActivity;
import com.example.commerce.R;
import com.example.commerce.domain.BestSell;

import java.util.List;

public class BestSellAdapter extends RecyclerView.Adapter<BestSellAdapter.ViewHolder> {

    Context context;
    List<BestSell> BestSellList;
    public BestSellAdapter(Context context, List<BestSell> BestSellList) {
        this.context=context;
        this.BestSellList=BestSellList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_bestsell_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BSCost.setText(BestSellList.get(position).getPrice()+"€");
        holder.BSName.setText(BestSellList.get(position).getName());
        Glide.with(context).load(BestSellList.get(position).getImg_url()).into(holder.BSImg);
        holder.BSLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("detail",BestSellList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return BestSellList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout BSLayout;
        private ImageView BSImg;
        private TextView BSName;
        private TextView BSCost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BSLayout=itemView.findViewById(R.id.bestsell_single_item);
            BSImg=itemView.findViewById(R.id.bestsell_img);
            BSName=itemView.findViewById(R.id.bestsell_name);
            BSCost=itemView.findViewById(R.id.bestsell_price);
        }
    }
}
