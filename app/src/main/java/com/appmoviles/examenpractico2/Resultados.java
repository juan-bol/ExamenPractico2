package com.appmoviles.examenpractico2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Resultados extends AppCompatActivity {

    FirebaseDatabase rtdb;

    private TextView tv_informacion;
    private TextView tv_resultados;
    private RadioGroup radio_group_resultados;
    private Button btn_consultar;
    private Button btn_regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        rtdb = FirebaseDatabase.getInstance();

        tv_informacion = findViewById(R.id.tv_informacion);
        tv_resultados = findViewById(R.id.tv_resultados);
        radio_group_resultados = findViewById(R.id.radio_group_resultados);
        btn_consultar = findViewById(R.id.btn_consultar);
        btn_regresar = findViewById(R.id.btn_regresar);

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Resultados.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonId = radio_group_resultados.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                final String consulta = radioButton.getText().toString();

                DatabaseReference data = null;
                if(consulta.equals("Todo el público")){
                    data = rtdb.getReference().child("votos").child("todos");
                } else if(consulta.equals("Mujeres adultas")){
                    data = rtdb.getReference().child("votos").child("MujeresAdultas");
                } else if(consulta.equals("Hombres adultos")){
                    data = rtdb.getReference().child("votos").child("HombresAdultos");
                } else if(consulta.equals("Mujeres adolescentes")){
                    data = rtdb.getReference().child("votos").child("MujeresAdolescentes");
                } else if(consulta.equals("Hombres adolescentes")){
                    data = rtdb.getReference().child("votos").child("HombresAdolescentes");
                } else if(consulta.equals("Niños")){
                    data = rtdb.getReference().child("votos").child("ninos");
                }

                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.e("Cantidad de votos", dataSnapshot.getValue()+" ---- "+dataSnapshot.getKey());
                        Log.e("Cantidad de votos", dataSnapshot.getChildrenCount()+"");

                        if(dataSnapshot.getValue()==null){
                            Toast.makeText(Resultados.this, "Aún no hay ningún voto en esta categoría", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            int votosSpiderman=0;
                            int votosIronman = 0;
                            int votosCapitan = 0;
                            int votosCapitana = 0;
                            int votosHulk = 0;
                            int votosViuda = 0;
                            int votosThor = 0;
                            int votosDoctor = 0;

                            int count = 0;

                            for(DataSnapshot hijo : dataSnapshot.getChildren()){
                                Voto voto = hijo.getValue(Voto.class);
                                Log.e("voto", voto.getHeroe());
                                String heroe = voto.getHeroe();
                                if(heroe.equals("Spiderman")){
                                    votosSpiderman++;
                                } else if(heroe.equals("Ironman")){
                                    votosIronman++;
                                } else if(heroe.equals("Capitán América")){
                                    votosCapitan++;
                                } else if(heroe.equals("Capitana marvel")){
                                    votosCapitana++;
                                } else if(heroe.equals("Hulk")){
                                    votosHulk++;
                                } else if(heroe.equals("La viuda Negra")){
                                    votosViuda++;
                                } else if(heroe.equals("Thor")){
                                    votosThor++;
                                }else if(heroe.equals("Doctor Strange")){
                                    votosDoctor++;
                                }
                                count++;
                            }

                            String resultados = "Spiderman ("+(votosSpiderman*100/count)+"%)\n"+
                                    "Ironman ("+(votosIronman*100/count)+"%)\n"+
                                    "Capitán América ("+(votosCapitan*100/count)+"%)\n"+
                                    "Capitana Marvel ("+(votosCapitana*100/count)+"%)\n"+
                                    "Hulk ("+(votosHulk*100/count)+"%)\n"+
                                    "La Viuda Negra ("+(votosViuda*100/count)+"%)\n"+
                                    "Thor ("+(votosThor*100/count)+"%)\n"+
                                    "Doctor Strange ("+(votosDoctor*100/count)+"%)\n";

                            tv_informacion.setText("Los superheroes más populares para "+consulta+" son:");
                            tv_resultados.setText(resultados);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
