package com.smarthing.flowerup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthing.flowerup.model.ListElement;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater minflater;

    public ListAdapter(List<ListElement> itemList) {
        this.mData = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
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


            temp.setVisibility(View.GONE);
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
