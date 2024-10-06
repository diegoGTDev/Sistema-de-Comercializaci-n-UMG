package com.mycompany.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC
 */
public class UsuarioService {
    private String api_url = "http://localhost/sgc_api";
    
    public UsuarioService(){
        
    }
    
    public boolean iniciarSesion(String username_login, String userpasssword_login){
        String request_url = api_url + "/login.php?nombre="+username_login+"&password="+userpasssword_login;
        StringBuilder resultado = new StringBuilder();
        try{
            URL url = new URL(request_url);
            HttpURLConnection conexion = (HttpURLConnection)url.openConnection();
            conexion.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String linea;
            while((linea = rd.readLine()) !=null){
                resultado.append(linea);
            }
            rd.close();
        }catch(Exception e){
            e.printStackTrace();
        }
       
        String result = resultado.toString();
        System.out.println("Resultado es: " + result);
        if(result.contains("1")){
            return true;
        }
        else{
            return false;
        }
    }
}
