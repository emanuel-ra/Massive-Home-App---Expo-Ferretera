package com.example.massivehomedashboard;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.massivehomedashboard.db.DbProspectos;
import com.example.massivehomedashboard.entidades.E_Prospecto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewProspectoActivity extends AppCompatActivity {
    int id = 0;
    TextView vwNameProspecto,vwEmpresa,vwTelefono,vwCorreo,vwComentarios,vwCreated_at;
    FloatingActionButton btnFloatingEditRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prospecto);



        if(savedInstanceState==null){
            Bundle extras = getIntent().getExtras();

            if(extras == null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("id");
            }
        }else{
            id = (int) savedInstanceState.getSerializable("id");
        }

        DbProspectos oDbProspectos = new DbProspectos(ViewProspectoActivity.this);
        ArrayList<E_Prospecto> oProspecto;
        String filtro = " WHERE id = "+id+" LIMIT 1 ";
        oProspecto = oDbProspectos.getProspectos(filtro);

        vwNameProspecto = findViewById(R.id.vwNameProspecto);
        vwEmpresa = findViewById(R.id.vwEmpresa);
        vwTelefono = findViewById(R.id.vwTelefono);
        vwCorreo = findViewById(R.id.vwCorreo);
        vwComentarios = findViewById(R.id.vwComentarios);
        vwCreated_at = findViewById(R.id.vwCreated_at);

        btnFloatingEditRecord = findViewById(R.id.btnFloatingEditRecord);

        vwNameProspecto.setText(oProspecto.get(0).getNombre_contacto());
        vwEmpresa.setText(oProspecto.get(0).getEmpresa());
        vwTelefono.setText(oProspecto.get(0).getTelefono());
        vwCorreo.setText(oProspecto.get(0).getEmail());
        vwComentarios.setText(oProspecto.get(0).getComentarios());
        vwCreated_at.setText(oProspecto.get(0).getCreated_at());

        btnFloatingEditRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(id);
                Intent oIntent = new Intent(ViewProspectoActivity.this,FormEditProspectosActivity.class);
                oIntent.putExtra("id",id);
                startActivity(oIntent);
                //Toast.makeText(ViewProspectoActivity.this,oprospectoid.getId(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}


