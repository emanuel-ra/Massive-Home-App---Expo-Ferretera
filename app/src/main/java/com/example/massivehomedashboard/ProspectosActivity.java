package com.example.massivehomedashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.massivehomedashboard.adaptadores.ProspectosListAdapter;
import com.example.massivehomedashboard.db.DbProspectos;
import com.example.massivehomedashboard.entidades.E_Prospecto;

import java.util.ArrayList;

public class ProspectosActivity extends AppCompatActivity {

    CardView oCardViewAdd;
    RecyclerView listaProspectos;
    ArrayList<E_Prospecto> listArrayProspectos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospectos);

        listaProspectos = findViewById(R.id.recyclerviewProspectos);
        listaProspectos.setLayoutManager(new LinearLayoutManager(this));

        DbProspectos oDbProspectos = new DbProspectos(ProspectosActivity.this);

        listArrayProspectos = new ArrayList<>();

        String filter = " ORDER BY nombre_contacto ASC ";
        listArrayProspectos = oDbProspectos.getProspectos(filter);
        ProspectosListAdapter adapter = new ProspectosListAdapter(listArrayProspectos);

        listaProspectos.setAdapter(adapter);

    }
    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_prospectos,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.NewRecordMenuOption:
                newRecord();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
    private void newRecord(){
        Intent oIntent = new Intent(ProspectosActivity.this,ProspectosAddActivity.class);
        startActivity(oIntent);
    }
}