package com.example.valdemar.daaduniva;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.valdemar.daaduniva.Generals.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextNombre, editTextApellidoP,editTextApellidoM,editTextMail, editTextPass;
    Button buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidoP = findViewById(R.id.editTextApellidoP);
        editTextApellidoM = findViewById(R.id.editTextApellidoM);
        editTextMail = findViewById(R.id.editTextMail);
        editTextPass = findViewById(R.id.editTextPass);
        buttonRegistro = findViewById(R.id.buttonRegistro);

        buttonRegistro.setOnClickListener(this);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        final String nombre = editTextNombre.getText().toString();
        final String apellidoP = editTextApellidoP.getText().toString();
        final String apellidoM = editTextApellidoM.getText().toString();
        final String correo = editTextMail.getText().toString();
        final String contrasena = editTextPass.getText().toString();
        /*

        // NO BORRAR ESTA COMENTADO PARA PODER HACER PRUEBAS
        Response.Listener<String> respoListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if(success){
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Error en registro")
                                .setNegativeButton("Retry",null)
                                .create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(nombre,apellidoP,apellidoM,correo,contrasena,respoListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);*/

        Toast.makeText(this, "" +
                "nombre:" + nombre+"\n" +
                "apellido_paterno:" + apellidoP+"\n" +
                "apellido_materno:" + apellidoM+"\n" +
                "correo:" +correo+"\n" +
                "contraseña:" +contrasena, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
