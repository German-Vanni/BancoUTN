package com.example.banco_utn_gts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.banco_utn_gts.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Boolean simulado = false;
    int diasSimulados = -1;
    Double capitalInvertirSimulado = -1.;
    String nombre = "";
    String apellido = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<String> listaDeMonedas = new ArrayList<String>();
        listaDeMonedas.add("PESOS");
        listaDeMonedas.add("DOLAR");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDeMonedas);

        binding.spinnerMoneda.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("plazo") && bundle.containsKey("capitalInvertir")) {
                diasSimulados = bundle.getInt("plazo");
                capitalInvertirSimulado = bundle.getDouble("capitalInvertir");
                simulado = true;
                binding.constituirButton.setEnabled(true);
            }
        }


        binding.simularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SimularPlazoFijo.class);
                startActivity(intent);
            }
        });
        binding.constituirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Validar
                String capitalString = String.format("%.2f",capitalInvertirSimulado);
                String message = "Tu plazo fijo de ";
                message += capitalString + " por " + diasSimulados + " días ha sido constituido!";


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder
                        .setTitle("Felicitaciones " + nombre + " " + apellido + "!")
                        .setMessage(message)
                        .setPositiveButton("PIOLA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}