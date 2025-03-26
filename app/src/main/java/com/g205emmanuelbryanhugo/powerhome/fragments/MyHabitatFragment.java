package com.g205emmanuelbryanhugo.powerhome.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_booking, null);
        CalendarView calendarView = dialogView.findViewById(R.id.calendar_view);
        Spinner spinnerTimeSlot = dialogView.findViewById(R.id.spinner_time_slot);
        Spinner spinnerEquipment = dialogView.findViewById(R.id.spinner_equipment);
        Button btnConfirm = dialogView.findViewById(R.id.btn_confirm_booking);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        final long[] selectedDateMillis = {calendarView.getDate()};
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            selectedDateMillis[0] = selected.getTimeInMillis();
        });

        List<String> timeSlots = new ArrayList<>();
        List<Integer> timeSlotIds = new ArrayList<>();
        timeSlots.add("20h00 - 21h00");
        timeSlotIds.add(1);

        ArrayAdapter<String> slotAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, timeSlots);
        spinnerTimeSlot.setAdapter(slotAdapter);

        List<Equipment> equipmentList = viewModel.getEquipments().getValue();
        List<String> equipmentNames = new ArrayList<>();
        List<Integer> equipmentIds = new ArrayList<>();
        for (Equipment e : equipmentList) {
            equipmentNames.add(e.getName());
            equipmentIds.add(e.getId());
        }

        ArrayAdapter<String> equipAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, equipmentNames);
        spinnerEquipment.setAdapter(equipAdapter);

        btnConfirm.setOnClickListener(v -> {
            int selectedSlotPos = spinnerTimeSlot.getSelectedItemPosition();
            int selectedEquipPos = spinnerEquipment.getSelectedItemPosition();

            if (selectedSlotPos >= 0 && selectedEquipPos >= 0) {
                int timeSlotId = timeSlotIds.get(selectedSlotPos);
                int applianceId = equipmentIds.get(selectedEquipPos);

                SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
                String token = prefs.getString("token", null);
                String url = "http://10.0.2.2/powerhome_php/bookSlot.php?token=" + token + "&appliance_id=" + applianceId + "&time_slot_id=" + timeSlotId;

                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            JSONObject json = new JSONObject(response);
                            String message = json.getString("message");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            if (json.getBoolean("success")) viewModel.refreshData();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Toast.makeText(getContext(), "Erreur réseau", Toast.LENGTH_LONG).show()
                );
                queue.add(request);
                dialog.dismiss();
            }
        });
    }
    

    @Override
    public void onStateChanged(Equipment equipment, boolean isActive) {
        viewModel.updateEquipmentState(equipment, isActive);
    }
}
