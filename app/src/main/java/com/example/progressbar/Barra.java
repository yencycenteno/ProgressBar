package com.example.progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Barra extends AppCompatActivity {

    Handler h = new Handler();
    ProgressBar proBar;
    AutoCompleteTextView Fruta, Animal, Lenguaje;
    String [] fruta = {"Mango", "Jocote", "Piña", "Papaya"};
    String [] animal = {"León", "Gato", "Perro", "Tigre"};
    String [] lenguaje = {"C#", "C++", "Java", "PHP"};
    int i = 0;
    boolean isActivo = false;
    TextView porcentaje, bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        bar = findViewById(R.id.bar);
        porcentaje = findViewById(R.id.porcentaje);
        proBar = findViewById(R.id.proBar);
        Fruta = findViewById(R.id.Fruta);
        Animal = findViewById(R.id.Animal);
        Lenguaje = findViewById(R.id.Lenguaje);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, fruta);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, animal);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, lenguaje);

        bar.setText("Bienvenido " + getIntent().getStringExtra("Correo"));
        Fruta.setThreshold(1);
        Animal.setThreshold(2);
        Lenguaje.setThreshold(3);

        Fruta.setAdapter(adapter1);
        Animal.setAdapter(adapter2);
        Lenguaje.setAdapter(adapter3);
    }

    public void OnClickProcesar(View v){
        if(Fruta.getText().toString().isEmpty() && Animal.getText().toString().isEmpty() && Lenguaje.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite la fruta, el animal y el leguaje de programación favorito", Toast.LENGTH_SHORT).show();
        } else if(Fruta.getText().toString().isEmpty() && Animal.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite la fruta y el animal favorito", Toast.LENGTH_SHORT).show();
        } else if(Animal.getText().toString().isEmpty() && Lenguaje.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite el animal y el lenguaje de programación favorito", Toast.LENGTH_SHORT).show();
        } else if(Fruta.getText().toString().isEmpty() && Lenguaje.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite el fruta y el lenguaje de programación favorito", Toast.LENGTH_SHORT).show();
        } else if(Fruta.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite el fruta favorita", Toast.LENGTH_SHORT).show();
        } else if(Animal.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite el animal favorito", Toast.LENGTH_SHORT).show();
        } else if(Lenguaje.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite el lenguaje de programación favorito", Toast.LENGTH_SHORT).show();
        } else{
            if(!isActivo){
                Thread hr = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(i <= 100){
                            h.post(new Runnable() {
                                @Override
                                public void run() {
                                    porcentaje.setText(i +" %");
                                    proBar.setProgress(i);
                                }
                            });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(i == 100) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Fruta: " + Fruta.getText().toString() + "\n" +
                                                "Animal: " + Animal.getText().toString() + "\n" +
                                                "Lenguaje: " + Lenguaje.getText().toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            i++;
                            isActivo = true;
                        }
                    }
                });
                hr.start();
            }
        }
    }
}
