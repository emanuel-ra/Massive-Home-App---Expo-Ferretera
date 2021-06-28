package com.example.massivehomedashboard.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.massivehomedashboard.R;
import com.example.massivehomedashboard.ViewProspectoActivity;
import com.example.massivehomedashboard.entidades.E_Prospecto;

import java.util.ArrayList;

public class ProspectosListAdapter extends RecyclerView.Adapter<ProspectosListAdapter.ProspectosViewHolder> {
    @NonNull
    ArrayList<E_Prospecto> listProspectos;

    public ProspectosListAdapter(ArrayList<E_Prospecto> listProspectos){
        this.listProspectos = listProspectos;
    }
    @Override
    public ProspectosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_prospectos,null,false);
        return  new ProspectosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProspectosListAdapter.ProspectosViewHolder holder, int position) {
        String Nombre = listProspectos.get(position).getNombre_contacto();
        String Empresa = listProspectos.get(position).getEmpresa();
        String Telefono = listProspectos.get(position).getTelefono();
        String Correo = listProspectos.get(position).getEmail();
        holder.vwNombre.setText("Nombre: "+Nombre);
        holder.vwEmpresa.setText("Empresa: "+Empresa);
        holder.vwEmail.setText("Correo: "+Correo);
        holder.vwTelefono.setText("Telefono: "+Telefono);
    }

    @Override
    public int getItemCount() {
        return listProspectos.size();
    }

    public class ProspectosViewHolder extends RecyclerView.ViewHolder {
        TextView vwNombre,vwEmpresa,vwTelefono,vwEmail;
        public ProspectosViewHolder(@NonNull View itemView) {
            super(itemView);
            vwNombre = itemView.findViewById(R.id.vwNombre);
            vwEmpresa = itemView.findViewById(R.id.vwEmpresa);
            vwTelefono = itemView.findViewById(R.id.vwTelefono);
            vwEmail = itemView.findViewById(R.id.vwEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context oContext = v.getContext();
                    Intent oIntent = new Intent(oContext, ViewProspectoActivity.class);
                    oIntent.putExtra("id",listProspectos.get(getAdapterPosition()).getId());
                    oContext.startActivity(oIntent);
                }
            });
        }
    }
}
