package com.surfacetech.orderitonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.surfacetech.orderitonline.Common.Common;
import com.surfacetech.orderitonline.Interface.IOnRecyclerViewClickLister;
import com.surfacetech.orderitonline.MenuActivity;
import com.surfacetech.orderitonline.Model.EventBus.MenuItemEvent;
import com.surfacetech.orderitonline.Model.Restaurant;
import com.surfacetech.orderitonline.R;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyRestaurantAdapter extends RecyclerView.Adapter<MyRestaurantAdapter.MyViewHolder> {

    Context context;
    List<Restaurant> restaurantList;

    public MyRestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context)
                .inflate(R.layout.layout_restaurant, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(restaurantList.get(position).getImage()).into(holder.img_restaurant);
        holder.txt_restaurant_address.setText(new StringBuffer(restaurantList.get(position).getAddress()));
        holder.txt_restaurant_name.setText(new StringBuffer(restaurantList.get(position).getName()));

        // prevent crashing by implementing this
        holder.setLister((view, position1) -> {
             //Toast.makeText(context, ""+restaurantList.get(position).getName(), Toast.LENGTH_SHORT).show();
            Common.currentRestaurant = restaurantList.get(position);
            EventBus.getDefault().postSticky(new MenuItemEvent(true,restaurantList.get(position)));
            context.startActivity(new Intent(context, MenuActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_restaurant_name)
        TextView txt_restaurant_name;

        @BindView(R.id.txt_restaurant_address)
        TextView txt_restaurant_address;

        @BindView(R.id.img_restaurant)
        ImageView img_restaurant;

        IOnRecyclerViewClickLister lister;

        public void setLister(IOnRecyclerViewClickLister lister) {
            this.lister = lister;
        }

        Unbinder unbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            lister.onClick(view,getAdapterPosition());
        }
    }
}
