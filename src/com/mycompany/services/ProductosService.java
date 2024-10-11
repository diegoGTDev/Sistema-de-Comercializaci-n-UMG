/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.mycompany.models.Cliente;
import com.mycompany.models.Producto;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author PC
 */
public class ProductosService {

    private static String apiURL = "http://localhost/sgc_api/producto";
    private static ProductosService _instance;

    public ProductosService getInstance() {
        if (_instance == null) {
            _instance = new ProductosService();
        }
        return _instance;
    }

    public static void Post(String codigo, String nombre, int disponible, int precio_compra, int precio_venta, int proveedor_id) {
        try {
            JSONObject producto = new JSONObject();
            producto.put("codigo", codigo);
            producto.put("nombre", nombre);
            producto.put("disponible", disponible);
            producto.put("precio_compra", precio_compra);
            producto.put("precio_venta", precio_venta);
            producto.put("proveedor_id", proveedor_id);
            String productoString = producto.toString();
            System.out.println("json: " + productoString);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiURL + "/crearProducto.php"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(productoString))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = postResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static Producto Get(String codigoConsulta) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(apiURL + "/consultarProducto.php?codigo=" + codigoConsulta)).build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            System.out.println("STRING: " + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.length() > 0) {
                Producto producto = new Producto();
                producto.setCodigo(jsonObject.getString("Codigo"));
                producto.setNombre(jsonObject.getString("Nombre"));
                producto.setDisponible(Integer.parseInt(jsonObject.getString("Disponible").toString()));
                producto.setPrecio_Venta(Integer.parseInt(jsonObject.getString("Precio_Venta")));
                producto.setPrecio_Compra(Integer.parseInt(jsonObject.getString("Precio_Compra")));
                producto.setProveedor(jsonObject.getString("Proveedor"));
                producto.setProveedor_id(jsonObject.getInt("Proveedor_id"));
                return producto;
            } else {

                return null;
            }
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Producto> getAll() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(apiURL + "/consultarProductos.php")).build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            JSONArray jsonArray = new JSONArray(jsonString);
            ArrayList<Producto> productos = new ArrayList<>();
            if (jsonArray.length() < 1) {
                JOptionPane.showMessageDialog(null, "No fue posible obtener los datos");
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 8. Extraer los datos del cliente
Producto producto = new Producto();
                producto.setCodigo(jsonObject.getString("Codigo"));
                producto.setNombre(jsonObject.getString("Nombre"));
                producto.setDisponible(Integer.parseInt(jsonObject.getString("Disponible").toString()));
                producto.setPrecio_Venta(Integer.parseInt(jsonObject.getString("Precio_Venta")));
                producto.setPrecio_Compra(Integer.parseInt(jsonObject.getString("Precio_Compra")));
                producto.setProveedor(jsonObject.getString("Proveedor"));
                producto.setProveedor_id(jsonObject.getInt("Proveedor_id"));
                productos.add(producto);
            }
            return productos;
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void PUT(Producto p) {
        try {
            JSONObject producto = new JSONObject();
            producto.put("codigo", p.getCodigo());
            producto.put("nombre", p.getNombre());
            producto.put("disponible", p.getDisponible());
            producto.put("precio_compra", p.getPrecio_Compra());
            producto.put("precio_venta", p.getPrecio_Venta());
            producto.put("proveedor_id", p.getProveedor_id());
            String productoString = producto.toString();
            System.out.println("Producto String: " + productoString);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiURL + "/actualizarProducto.php"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(productoString))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = postResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();

        }
    }

    public static Boolean Delete(String codigo) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            System.out.println("El nit es:" + codigo);
            HttpRequest deleteRequest = HttpRequest.newBuilder().uri(URI.create(apiURL + "/eliminarProducto.php?codigo=" + codigo)).DELETE().build();
            HttpResponse<String> getResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            if (jsonString.length() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return false;
    }

}
