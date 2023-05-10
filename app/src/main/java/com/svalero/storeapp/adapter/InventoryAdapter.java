package com.svalero.storeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.svalero.storeapp.R;
import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.view.SeeMapView;
import com.svalero.storeapp.view.SelectMapView;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.SuperheroHolder> {
    private Context context;
    private List<Inventory> inventoryList;
    private Intent intentFrom;
    private String token;
    static final int REQUEST_MAP_CAPTURE = 2;

    public InventoryAdapter(Context context, List<Inventory> inventoryList, Intent intentFrom, String token) {
        this.context = context;
        this.inventoryList = inventoryList;
        this.intentFrom = intentFrom;
        this.token = token;
    }

    public SuperheroHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_item, parent, false);
        return new SuperheroHolder(view);
    }

    @Override
    public void onBindViewHolder( SuperheroHolder holder, int position) {
        holder.location.setText(inventoryList.get(position).getLocation());
        holder.address.setText(inventoryList.get(position).getAddress());
        holder.value.setText(String.valueOf(inventoryList.get(position).getTotalValue()));
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public class SuperheroHolder extends RecyclerView.ViewHolder {
        public TextView location;
        public TextView address;
        public TextView value;
        public Button seeMap;
        public Button addMap;
        public View view;


        public SuperheroHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            location = view.findViewById(R.id.tvInventoryLocation);
            address = view.findViewById(R.id.tvInventoryAddress);
            value = view.findViewById(R.id.tvInventoryValue);
            seeMap = view.findViewById(R.id.tvInventorySeeMap);
            addMap = view.findViewById(R.id.tvInventoryAddMap);

            addMap.setOnClickListener(v -> registerMap(getAdapterPosition()));
            seeMap.setOnClickListener(v -> seeMapDetail(getAdapterPosition()));

        }
    }

    private void seeMapDetail(int adapterPosition) {
        Intent intentMap = new Intent(context, SeeMapView.class);
        String username = intentFrom.getStringExtra("username");
        intentMap.putExtra("username", username);
        intentMap.putExtra("inventory", inventoryList.get(adapterPosition));
        context.startActivity(intentMap);
    }

    private void registerMap(int adapterPosition) {
        Intent intentMap = new Intent(context, SelectMapView.class);
        String username = intentFrom.getStringExtra("username");
        intentMap.putExtra("username", username);
        intentMap.putExtra("token", token);
        intentMap.putExtra("inventory", inventoryList.get(adapterPosition));
        context.startActivity(intentMap);
    }
}
