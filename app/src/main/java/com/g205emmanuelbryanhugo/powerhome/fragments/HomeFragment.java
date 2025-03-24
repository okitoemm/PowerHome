package com.g205emmanuelbryanhugo.powerhome.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.g205emmanuelbryanhugo.powerhome.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        Button voirHabitatButton = view.findViewById(R.id.VoirMonHabitatButton);
        voirHabitatButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_home_to_habitat);
        });
        
        return view;
    }
}
