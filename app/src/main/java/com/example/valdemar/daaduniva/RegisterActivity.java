package com.example.valdemar.daaduniva;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.valdemar.daaduniva.Services.ApiServices;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextNombre, editTextApellidoP,editTextApellidoM,editTextMail, editTextPass;
    Button buttonRegistro;

    Context context = this;

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

        if (!check(nombre)) {
            Toast.makeText(context, "Ingresa tu nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!check(apellidoP)) {
            Toast.makeText(context, "Ingresa tu apellido materno.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!check(apellidoM)) {
            Toast.makeText(context, "Ingresa tu apellido paterno.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!check(correo)) {
            Toast.makeText(context, "Ingresa un correo.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!check(contrasena)) {
            Toast.makeText(context, "Ingresa una contraseña.", Toast.LENGTH_SHORT).show();
            return;
        }

        ServerRegister();
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


    }

    private boolean check(String text){
        if (text.equals(""))
            return false;
        else
            return true;
    }

    private void ServerRegister(){
        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidoP.getText().toString() + " " + editTextApellidoM.getText().toString();
        final String email = editTextMail.getText().toString();
        String password = editTextPass.getText().toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW"),
                "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                        "Content-Disposition: form-data; name=\"name\"\r\n\r\n" +nombre+ "\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                        "Content-Disposition: form-data; name=\"laste_names\"\r\n\r\n" +apellidos+"\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                        "Content-Disposition: form-data; name=\"email\"\r\n\r\n"+email+"\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                        "Content-Disposition: form-data; name=\"password\"\r\n\r\n"+password+"\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW--");

        ApiServices.user service = ApiServices.getRetrofitInstance().create(ApiServices.user.class);
        Call<Integer> call = service.register(requestBody);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                try{
                    if (response.body() == 1){
                        SharedPreferences preferences;
                        preferences = getSharedPreferences("cache_univa",0);
                        preferences.edit().putString("email", email).commit();

                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                catch (NullPointerException e){
                    Toast.makeText(context, "El usuario ya existe.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Integer>  call, Throwable t) {
                Toast.makeText(context, "Problemas de petición.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
