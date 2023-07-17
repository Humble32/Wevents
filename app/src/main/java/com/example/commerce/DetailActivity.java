package com.example.commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.commerce.domain.BestSell;
import com.example.commerce.domain.Feature;
import com.example.commerce.domain.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {
    private ImageView Image;
    private TextView ItemName;
    private TextView Price;
    private Button ItemRating;
    private TextView ItemRatDesc;
    private TextView ItemDesc;
    private Button AddToCart;
    private TextView Date;
    private TextView AddToFav;
    private Button BuyBtn;
    private Toolbar Toolbar;
    private FirebaseFirestore Store;
    private FirebaseAuth Auth;

    Feature feature = null;
    BestSell bestSell = null;
    Items items = null;

   boolean InFavorite=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Image = findViewById(R.id.item_img);
        ItemName = findViewById(R.id.item_name);
        Price = findViewById(R.id.item_price);
        ItemRating = findViewById(R.id.item_rating);
        ItemRatDesc = findViewById(R.id.item_rat_des);
        ItemDesc = findViewById(R.id.item_des);
        AddToCart = findViewById(R.id.item_add_cart);
        AddToFav = findViewById(R.id.item_add_fav);
        BuyBtn = findViewById(R.id.item_buy_now);
        Date = findViewById(R.id.date);
        Store = FirebaseFirestore.getInstance();
        Auth = FirebaseAuth.getInstance();
        final Object obj = getIntent().getSerializableExtra("detail");
        if (obj instanceof Feature) {
            feature = (Feature) obj;
        } else if (obj instanceof BestSell) {
            bestSell = (BestSell) obj;
        } else if (obj instanceof Items) {
            items = (Items) obj;
        }
        if (feature != null) {
            Glide.with(getApplicationContext()).load(feature.getImg_url()).into(Image);
            ItemName.setText(feature.getName());
            Price.setText(feature.getPrice() + "€");
            ItemRating.setText(feature.getRating() + "");
            switch (feature.getRating()) {
                case 1:
                    ItemRatDesc.setText("I just hate it");
                    break;
                case 2:
                    ItemRatDesc.setText("I don't like it");
                    break;
                case 3:
                    ItemRatDesc.setText("It is awesome");
                    break;
                case 4:
                    ItemRatDesc.setText("I just like it");
                    break;
                case 5:
                    ItemRatDesc.setText("I just love it");
                    break;
            }
            ItemDesc.setText(feature.getDescription());
            Date.setText(feature.getDate());
        }
        if (bestSell != null) {
            Glide.with(getApplicationContext()).load(bestSell.getImg_url()).into(Image);
            ItemName.setText(bestSell.getName());
            Price.setText(bestSell.getPrice() + "€");
            ItemRating.setText(bestSell.getRating() + "");
            switch (bestSell.getRating()) {
                case 1:
                    ItemRatDesc.setText("I just hate it");
                    break;
                case 2:
                    ItemRatDesc.setText("I don't like it");
                    break;
                case 3:
                    ItemRatDesc.setText("It is awesome");
                    break;
                case 4:
                    ItemRatDesc.setText("I just like it");
                    break;
                case 5:
                    ItemRatDesc.setText("I just love it");
                    break;
            }
            ItemDesc.setText(bestSell.getDescription());
            Date.setText(bestSell.getDate());
        }

        if (items != null) {
            Glide.with(getApplicationContext()).load(items.getImg_url()).into(Image);
            ItemName.setText(items.getName());
            Price.setText(items.getPrice() + " €");
            ItemRating.setText(items.getRating() + "");
            switch (items.getRating()) {
                case 1:
                    ItemRatDesc.setText("I just hate it");
                    break;
                case 2:
                    ItemRatDesc.setText("I don't like it");
                    break;
                case 3:
                    ItemRatDesc.setText("It is awesome");
                    break;
                case 4:
                    ItemRatDesc.setText("I just like it");
                    break;
                case 5:
                    ItemRatDesc.setText("I just love it");
                    break;
            }
            ItemDesc.setText(items.getDescription());
            Date.setText(items.getDate());
        }


        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feature != null) {
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Cart").add(feature).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this, "Successfully added to cart", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                if (bestSell != null) {
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Cart").add(bestSell).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this, "Successfully added to cart", Toast.LENGTH_SHORT).show();


                                }
                            });
                }
                if (items != null) {
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Cart").add(items).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this, "Successfully added to cart", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                        /*Intent intent=new Intent(DetailActivity.this,HomeActivity.class);
                        startActivity(intent);
                Toast.makeText(DetailActivity.this, "Successfully added to cart.", Toast.LENGTH_SHORT).show();*/
            }
        });
        BuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, AddressActivity.class);
                if (feature != null) {
                    intent.putExtra("item", feature);
                }
                if (bestSell != null) {
                    intent.putExtra("item", bestSell);
                }
                if (items != null) {
                    intent.putExtra("item", items);
                }
                startActivity(intent);
            }
        });
        AddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feature != null) {
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Fav").add(feature).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this, "Successfully added to your Wishlist", Toast.LENGTH_SHORT).show();
                                    AddToFav.setBackgroundResource(R.drawable.baseline_favorite_check_24);
                                }
                            });
                }/*else {
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Fav").document().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(DetailActivity.this, "Removed added to your Wishlist", Toast.LENGTH_SHORT).show();
                                    AddToFav.setBackgroundResource(R.drawable.baseline_favorite_24);
                                }
                            });
                    }
                }
                });

                private fun checkIsFavorite(){
        Log.d(TAG, "checkIsFavorite:Checking if book is in fav or not")

        database.child(auth.uid!!).child("Favorites").child(fdId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    isInMyFavorite = snapshot.exists()
                    if (isInMyFavorite){
                        // available in favorite
                        //set drawable icon
                        binding.cbHeart1.setButtonDrawable(R.drawable.ic_heart_filled)
                    }
                    else{
                        // not available in favorite
                        //set drawable icon
                        binding.cbHeart1.setButtonDrawable(R.drawable.ic_heart_outlined)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.cbHeart1.setButtonDrawable(R.drawable.ic_heart_outlined)
                }

            })
    }*/
                if (bestSell != null) {
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Fav").add(bestSell).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this, "Successfully added to Wishlist", Toast.LENGTH_SHORT).show();
                                    AddToFav.setBackgroundResource(R.drawable.baseline_favorite_check_24);
                                }
                            });
                }
                if (items != null) {
                    Store.collection("Users").document(Auth.getCurrentUser().getUid())
                            .collection("Fav").add(items).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(DetailActivity.this, "Successfully added to Wishlist", Toast.LENGTH_SHORT).show();
                                    AddToFav.setBackgroundResource(R.drawable.baseline_favorite_check_24);
                                }
                            });
                }
            }
        });
    }
}