package com.g205emmanuelbryanhugo.powerhome.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.g205emmanuelbryanhugo.powerhome.R;
import com.g205emmanuelbryanhugo.powerhome.models.Equipment;
import java.util.ArrayList;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private List<Equipment> equipments;
    private final OnEquipmentStateChangeListener listener;

    public interface OnEquipmentStateChangeListener {
        void onStateChanged(Equipment equipment, boolean isActive);
    }

    public EquipmentAdapter(OnEquipmentStateChangeListener listener) {
        this.equipments = new ArrayList<>();
        this.listener = listener;
    }

    public void submitList(List<Equipment> newList) {
        this.equipments = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_equipment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Equipment equipment = equipments.get(position);
        holder.ivIcon.setImageResource(equipment.getIconResourceId());
        holder.tvName.setText(equipment.getName());
        holder.tvConsumption.setText(equipment.getConsumption() + " kWh");
        holder.switchEquipment.setChecked(equipment.isActive());
        
        holder.switchEquipment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            equipment.setActive(isChecked);
            if (listener != null) {
                listener.onStateChanged(equipment, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return equipments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvConsumption;
        Switch switchEquipment;

        ViewHolder(View view) {
            super(view);
            ivIcon = view.findViewById(R.id.iv_equipment_icon);
            tvName = view.findViewById(R.id.tv_equipment_name);
            tvConsumption = view.findViewById(R.id.tv_equipment_consumption);
            switchEquipment = view.findViewById(R.id.switch_equipment);
        }
    }
}
