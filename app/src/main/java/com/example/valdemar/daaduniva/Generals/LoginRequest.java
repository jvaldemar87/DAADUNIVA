package com.example.valdemar.daaduniva.Generals;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    static String url = "http://direccionweb.com/login.php";
    private static final String LOGIN_REQUEST_URL = url;
    private Map<String,String> params;
    public LoginRequest(String correo, String contrasena, Response.Listener<String> listener){
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("correo",correo);
        params.put("contrasena",contrasena);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
