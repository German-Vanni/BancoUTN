package com.example.banco_utn_gts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.banco_utn_gts.databinding.ActivitySimularPlazoFijoBinding;

public class SimularPlazoFijo extends AppCompatActivity {
    ActivitySimularPlazoFijoBinding binding;
    private double tasaNomAnual = -1;
    private double tasaEfecAnual = -1;
    private double capitalInvertir  = -1;
    private int plazo = 30;

    TextView plazoResultTextView;
    TextView capitalResultTextView;
    TextView interesesResultTextView;
    TextView montoTotalResultTextView;
    TextView montoTotalAnualResultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySimularPlazoFijoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Simular Plazo Fijo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //TEST
//        binding.capitalInvertirEditText.setText("100000");
//        binding.tasaNominalAnualEditText.setText("69.5");
//        binding.tasaEfectivaAnualEditText.setText("96.33");

        binding.confirmarButton.setEnabled(false);

        binding.tasaNominalAnualEditText.addTextChangedListener(new InputValuesTextWatcher());
        binding.tasaEfectivaAnualEditText.addTextChangedListener(new InputValuesTextWatcher());
        binding.capitalInvertirEditText.addTextChangedListener(new InputValuesTextWatcher());
        binding.plazoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                plazo = seekBar.getProgress()*30;
                String plazoString = " " + plazo  + " Días";
                plazoResultTextView.setText(plazoString);
                binding.seekbarLabelTextView.setText(plazoString);
                if(CheckValidValuesOnInput()){
                    calcular();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Creamos los views necesarios para colocar los valores calculados dinamicamente
        // en la parte inferior de la pantalla
        plazoResultTextView = new TextView(getApplicationContext());
        capitalResultTextView = new TextView(getApplicationContext());
        interesesResultTextView = new TextView(getApplicationContext());
        montoTotalResultTextView = new TextView(getApplicationContext());
        montoTotalAnualResultTextView = new TextView(getApplicationContext());




        binding.resultadoPlazoLinearlayout.addView(plazoResultTextView);
        binding.resultadoCapitalLinearlayout.addView(capitalResultTextView);
        binding.resultadoInteresesGanadosLinearlayout.addView(interesesResultTextView);
        binding.resultadoMontoTotalLinearlayout.addView(montoTotalResultTextView);
        binding.resultadoMontoTotalAnualLinearlayout.addView(montoTotalAnualResultTextView);

        binding.confirmarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SimularPlazoFijo.this, MainActivity.class);
                i.putExtra("capitalInvertir", capitalInvertir);
                i.putExtra("plazo", plazo);
                startActivity(i);
            }
        });
    }


    private class InputValuesTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(CheckValidValuesOnInput()){
                binding.confirmarButton.setEnabled(true);
                calcular();
            }
            else{
//              binding.confirmarButton.setSelected(false);
                binding.confirmarButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private void calcular() {
        capitalResultTextView.setText(binding.capitalInvertirEditText.getText());
        Double interesesGanados = capitalInvertir * (tasaNomAnual / 100) * plazo / (360) ;
        interesesResultTextView.setText(String.format("%.2f", interesesGanados));
        Double montoTotal = capitalInvertir + interesesGanados;
        montoTotalResultTextView.setText(String.format("%.2f",montoTotal));
        Double montoTotalAnual = (capitalInvertir * (tasaNomAnual / 100)) + capitalInvertir;
        montoTotalAnualResultTextView.setText(String.format("%.2f", montoTotalAnual));
    }

    protected Boolean CheckValidValuesOnInput(){
        try {
            tasaNomAnual = Double.parseDouble(binding.tasaNominalAnualEditText.getText().toString());

        }catch (Exception e){
            tasaNomAnual = -1;
            return false;
        }
        try {
            tasaEfecAnual = Double.parseDouble(binding.tasaEfectivaAnualEditText.getText().toString());
        }catch (Exception e){
            tasaEfecAnual = -1;
            return false;
        }
        try {
            capitalInvertir = Double.parseDouble(binding.capitalInvertirEditText.getText().toString());
        }catch (Exception e){
            capitalInvertir = -1;
            return false;
        }


        if(tasaNomAnual <= 0 || tasaEfecAnual<= 0 || capitalInvertir <= 0){
            tasaNomAnual = -1;
            tasaEfecAnual = -1;
            capitalInvertir = -1;
            return false;
        }

        return true;
    }

}