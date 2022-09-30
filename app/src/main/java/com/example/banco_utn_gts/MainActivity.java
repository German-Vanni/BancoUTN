package com.example.banco_utn_gts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.banco_utn_gts.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Boolean simulado = false;
    int diasSimulados = -1;
    Double capitalInvertirSimulado = -1.;
    String nombre;
    String apellido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Constituir Plazo Fijo");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        List<String> listaDeMonedas = new ArrayList<String>();
        listaDeMonedas.add("PESOS");
        listaDeMonedas.add("DOLARES");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDeMonedas);

//        if(savedInstanceState != null){
//            Log.e("SavedInstanceState","savedInstance not null :D" );
//            nombre = savedInstanceState.getString("nombre");
//            apellido = savedInstanceState.getString("apellido");
//            binding.editNombre.setText(nombre);
//            binding.editApellido.setText((apellido));
//        }

        binding.spinnerMoneda.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            nombre = bundle.getString("nombre");
            apellido = bundle.getString("apellido");
            diasSimulados = bundle.getInt("plazo");
            int iMoneda = adapter.getPosition(bundle.getString("moneda"));
            if(iMoneda != -1){
                binding.spinnerMoneda.setSelection(iMoneda);
            }

            capitalInvertirSimulado = bundle.getDouble("capitalInvertir");
            simulado = true;
            if(nombre.trim().length() > 0 && apellido.trim().length() > 0){
                binding.constituirButton.setEnabled(true);
            }
            else{
                binding.constituirButton.setEnabled(false);
            }


        }
        binding.editNombre.setText(nombre);
        binding.editApellido.setText(apellido);



        binding.editNombre.addTextChangedListener(new DisableConstituirOnEmpty());
        binding.editApellido.addTextChangedListener(new DisableConstituirOnEmpty());


        binding.simularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SimularPlazoFijo.class);
                nombre = binding.editNombre.getText().toString();
                apellido = binding.editApellido.getText().toString();
                intent.putExtra("nombre", nombre);
                intent.putExtra("apellido", apellido);
                String moneda =  binding.spinnerMoneda.getSelectedItem().toString();
                intent.putExtra("moneda", moneda);
                startActivity(intent);

            }
        });
        binding.constituirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String capitalString = String.format("%.2f",capitalInvertirSimulado);
                String message = "Tu plazo fijo de ";
                String monedaString = binding.spinnerMoneda.getSelectedItem().toString().toLowerCase();
                message += capitalString + " " + monedaString + " por " + diasSimulados + " días ha sido constituido!";


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

    private class DisableConstituirOnEmpty implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nombreInput = binding.editNombre.getText().toString();
            String apellidoInput = binding.editApellido.getText().toString();

            if(nombreInput.trim().length() > 0  && apellidoInput.trim().length() > 0 && simulado){
                nombre = nombreInput;
                apellido = apellidoInput;
                binding.constituirButton.setEnabled(true);
            }
            else{
                binding.constituirButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nombre = binding.editNombre.getText().toString();
        apellido = binding.editApellido.getText().toString();
        outState.putString("nombre",nombre);
        outState.putString("apellido",apellido);
        binding.editNombre.onSaveInstanceState();

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nombre = savedInstanceState.getString("nombre");
        apellido = savedInstanceState.getString("apellido");
        binding.editNombre.setText(nombre);
        binding.editApellido.setText(apellido);
    }*/
}