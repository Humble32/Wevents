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
import com.example.commerce.ItemActivity;
import com.example.commerce.ItemsActivity;
import com.example.commerce.R;
import com.example.commerce.domain.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> CategoryList;
    public CategoryAdapter(Context context, List<Category> CategoryList) {
        this.context=context;
        this.CategoryList=CategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        holder.TypeName.setText(CategoryList.get(position).getName());
        Glide.with(context).load(CategoryList.get(position).getImg_url()).into(holder.TypeImg);
        holder.TypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ItemsActivity.class);
                intent.putExtra("name",CategoryList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView TypeImg;
        private TextView TypeName;
        private LinearLayout TypeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TypeImg=itemView.findViewById(R.id.category_img);
            TypeName=itemView.findViewById(R.id.category_name);
            TypeLayout=itemView.findViewById(R.id.cat_single_item);
        }
    }
}
