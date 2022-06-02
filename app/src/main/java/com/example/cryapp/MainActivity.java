package com.example.cryapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cryapp.databinding.ActivityMainBinding;

import org.web3j.abi.datatypes.Int;

public class MainActivity extends AppCompatActivity {

    private String password_app_string, walletDirectory, login, password_wallet, walletName;
    public SLData value = new SLData(this);
    public setupBounceCastle castle = new setupBounceCastle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        com.example.cryapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        load_value();
    }

    public void load_value(){
        walletDirectory = value.load_data("walletDirectory");
        password_app_string = value.load_data("password_app_string");
        login = value.load_data("login");
        password_wallet = value.load_data("password_wallet");
        walletName = value.load_data("walletName");
    }

    public Bundle getMyData() {
        Bundle data = new Bundle();
        data.putString("walletDirectory",walletDirectory);
        data.putString("password_app_string",password_app_string);
        data.putString("login",login);
        data.putString("password_wallet",password_wallet);
        data.putString("walletName",walletName);
        return data;
    }
}