package com.example.commerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.example.commerce.domain.Feature;

import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    Context context;
    List<Feature> FeatureList;
    public FeatureAdapter(Context context, List<Feature> FeatureList) {
        this.context=context;
        this.FeatureList=FeatureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_feature_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.FeatCost.setText(FeatureList.get(position).getPrice()+"€");
        holder.FeatName.setText(FeatureList.get(position).getName());
        Glide.with(context).load(FeatureList.get(position).getImg_url()).into(holder.FeatImg);
        holder.FeatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("detail",FeatureList.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return FeatureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout FeatLayout;
        private ImageView FeatImg;
        private TextView FeatName;
        private TextView FeatCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FeatLayout=itemView.findViewById(R.id.feature_single_item);
            FeatImg=itemView.findViewById(R.id.feature_img);
            FeatName=itemView.findViewById(R.id.feature_name);
            FeatCost=itemView.findViewById(R.id.feature_price);
        }
    }
}
