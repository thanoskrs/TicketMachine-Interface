package com.project.ticketmachine.ui.uniform;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.ticketmachine.MainActivity;
import com.project.ticketmachine.ProductScreen;
import com.project.ticketmachine.databinding.ActivityProductScreenBinding;
import com.project.ticketmachine.databinding.FragmentUniformBinding;

public class UniformFragment extends Fragment {

    private FragmentUniformBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UniformViewModel uniformViewModel =
                new ViewModelProvider(this).get(UniformViewModel.class);

        binding = FragmentUniformBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        uniformViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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


        // on cancel button
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        // on cancel button
        binding.cancelButtonTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        //products listeners

        binding.box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box1Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box2Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box3Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box4Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box5Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box6Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box7Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        binding.box8Ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}