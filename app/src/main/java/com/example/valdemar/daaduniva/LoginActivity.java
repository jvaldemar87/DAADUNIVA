package com.example.valdemar.daaduniva;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valdemar.daaduniva.Generals.WiFi;
import com.example.valdemar.daaduniva.Services.ApiServices;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;

    TextView textViewBotonRegistro;
    Button buttonLogin, buttonRegister;
    EditText editTextMail,editTextPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextMail = findViewById(R.id.editTextMail);
        editTextPass = findViewById(R.id.editTextPass);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegister:
                Intent intentRegister = new Intent(this,RegisterActivity.class);
                LoginActivity.this.startActivity(intentRegister);
                break;
            case R.id.buttonLogin:
                if (!check(editTextMail.getText().toString())) {
                    Toast.makeText(context, "Ingresa un correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!check(editTextPass.getText().toString())) {
                    Toast.makeText(context, "Ingresa una contraseña.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (WiFi.connectionWifi(context))
                        ServerLogin();
                }
                /*
                //NO BORRAR
                final String correo = editTextMail.getText().toString();
                final String contrasena = editTextPass.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Error en login")
                                        .setNegativeButton("Retry",null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(correo,contrasena,responseListener);*/

                break;
        }
    }

    private boolean check(String text){
        if (text.equals(""))
            return false;
        else
            return true;
    }

    private void ServerLogin(){

        final String email = editTextMail.getText().toString();
        final String password = editTextPass.getText().toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW"),
                "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                "Content-Disposition: form-data; name=\"email\"\r\n\r\n"+ email + "\r\n" +
                "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                "Content-Disposition: form-data; name=\"password\"\r\n\r\n"+ password +"\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW--");

        ApiServices.user service = ApiServices.getRetrofitInstance().create(ApiServices.user.class);
        Call<Integer> call = service.login(requestBody);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if (response.body() == 1){
                    SharedPreferences preferences;
                    preferences = getSharedPreferences("cache_univa",0);
                    preferences.edit().putString("email", email).commit();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Usuario incorrecto.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer>  call, Throwable t) {
                Toast.makeText(context, "Problemas de petición.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
