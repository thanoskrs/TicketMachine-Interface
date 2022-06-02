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
import com.project.ticketmachine.Payment;
import com.project.ticketmachine.ProductScreen;

import com.project.ticketmachine.databinding.ActivityProductScreenBinding;
import com.project.ticketmachine.databinding.FragmentUniformBinding;
import com.project.ticketmachine.ui.uniform.UniformFragment;

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
        binding.cancelButtonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),com.project.ticketmachine.MainActivity.class);
                startActivity(intent);
            }
        });

        //products listeners

        binding.uniformBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });


        binding.uniformBox1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });


        binding.uniformBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });


        binding.uniformBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox5Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox6Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox7Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
            }
        });

        binding.uniformBox8Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentScreen = new Intent(getActivity(), Payment.class);
                paymentScreen.putExtra("product", "90 Λεπτών");
                paymentScreen.putExtra("price", "1.20$");
                UniformFragment.this.startActivity(paymentScreen);
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