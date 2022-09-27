package com.example.banco_utn_gts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.banco_utn_gts.databinding.ActivitySimularPlazoFijoBinding;

public class SimularPlazoFijo extends AppCompatActivity {
    ActivitySimularPlazoFijoBinding binding;
    private double tasaNomAnual = -1;
    private double tasaEfecAnual = -1;
    private double capitalInvertir  = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySimularPlazoFijoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());;

        binding.tasaNominalAnualEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(CheckValidValuesOnInput()){
                    binding.confirmarButton.setEnabled(true);
                }
                else{
                    binding.confirmarButton.setSelected(false);
                    binding.confirmarButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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