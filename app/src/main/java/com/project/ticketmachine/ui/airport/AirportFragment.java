package com.project.ticketmachine.ui.airport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.ticketmachine.ProductScreen;
import com.project.ticketmachine.databinding.FragmentAirportBinding;

public class AirportFragment extends Fragment {

    private FragmentAirportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AirportViewModel airportViewModel =
                new ViewModelProvider(this).get(AirportViewModel.class);

        binding = FragmentAirportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProductScreen activity = (ProductScreen) getActivity();
        String product_kind = activity.getProduct_kind();

        if (product_kind.equals("ticket")){
            binding.cardView.setVisibility(View.INVISIBLE);
            binding.ticketView.setVisibility(View.VISIBLE);
        }
        else{
            binding.ticketView.setVisibility(View.INVISIBLE);
            binding.cardView.setVisibility(View.VISIBLE);
        }


//        final TextView textView = binding.textNotifications;
//        airportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}