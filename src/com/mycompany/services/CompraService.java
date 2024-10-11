/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import com.mycompany.models.Compra;
import com.mycompany.models.DetalleCompra;

/**
/**
 *
 * @author PC
 */
public class CompraService {
    private static String apiUrl = "http://localhost/sgc_api/compra";
    private static CompraService _instance;

    private CompraService() {

    }

    private static CompraService getInstance() {
        if (_instance == null) {
            _instance = new CompraService();
        }
        return _instance;
    }
    public static int getTotalCompras() {
    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/totalCompras.php"))
                .GET()
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        JSONObject response = new JSONObject(getResponse.body());

        return response.getInt("totalCompras");
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}

public static void post(Compra compra, ArrayList<DetalleCompra> detalles) {
    JSONObject compraJSON = new JSONObject();
    compraJSON.put("usuario_id", compra.getUsuario_id());
    compraJSON.put("fecha", compra.getFecha()); // Asumiendo que tienes un método para obtener la fecha
    compraJSON.put("total", compra.getTotal());
    JSONArray detallesArray = new JSONArray();
    for (DetalleCompra detalle : detalles) {
        JSONObject detalleJSON = new JSONObject();
        detalleJSON.put("codigo_producto", detalle.getCodigo_producto());
        detalleJSON.put("unidades", detalle.getUnidades());
        detalleJSON.put("precio_compra", detalle.getPrecio_compra());
        detalleJSON.put("subtotal", detalle.getSubtotal());
        detalleJSON.put("proveedor_id", detalle.getProveedor_id());
        detallesArray.put(detalleJSON); // Agregar el detalle al array
    }
    compraJSON.put("detalles", detallesArray);
    String compraString = compraJSON.toString();
    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/generarCompra.php"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(compraString))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + postResponse.body().toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static Compra getCompra(int compraId) {
    Compra compra = null;
    try {
        String url = apiUrl + "/compra.php?compra_id=" + compraId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            compra = new Compra();
            JSONObject jsonCompra = jsonResponse.getJSONObject("compra");
            compra.setCompra_id(jsonCompra.getInt("compra_id"));
            compra.setFecha(jsonCompra.getString("fecha"));
            compra.setUsuario_id(jsonCompra.getInt("usuario_id"));
            compra.setUsuario(jsonCompra.getString("usuario"));
            //compra.setTotal(Integer.parseInt(jsonCompra.getString("total")));
            compra.setTotal(jsonCompra.getInt("total"));
            JSONArray jsonArray = jsonCompra.getJSONArray("detalles");
            ArrayList<DetalleCompra> detallesArray = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DetalleCompra detalle = new DetalleCompra();
                detalle.setCodigo_producto(jsonObject.getString("codigo_producto"));
                detalle.setNombre_producto(jsonObject.getString("producto_nombre"));
                detalle.setUnidades(jsonObject.getInt("unidades"));
                detalle.setProveedor(jsonObject.getString("proveedor_nombre"));
                detalle.setProveedor_id(jsonObject.getInt("proveedor_id"));
                detalle.setPrecio_compra(jsonObject.getInt("precio_compra"));
                detalle.setSubtotal(jsonObject.getInt("subtotal"));
                detallesArray.add(detalle);
            }
            compra.setDetalles(detallesArray);
        } else {
            System.out.println("Error en la consulta de la compra. Código de estado: " + response.statusCode());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return compra; // Retorna la compra, o null si ocurrió un error
}

public static ArrayList<Compra> getAllCompras() {
    ArrayList<Compra> compras = new ArrayList<>();

    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/consultarCompras.php"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.getBoolean("success")) {
                JSONArray comprasArray = jsonResponse.getJSONArray("compras");

                for (int i = 0; i < comprasArray.length(); i++) {
                    JSONObject compraJson = comprasArray.getJSONObject(i);
                    Compra compra = new Compra();
                    compra.setCompra_id(compraJson.getInt("compra_id")); // ID de la compra
                    compra.setFecha(compraJson.getString("fecha"));
                    compra.setUsuario(compraJson.getString("usuario"));
                    compra.setTotal(Integer.parseInt(compraJson.getString("total")));
                    compras.add(compra);
                }
            } else {
                System.out.println("Error: " + jsonResponse.getString("message"));
            }
        } else {
            System.out.println("Error en la solicitud: " + response.statusCode());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return compras;
}

public static void put(Compra compra) {
    JSONObject compraRequest = new JSONObject();
    JSONArray detallesArray = new JSONArray();
    compraRequest.put("compra_id", compra.getCompra_id());
    compraRequest.put("usuario_id", compra.getUsuario_id());
    compraRequest.put("fecha", compra.getFecha());
    compraRequest.put("total", compra.getTotal());
    
    for (DetalleCompra detalle : compra.getDetalles()) {
        JSONObject detalleJSON = new JSONObject();
        detalleJSON.put("codigo_producto", detalle.getCodigo_producto());
        detalleJSON.put("proveedor_id", detalle.getProveedor_id());
        detalleJSON.put("unidades", detalle.getUnidades());
        detalleJSON.put("precio_compra", detalle.getPrecio_compra());
        detalleJSON.put("subtotal", detalle.getSubtotal());

        detallesArray.put(detalleJSON); // Agregar el detalle al array
    }
    compraRequest.put("detalles", detallesArray);
    String compraRequestString = compraRequest.toString();
    System.out.println("CompraRequest: " + compraRequestString);
    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/compra.php"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(compraRequestString))
                .build();
        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response was: " + putResponse.body());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void eliminarCompra(int compraId) {
    try {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/compra.php?compra_id=" + compraId))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        if (deleteResponse.statusCode() == 200) {
            System.out.println("Compra eliminada correctamente. Respuesta: " + deleteResponse.body());
        } else {
            System.out.println("Error al eliminar la compra. Código de respuesta: " + deleteResponse.statusCode());
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
