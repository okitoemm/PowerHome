package com.g205emmanuelbryanhugo.powerhome.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.g205emmanuelbryanhugo.powerhome.R;
import com.g205emmanuelbryanhugo.powerhome.adapters.EquipmentAdapter;
import com.g205emmanuelbryanhugo.powerhome.viewmodels.MyHabitatViewModel;
import com.g205emmanuelbryanhugo.powerhome.models.Equipment;

public class MyHabitatFragment extends Fragment implements EquipmentAdapter.OnEquipmentStateChangeListener {
    private MyHabitatViewModel viewModel;
    private RecyclerView rvEquipments;
    private TextView tvConsumption;
    private TextView tvEcoCoins;
    private EquipmentAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_habitat, container, false);
        
        initializeViews(view);
        setupViewModel();
        setupRecyclerView();
        setupBookingButton(view);
        
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshData();
        });
        
        return view;
    }

    private void initializeViews(View view) {
        rvEquipments = view.findViewById(R.id.rv_equipment_list);
        tvConsumption = view.findViewById(R.id.tv_current_consumption);
        tvEcoCoins = view.findViewById(R.id.tv_eco_coins);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MyHabitatViewModel.class);
        viewModel.getEquipments().observe(getViewLifecycleOwner(), equipments -> {
            adapter.submitList(equipments);
        });
        
        viewModel.getTotalConsumption().observe(getViewLifecycleOwner(), consumption -> {
            tvConsumption.setText(String.format("%.2f kWh", consumption));
        });

        viewModel.getEcoCoins().observe(getViewLifecycleOwner(), coins -> {
            tvEcoCoins.setText(String.format("%d éco-coins", coins));
        });
    }

    private void setupRecyclerView() {
        adapter = new EquipmentAdapter(this);
        rvEquipments.setAdapter(adapter);
        rvEquipments.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupBookingButton(View view) {
        view.findViewById(R.id.btn_book_slot).setOnClickListener(v -> {
            showBookingDialog();
        });
    }

    private void showBookingDialog() {
        // TODO: Implémenter la boîte de dialogue de réservation
    }

    @Override
    public void onStateChanged(Equipment equipment, boolean isActive) {
        viewModel.updateEquipmentState(equipment, isActive);
    }
}
