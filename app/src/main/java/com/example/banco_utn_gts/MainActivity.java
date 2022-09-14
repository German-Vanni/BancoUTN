package com.example.banco_utn_gts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.banco_utn_gts.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<String> listaDeMonedas = new ArrayList<String>();
        listaDeMonedas.add("PESOS");
        listaDeMonedas.add("DOLAR");

        //Spinner spinner = findViewById();
    }
}