package me.parzibyte.crudsqlite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.parzibyte.crudsqlite.modelos.Contacto;

public class AdaptadorContactos extends RecyclerView.Adapter<AdaptadorContactos.MyViewHolder> {

    private List<Contacto> listaDeContactos;

    public void setListaDeContactos(List<Contacto> listaDeContactos) {
        this.listaDeContactos = listaDeContactos;
    }

    public AdaptadorContactos(List<Contacto> contactos) {
        this.listaDeContactos = contactos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaContacto = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_contacto, viewGroup, false);
        return new MyViewHolder(filaContacto);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la contacto de nuestra lista gracias al índice i
        Contacto contacto = listaDeContactos.get(i);

        // Obtener los datos de la lista
        String nombreContacto = contacto.getNombre();
        String apellidoContacto = contacto.getApellido();
        String telefonoContacto = contacto.getTelefono();
        String emailContacto = contacto.getEmail();
        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombreContacto);
        myViewHolder.apellido.setText(apellidoContacto);
        myViewHolder.telefono.setText(telefonoContacto);
        myViewHolder.email.setText(emailContacto);
    }

    @Override
    public int getItemCount() {
        return listaDeContactos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, apellido, telefono, email;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.apellido = itemView.findViewById(R.id.tvApellido);
            this.telefono = itemView.findViewById(R.id.tvTelefono);
            this.email = itemView.findViewById(R.id.tvEmail);
        }
    }
}
