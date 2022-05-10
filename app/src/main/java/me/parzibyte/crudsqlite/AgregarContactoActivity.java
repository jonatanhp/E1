package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.ContactosController;
import me.parzibyte.crudsqlite.modelos.Contacto;

public class AgregarContactoActivity extends AppCompatActivity {
    private Button btnAgregarContacto, btnCancelarNuevaContacto;
    private EditText etNombre, etApellido, etTelefono, etEmail;
    private ContactosController contactosController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        btnAgregarContacto = findViewById(R.id.btnAgregarContacto);
        btnCancelarNuevaContacto = findViewById(R.id.btnCancelarNuevaContacto);
        // Crear el controlador
        contactosController = new ContactosController(AgregarContactoActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etNombre.setError(null);
                etApellido.setError(null);
                etTelefono.setError(null);
                etEmail.setError(null);
                String nombre = etNombre.getText().toString(),
                       String apellido = etApellido.getText().toString(),
                       String telefono = etTelefono.getText().toString(),
                       String email = etEmail.getText().toString();
                if ("".equals(nombre)) {
                    etNombre.setError("Escribe el nombre del contacto");
                    etNombre.requestFocus();
                    return;
                }
                if ("".equals(apellido)) {
                    etApellido.setError("Escribe el apellido del contacto");
                    etApellido.requestFocus();
                    return;
                }

                if ("".equals(telefono)) {
                    etTelefono.setError("Escribe el teléfono del contacto");
                    etTelefono.requestFocus();
                    return;
                }

                if ("".equals(email)) {
                    etEmail.setError("Escribe el email del contacto");
                    etEmail.requestFocus();
                    return;
                }

                // Crear el contacto
                // Ya pasó la validación
                Contacto nuevaContacto = new Contacto(nombre, apellido, telefono, email);
                long id = contactosController.nuevaContacto(nuevaContacto);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarContactoActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
