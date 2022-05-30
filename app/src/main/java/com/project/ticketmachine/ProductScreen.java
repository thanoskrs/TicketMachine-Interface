package com.project.ticketmachine;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.project.ticketmachine.databinding.ActivityProductScreenBinding;

public class ProductScreen extends AppCompatActivity {

    private ActivityProductScreenBinding binding;
    private String product_kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        System.out.println(extras.get("key"));
        if (extras.get("key").equals("ticket")){
            product_kind = "ticket";
        }
        else{
            product_kind = "card";
        }

        binding = ActivityProductScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (product_kind.equals("ticket")){
            binding.softBackground.setVisibility(View.INVISIBLE);
            binding.repeatOrderLayout.setVisibility(View.INVISIBLE);
        }

        binding.cancelRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.softBackground.setVisibility(View.INVISIBLE);
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_uniform, R.id.navigation_airport)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_product_screen);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public String getProduct_kind() {
        return product_kind;
    }

}