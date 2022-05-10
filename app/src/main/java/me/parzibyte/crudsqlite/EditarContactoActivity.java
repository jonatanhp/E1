package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.ContactosController;
import me.parzibyte.crudsqlite.modelos.Contacto;

public class EditarContactoActivity extends AppCompatActivity {
    private EditText etEditarNombre, etEditarEdad;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Contacto contactos;//La contactos que vamos a estar editando
    private ContactosController contactossController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contactos);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las contactoss
        contactossController = new ContactosController(EditarContactoActivity.this);

        // Rearmar la contactos
        // Nota: igualmente solamente podríamos mandar el id y recuperar la contactos de la BD
        long idContacto = extras.getLong("idContacto");
        String nombreContacto = extras.getString("nombreContacto");
        int edadContacto = extras.getInt("edadContacto");
        contactos = new Contacto(nombreContacto, edadContacto, idContacto);


        // Ahora declaramos las vistas
        etEditarEdad = findViewById(R.id.etEditarEdad);
        etEditarNombre = findViewById(R.id.etEditarNombre);
        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionContacto);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosContacto);


        // Rellenar los EditText con los datos de la contactos
        etEditarEdad.setText(String.valueOf(contactos.getEdad()));
        etEditarNombre.setText(contactos.getNombre());

        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etEditarNombre.setError(null);
                etEditarEdad.setError(null);
                // Crear la contactos con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoNombre = etEditarNombre.getText().toString();
                String posibleNuevaEdad = etEditarEdad.getText().toString();
                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Escribe el nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if (posibleNuevaEdad.isEmpty()) {
                    etEditarEdad.setError("Escribe la edad");
                    etEditarEdad.requestFocus();
                    return;
                }
                // Si no es entero, igualmente marcar error
                int nuevaEdad;
                try {
                    nuevaEdad = Integer.parseInt(posibleNuevaEdad);
                } catch (NumberFormatException e) {
                    etEditarEdad.setError("Escribe un número");
                    etEditarEdad.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                Contacto contactosConNuevosCambios = new Contacto(nuevoNombre, nuevaEdad, contactos.getId());
                int filasModificadas = contactossController.guardarCambios(contactosConNuevosCambios);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarContactoActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
