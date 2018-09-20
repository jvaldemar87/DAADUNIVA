package com.example.valdemar.daaduniva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valdemar.daaduniva.Models.BaseRespond;
import com.example.valdemar.daaduniva.Services.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
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
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(intent);
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

    private void ServerLogin(String email, String password){
        ApiServices.user service = ApiServices.getRetrofitInstance().create(ApiServices.user.class);
        Call<BaseRespond> call = service.login(email, password);

        call.enqueue(new Callback<BaseRespond>() {
            @Override
            public void onResponse(Call<BaseRespond> call, retrofit2.Response<BaseRespond> response) {

            }

            @Override
            public void onFailure(Call<BaseRespond>  call, Throwable t) {

            }
        });
    }
}
