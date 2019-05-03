package com.appmoviles.examenpractico2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase rtdb;

    private EditText et_nombre;
    private EditText et_edad;
    private RadioGroup radio_group_genero;
    private RadioGroup radio_group_heroes;
    private Button btn_enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rtdb = FirebaseDatabase.getInstance();

        et_nombre = findViewById(R.id.et_nombre);
        et_edad = findViewById(R.id.et_edad);
        radio_group_genero = findViewById(R.id.radio_group_genero);
        radio_group_heroes = findViewById(R.id.radio_group_heroes);
        btn_enviar = findViewById(R.id.btn_enviar);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = et_nombre.getText().toString();
                int edad = Integer.parseInt(et_edad.getText().toString());

                int radioButtonId = radio_group_genero.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                String genero = radioButton.getText().toString();

                radioButtonId = radio_group_heroes.getCheckedRadioButtonId();
                radioButton = findViewById(radioButtonId);
                String heroe = radioButton.getText().toString();

                if(!nombre.equals("") && edad>0){
                    Voto voto = new Voto(nombre, edad, genero, heroe);
                    if(genero.equals("Hombre")){
                        if(edad>=18){
                            rtdb.getReference().child("votos").child("HombresAdultos").push().setValue(voto);
                        }
                        else if(edad>11 && edad<18){
                            rtdb.getReference().child("votos").child("HombresAdolescentes").push().setValue(voto);
                        }
                    }
                    else if(genero.equals("Mujer")){
                        if(edad>=18){
                            rtdb.getReference().child("votos").child("MujeresAdultas").push().setValue(voto);
                        }
                        else if(edad>11 && edad<18){
                            rtdb.getReference().child("votos").child("MujeresAdolescentes").push().setValue(voto);
                        }
                    }
                    if (edad<12){
                        rtdb.getReference().child("votos").child("ninos").push().setValue(voto);
                    }

                    rtdb.getReference().child("votos").child("todos").push().setValue(voto);

                    Intent i = new Intent(MainActivity.this, Resultados.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "Verifica tu nombre o tu edad", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
