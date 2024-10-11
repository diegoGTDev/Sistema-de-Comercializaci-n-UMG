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
import com.mycompany.models.Venta;
import com.mycompany.models.DetalleVenta;

/**
 *
 * @author PC
 */
public class VentaService {

    private static String apiUrl = "http://localhost/sgc_api/venta";
    private static VentaService _instance;

    private VentaService() {

    }

    private static VentaService getInstance() {
        if (_instance == null) {
            _instance = new VentaService();
        }
        return _instance;
    }
    public static int getTotalVentas(){
        try{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl+"/totalVentas.php")).GET().build();
        HttpResponse getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        JSONObject response = new JSONObject(getResponse.body().toString());

        int total = response.getInt("totalVentas");
        return total;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
        
    }
    public static void Post(Venta venta, ArrayList<DetalleVenta> detalles) {
        JSONObject ventaJSON = new JSONObject();
        ventaJSON.put("nit", venta.getNitCliente());
        ventaJSON.put("responsable", venta.getResponsable_id());
        ventaJSON.put("total", venta.getTotal());

        JSONArray detallesArray = new JSONArray();
        for (DetalleVenta detalle : detalles) {
            JSONObject detalleJSON = new JSONObject();
            detalleJSON.put("codigo_producto", detalle.getCodigoProducto());
            detalleJSON.put("unidades", detalle.getUnidades());
            detalleJSON.put("precio_unitario", detalle.getPrecio_unitario());
            detalleJSON.put("subtotal", detalle.getSubTotal());

            detallesArray.put(detalleJSON); // Agregar el detalle al array
        }
        ventaJSON.put("detalles", detallesArray);
        String ventaString = ventaJSON.toString();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/generarVenta.php"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(ventaString))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response venta: " + postResponse.body().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Venta getVenta(int ventaId) {
        
        Venta venta = null;
        try {
            // Construir la URL con el ID de la venta como parámetro
            String url = apiUrl + "/consultarVenta.php?venta_id=" + ventaId;

            // Crear un cliente HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Construir la solicitud GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar si la respuesta fue exitosa
            if (response.statusCode() == 200) {
                // Parsear el JSON recibido
                JSONObject jsonResponse = new JSONObject(response.body());
                System.out.println("Response: " + jsonResponse.toString());
                // Crear el objeto Venta a partir del JSON
                venta = new Venta();
                JSONObject jsonVenta = new JSONObject();
                jsonVenta = jsonResponse.getJSONObject("venta");
                venta.setId_venta(Integer.parseInt(jsonVenta.getString("venta_id")));
                venta.setNitCliente(jsonVenta.getString("cliente_nit"));
                venta.setFecha(jsonVenta.getString("fecha"));
                venta.setResponsable(jsonVenta.getString("responsable"));
                venta.setResponsable_id(Integer.parseInt(jsonVenta.getString("responsable_id")));
                venta.setTotal(jsonVenta.getInt("total"));
                JSONArray jsonArray = new JSONArray();
                jsonArray = jsonVenta.getJSONArray("detalles");
                ArrayList<DetalleVenta> detallesArray = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setCodigoProducto(jsonObject.getString("codigo_producto"));
                    detalle.setNombreProducto(jsonObject.getString("nombre_producto"));
                    detalle.setUnidades(Integer.parseInt(jsonObject.getString("unidades")));
                    detalle.setPrecio_unitario(Integer.parseInt(jsonObject.getString("precio_unitario")));
                    detalle.setSubTotal(Integer.parseInt(jsonObject.getString("subtotal")));
                    detallesArray.add(detalle);
                }
                venta.setDetalles(detallesArray);

            } else {
                System.out.println("Error en la consulta de la venta. Código de estado: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("VentaIDAPI: " + venta.getId_venta());
        return venta; // Retorna la venta, o null si ocurrió un error
    }

    public static ArrayList<Venta> getAll() {
        ArrayList<Venta> ventas = new ArrayList<>();

        try {
            // Realiza la solicitud a la API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/consultarVentas.php"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica el estado de la respuesta
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                if (jsonResponse.getBoolean("success")) {
                    JSONArray ventasArray = jsonResponse.getJSONArray("data");

                    // Procesa cada venta
                    for (int i = 0; i < ventasArray.length(); i++) {
                        JSONObject ventaJson = ventasArray.getJSONObject(i);
                        Venta venta = new Venta();
                        venta.setId_venta(ventaJson.getInt("venta_id")); // ID de la venta
                        venta.setNitCliente(ventaJson.getString("cliente_nit"));
                        venta.setFecha(ventaJson.getString("fecha")); // Ajusta según el formato de fecha
                        venta.setResponsable(ventaJson.getString("responsable")); // Nombre del responsable
                        venta.setTotal(ventaJson.getInt("total"));
                        ventas.add(venta);
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

        return ventas;
    }

    public static void put(Venta venta) {
        JSONObject ventaRequest = new JSONObject();
        JSONArray detallesArray = new JSONArray();
        ventaRequest.put("venta_id", venta.getId_venta());
        ventaRequest.put("total", venta.getTotal());
        ventaRequest.put("cliente_nit", venta.getNitCliente());
        ventaRequest.put("responsable", venta.getResponsable_id());
        for (DetalleVenta detalle : venta.getDetalles()) {
            JSONObject detalleJSON = new JSONObject();
            detalleJSON.put("codigo_producto", detalle.getCodigoProducto());
            detalleJSON.put("unidades", detalle.getUnidades());
            detalleJSON.put("precio_unitario", detalle.getPrecio_unitario());
            detalleJSON.put("subtotal", detalle.getSubTotal());

            detallesArray.put(detalleJSON); // Agregar el detalle al array
        }
        ventaRequest.put("detalles", detallesArray);
        String ventaRequestString = ventaRequest.toString();
        System.out.println("VentaString: " + ventaRequestString);
        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest putRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl + "/actualizarVenta.php")).PUT(HttpRequest.BodyPublishers.ofString(ventaRequestString)).build();
            HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response was: " + putResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void eliminarVenta(int ventaId) {
        try {
            // Crear la URL con el ID de la venta como parámetro

            // Crear el cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Configurar la solicitud HTTP DELETE
            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "/eliminarVenta.php?venta_id=" + ventaId))
                    .header("Content-Type", "application/json")
                    .DELETE() // Usar el método DELETE
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

            // Imprimir la respuesta
            if (deleteResponse.statusCode() == 200) {
                System.out.println("Venta eliminada correctamente. Respuesta: " + deleteResponse.body());
            } else {
                System.out.println("Error al eliminar la venta. Código de respuesta: " + deleteResponse.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
