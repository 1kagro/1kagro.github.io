package com.smarthing.flowerup;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarthing.flowerup.model.ListElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private List<ListElement> mData2;

    private LayoutInflater minflater;

    public ListAdapter(List<ListElement> itemList) {
        this.mData = itemList;
        //mData2.addAll(mData);
    }

    public void setFilteredList(List<ListElement> filteredList){
        this.mData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));

        final ListElement item = mData.get(position);
        holder.name.setText(item.getName());
        holder.category.setText(item.getCategory());
        holder.room.setText(item.getRoom());
        holder.temp.setText(item.getTemp() + "°C");
        holder.humedad.setText(item.getHumedad() + "%");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), DetallePlanta.class);
                intent.putExtra("name", holder.name.getText().toString());
                intent.putExtra("category", holder.category.getText().toString());
                intent.putExtra("temp", holder.temp.getText().toString().replace("°C", ""));
                intent.putExtra("humedad", holder.humedad.getText().toString().replace("%", ""));
                intent.putExtra("room", holder.room.getText().toString());
                intent.putExtra("dia", item.getDias() + "");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public void filtrado(String txtBuscar) {
        int longitud = txtBuscar.length();
        if(longitud == 0){
            mData.clear();
            mData.addAll(mData2);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ListElement> collecion = mData.stream()
                        .filter(i -> i.getName().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                mData.clear();
                mData.addAll(collecion);
            } else {
                for (ListElement element: mData2) {
                    if(element.getName().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        mData.add(element);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<ListElement> items) { mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, category, room;
        Button temp, humedad;
        boolean sw_temp, sw_humedad;

        public ViewHolder(View itemView) {
            super(itemView);
            //iconImage = itemView.findViewById(R.id.imageViewPlant);
            name = itemView.findViewById(R.id.name_plant);
            category = itemView.findViewById(R.id.categoria_plant);
            room = itemView.findViewById(R.id.ubicacion_plant);


            temp = itemView.findViewById(R.id.btn_temp);
            humedad = itemView.findViewById(R.id.btn_water);
        }

        void bindData(final ListElement item) {
            //iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            category.setText(item.getCategory());
            room.setText(item.getRoom());

            if(item.isSw_temp() == false) temp.setVisibility(View.INVISIBLE);
            if(item.isSw_humedad() == false) humedad.setVisibility(View.INVISIBLE);
            //temp.setVisibility(View.GONE);
            /*temp.setBackgroundDrawable(null);
            temp.setCompoundDrawables(null, null, null, null);
            temp.setText("");
            temp.setWidth(0);*/
        }

        public void setVisibility(Button sensor){
            sensor.setVisibility(View.GONE);
        }
    }
}
