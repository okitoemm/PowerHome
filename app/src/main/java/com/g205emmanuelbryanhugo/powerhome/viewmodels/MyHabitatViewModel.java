package com.g205emmanuelbryanhugo.powerhome.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.g205emmanuelbryanhugo.powerhome.models.Equipment;
import java.util.List;
import java.util.ArrayList;

public class MyHabitatViewModel extends ViewModel {
    private final MutableLiveData<List<Equipment>> equipments = new MutableLiveData<>();
    private final MutableLiveData<Double> totalConsumption = new MutableLiveData<>(0.0);
    private final MutableLiveData<Integer> ecoCoins = new MutableLiveData<>(0);

    public MyHabitatViewModel() {
        loadEquipments();
    }

    public LiveData<List<Equipment>> getEquipments() {
        return equipments;
    }

    public LiveData<Double> getTotalConsumption() {
        return totalConsumption;
    }

    public LiveData<Integer> getEcoCoins() {
        return ecoCoins;
    }

    public void updateEquipmentState(Equipment equipment, boolean isActive) {
        // TODO: Mettre à jour l'état de l'équipement et recalculer la consommation
        calculateTotalConsumption();
    }

    public void refreshData() {
        loadEquipments();
    }

    private void loadEquipments() {
        // TODO: Charger les équipements depuis l'API
        List<Equipment> equipmentList = new ArrayList<>();
        // Ajouter des équipements de test
        equipments.setValue(equipmentList);
    }

    private void calculateTotalConsumption() {
        double total = 0;
        List<Equipment> currentEquipments = equipments.getValue();
        if (currentEquipments != null) {
            for (Equipment equipment : currentEquipments) {
                if (equipment.isActive()) {
                    total += equipment.getConsumption();
                }
            }
        }
        totalConsumption.setValue(total);
    }
}
