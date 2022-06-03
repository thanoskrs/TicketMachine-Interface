package com.project.ticketmachine.ui.airport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.ticketmachine.MainActivity;
import com.project.ticketmachine.ProductScreen;
import com.project.ticketmachine.databinding.FragmentAirportBinding;

import org.bson.Document;

public class AirportFragment extends Fragment {

    private FragmentAirportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AirportViewModel airportViewModel =
                new ViewModelProvider(this).get(AirportViewModel.class);

        binding = FragmentAirportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProductScreen activity = (ProductScreen) getActivity();
        String product_kind = null;

        if (MainActivity.user == null){
            product_kind = "Ticket";
        }
        else{
            product_kind = (String) MainActivity.user.get("Type");
        }

        if (product_kind.equals("ticket")){
            binding.cardView.setVisibility(View.INVISIBLE);
            binding.ticketView.setVisibility(View.VISIBLE);
        }
        else{
            binding.ticketView.setVisibility(View.INVISIBLE);
            binding.cardView.setVisibility(View.VISIBLE);

            // card - airport
            int i = 1;
            for (Document document: MainActivity.list){
                if (document.get("TicketID").equals("uniform_box"+i+"_card")){
                    binding.uniformDurationBox1.setText((String)document.get("Name"));
                    if (MainActivity.user.get("Category") == "Student"){
                        binding.uniformCostBox1.setText((String)document.get("Student Price"));
                    }
                    else{
                        binding.uniformCostBox1.setText((String)document.get("Standard Price"));
                    }
                }
                i++;
            }
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