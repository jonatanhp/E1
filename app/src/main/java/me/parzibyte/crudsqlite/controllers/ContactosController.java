package me.parzibyte.crudsqlite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.parzibyte.crudsqlite.AyudanteBaseDeDatos;
import me.parzibyte.crudsqlite.modelos.Contacto;


public class ContactosController {
    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "contactos";

    public ContactosController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarContacto(Contacto contacto) {

        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(contacto.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaContacto(Contacto contacto) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("nombre", contacto.getNombre());
        valoresParaInsertar.put("apellido", contacto.getApellido());
        valoresParaInsertar.put("telefono", contacto.getTelefono());
        valoresParaInsertar.put("email", contacto.getEmail());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Contacto contactoEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("nombre", contactoEditada.getNombre());
        valoresParaActualizar.put("apellido", contactoEditada.getApellido());
        valoresParaActualizar.put("telefono", contactoEditada.getTelefono());
        valoresParaActualizar.put("email", contactoEditada.getEmail());
        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idContacto
        String[] argumentosParaActualizar = {String.valueOf(contactoEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Contacto> obtenerContactos() {
        ArrayList<Contacto> contactos = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"nombre", "apellido", "telefono" , "email", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from contactos
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return contactos;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return contactos;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de contactos
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String nombreObtenidoDeBD = cursor.getString(0);
            String apellidoObtenidoDeBD = cursor.getString(1);
            String telefonoObtenidoDeBD = cursor.getString(2);
            String emailObtenidoDeBD = cursor.getString(3);
            long idContacto = cursor.getLong(4);
            Contacto contactoObtenidaDeBD = new Contacto(nombreObtenidoDeBD, apellidoObtenidoDeBD, 
            telefonoObtenidoDeBD,emailObtenidoDeBD, idContacto);
            contactos.add(contactoObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de contactos :)
        cursor.close();
        return contactos;
    }
}