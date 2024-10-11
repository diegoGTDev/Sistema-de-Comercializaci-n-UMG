/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;
import com.mycompany.models.Cliente;
import com.mycompany.models.Proveedor;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author PC
 */
public class ProveedorService {
    private static String apiUrl = "http://localhost/sgc_api/proveedor";
    private static ProveedorService _instance;

    public ProveedorService() {
    }
    
    private static ProveedorService getInstance(){
        if (_instance == null){
            _instance = new ProveedorService();
        }
        return _instance;
    }
    public static void post(Proveedor _proveedor){
        try{
            JSONObject proveedor = new JSONObject();
            proveedor.put("nombre", _proveedor.getNombre());
            proveedor.put("direccion", _proveedor.getDireccion());
            proveedor.put("telefono", _proveedor.getTelefono());
            String proveedorString = proveedor.toString();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest postRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl+"/proveedores.php")).header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(proveedorString))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = postResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
        }catch(Exception ex ){
            ex.printStackTrace();
        }
    }
    public static Proveedor get(int id){
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + "/proveedores.php?proveedor_id="+id)).GET().build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
            Proveedor proveedor = new Proveedor();
            if(jsonObject.length() < 1){
                JOptionPane.showMessageDialog(null, "No fue posible obtener la información");
                return null;
            }
            proveedor.setNombre(jsonObject.getString("nombre"));
            proveedor.setDireccion(jsonObject.getString("direccion"));
            proveedor.setTelefono(jsonObject.getString("telefono"));
            proveedor.setProveedor_id(Integer.parseInt(jsonObject.getString("proveedor_id")));
            
            return proveedor;
        }catch(IOException | InterruptedException | JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Proveedor> getAll(){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + "/proveedores.php")).GET().build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            JSONArray jsonArray = new JSONArray(jsonString);
            ArrayList<Proveedor> proveedores = new ArrayList<>();
            if (jsonArray.length() < 1){
                JOptionPane.showMessageDialog(null, "No fue posible obtener los datos");
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 8. Extraer los datos del cliente
                int id = Integer.parseInt(jsonObject.getString("proveedor_id"));
                String nombre = jsonObject.getString("nombre");
                String direccion = jsonObject.getString("direccion");
                String telefono = jsonObject.getString("telefono");
                // 9. Crear un objeto Cliente y añadirlo a la lista
                Proveedor proveedor = new Proveedor(nombre, direccion, telefono);
                proveedor.setProveedor_id(id);
                proveedores.add(proveedor);
            }
            return proveedores;
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static void PUT(Proveedor _proveedor){
        try{
            JSONObject proveedor = new JSONObject();
            proveedor.put("proveedor_id", _proveedor.getProveedor_id());
            proveedor.put("nombre", _proveedor.getNombre());
            proveedor.put("direccion", _proveedor.getDireccion());
            proveedor.put("telefono", _proveedor.getTelefono());
            String proveedorString = proveedor.toString();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest putRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl+"/proveedores.php")).header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(proveedorString))
                    .build();
            HttpResponse<String> postResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = postResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
        }catch(Exception ex ){
            ex.printStackTrace();
        }
    }
    
    public static void Delete(int id){
        try{
  
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest deleteRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + "/proveedores.php?proveedor_id="+id)).DELETE().build();
        HttpResponse<String> getResponse= client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        String jsonString = getResponse.body();
        JSONObject jsonObject = new JSONObject(jsonString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
