package com.example.massivehomedashboard;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.massivehomedashboard.db.DbProspectos;
import com.example.massivehomedashboard.tools.validations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ProspectosAddActivity extends AppCompatActivity {

    validations oValidation = new validations();

    Button btn_new_record;
    EditText txtNombre, txtEmpresa, txtTelefono, txtEmail,txtComentarios;
    String str_nombre, str_empresa,str_telefono,str_mail,str_comentarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospectos_add);

        btn_new_record = findViewById(R.id.btn_new_record);
        txtNombre = findViewById(R.id.editTextName);
        txtEmpresa = findViewById(R.id.editTextEmpresa);
        txtTelefono = findViewById(R.id.editTextPhone);
        txtEmail = findViewById(R.id.editTextEmail);
        txtComentarios = findViewById(R.id.editTextComentarios);

        btn_new_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addProspectos();
                }catch (Exception ex){
                    Toast.makeText(ProspectosAddActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private void addProspectos(){

        DbProspectos oDbProspectos = new DbProspectos(ProspectosAddActivity.this);

        long id = 0;

        str_nombre = txtNombre.getText().toString().trim();
        str_empresa = txtEmpresa.getText().toString().trim();
        str_telefono = txtTelefono.getText().toString().trim();
        str_mail = txtEmail.getText().toString().trim();
        str_comentarios = txtComentarios.getText().toString().trim();

        if(str_nombre.isEmpty()){
            Toast.makeText(ProspectosAddActivity.this,"Capture nombre de contacto",Toast.LENGTH_SHORT).show();
            return;
        }

        if(str_telefono.isEmpty() && str_mail.isEmpty()){
            Toast.makeText(ProspectosAddActivity.this,"Capture un numero de telefono o correo electronico",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!str_mail.isEmpty()){
            if(!oValidation.validEmail(str_mail)){
                Toast.makeText(ProspectosAddActivity.this,"Formato de correo invalido",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(!str_telefono.isEmpty()){
            if(!oValidation.validPhone(str_telefono)){
                Toast.makeText(ProspectosAddActivity.this,"Formato de telefono invalido",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String str_created_at = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        HashMap<String,String> oHasMap = new HashMap<String, String>();
        oHasMap.put("nombre",str_nombre);
        oHasMap.put("email",str_mail);
        oHasMap.put("telefono",str_telefono);
        oHasMap.put("empresa",str_empresa);
        oHasMap.put("comentario",str_comentarios);
        oHasMap.put("created_at",str_created_at.toString());

        //Toast.makeText(ProspectosAddActivity.this,currentTime,Toast.LENGTH_SHORT).show();

        id = oDbProspectos.insert(oHasMap);

        if(id>0){
            Toast.makeText(ProspectosAddActivity.this,"Registro Guardado",Toast.LENGTH_LONG).show();
            txtNombre.setText("");
            txtEmpresa.setText("");
            txtTelefono.setText("");
            txtEmail.setText("");
            txtComentarios.setText("");

            Intent oIntent = new Intent(ProspectosAddActivity.this,ViewProspectoActivity.class);
            oIntent.putExtra("id",id);
            startActivity(oIntent);
        }else{
            Toast.makeText(ProspectosAddActivity.this,"Error al Guardar",Toast.LENGTH_LONG).show();
        }

    }
}