package com.example.massivehomedashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.massivehomedashboard.db.DbProspectos;
import com.example.massivehomedashboard.entidades.E_Prospecto;
import com.example.massivehomedashboard.tools.validations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FormEditProspectosActivity extends AppCompatActivity {

    EditText txtNombre, txtEmpresa, txtTelefono, txtEmail,txtComentarios;
    String str_nombre, str_empresa,str_telefono,str_mail,str_comentarios;
    Button btn_edit_record;
    int id = 0;
    validations oValidation = new validations();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit_prospectos);

        try {

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

        }catch (Exception exp){
            System.out.println(exp.getMessage());
        }

        txtNombre = findViewById(R.id.editTextName);
        txtEmpresa = findViewById(R.id.editTextEmpresa);
        txtTelefono = findViewById(R.id.editTextPhone);
        txtEmail = findViewById(R.id.editTextEmail);
        txtComentarios = findViewById(R.id.editTextComentarios);
        btn_edit_record = findViewById(R.id.btn_edit_record);

        DbProspectos oDbProspectos = new DbProspectos(FormEditProspectosActivity.this);
        ArrayList<E_Prospecto> oProspecto;
        String filtro = " WHERE id = "+id+" LIMIT 1 ";
        oProspecto = oDbProspectos.getProspectos(filtro);

        txtNombre.setText(oProspecto.get(0).getNombre_contacto());
        txtEmpresa.setText(oProspecto.get(0).getEmpresa());
        txtTelefono.setText(oProspecto.get(0).getTelefono());
        txtEmail.setText(oProspecto.get(0).getEmail());
        txtComentarios.setText(oProspecto.get(0).getComentarios());


        btn_edit_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //long id = 0;

                str_nombre = txtNombre.getText().toString().trim();
                str_empresa = txtEmpresa.getText().toString().trim();
                str_telefono = txtTelefono.getText().toString().trim();
                str_mail = txtEmail.getText().toString().trim();
                str_comentarios = txtComentarios.getText().toString().trim();

                if(str_nombre.isEmpty()){
                    Toast.makeText(FormEditProspectosActivity.this,"Capture nombre de contacto",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(str_telefono.isEmpty() && str_mail.isEmpty()){
                    Toast.makeText(FormEditProspectosActivity.this,"Capture un numero de telefono o correo electronico",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!str_mail.isEmpty()){
                    if(!oValidation.validEmail(str_mail)){
                        Toast.makeText(FormEditProspectosActivity.this,"Formato de correo invalido",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(!str_telefono.isEmpty()){
                    if(!oValidation.validPhone(str_telefono)){
                        Toast.makeText(FormEditProspectosActivity.this,"Formato de telefono invalido",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String str_created_at = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                HashMap<String,String> oHasMap = new HashMap<String, String>();
                oHasMap.put("id", ""+id);
                oHasMap.put("nombre",str_nombre);
                oHasMap.put("email",str_mail);
                oHasMap.put("telefono",str_telefono);
                oHasMap.put("empresa",str_empresa);
                oHasMap.put("comentario",str_comentarios);

                if(oDbProspectos.update(oHasMap)){
                    Toast.makeText(FormEditProspectosActivity.this,"Registro Actualizado",Toast.LENGTH_LONG).show();
                    Intent oIntent = new Intent(FormEditProspectosActivity.this,ViewProspectoActivity.class);
                    oIntent.putExtra("id",id);
                    startActivity(oIntent);
                }else{
                    Toast.makeText(FormEditProspectosActivity.this,"Problema al actualizar información",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}