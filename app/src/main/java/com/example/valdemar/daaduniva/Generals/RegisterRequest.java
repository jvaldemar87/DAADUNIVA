package com.example.valdemar.daaduniva.Generals;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    static String url = "http://direccionweb.com/register";
    private static final String REGISTER_REQUEST_URL = url;
    private Map<String,String> params;
    public RegisterRequest(String nombre, String apellidoP, String apellidoM, String correo, String contrasena, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("nombre",nombre);
        params.put("apellidoP",apellidoP);
        params.put("apellidoM",apellidoM);
        params.put("correo",correo);
        params.put("contrasena",contrasena);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
