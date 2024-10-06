/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.mycompany.models.Cliente;
import java.util.List;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author Diego
 */
public class ClientesService {

    private static ClientesService instance;
    private String api_url = "http://localhost/sgc_api";

    public ClientesService() {
    }

    public static ClientesService getInstance() {
        if (instance == null) {
            instance = new ClientesService();
        }
        return instance;
    }

    public void crearCliente(String nit, String nombre, String direccion, String telefono, String correo) {
        try {
            JSONObject cliente = new JSONObject();
            cliente.put("nit", nit);
            cliente.put("nombre", nombre);
            cliente.put("direccion", direccion);
            cliente.put("telefono", telefono);
            cliente.put("correo", correo);
            String clienteString = cliente.toString();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create(api_url + "/guardarCliente.php"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(clienteString))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = postResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
            String mensaje = jsonObject.getString("message");
            JOptionPane.showMessageDialog(null, mensaje);
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
        }
    }

    public String[] consultarCliente(String nitConsulta) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(api_url + "/consultarCliente.php?nit=" + nitConsulta)).build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.length() > 0) {
                String nit = jsonObject.getString("nit");
                String nombre = jsonObject.getString("nombre");
                String direccion = jsonObject.getString("direccion");
                String telefono = jsonObject.getString("telefono");
                String correo = jsonObject.getString("correo");
                return new String[]{nit, nombre, direccion, telefono, correo};
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró al cliente");
                return null;
            }
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public ArrayList<Cliente> consultarClientes() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(api_url + "/consultarClientes.php")).build();
            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            JSONArray jsonArray = new JSONArray(jsonString);
            ArrayList<Cliente> clientes = new ArrayList<>();
            if (jsonArray.length() < 1){
                JOptionPane.showMessageDialog(null, "No fue posible obtener los datos");
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 8. Extraer los datos del cliente
                String nit = jsonObject.getString("nit");
                String nombre = jsonObject.getString("nombre");
                String direccion = jsonObject.getString("direccion");
                String telefono = jsonObject.getString("telefono");
                String correo = jsonObject.getString("correo");
                // 9. Crear un objeto Cliente y añadirlo a la lista
                Cliente cliente = new Cliente(nit, nombre, direccion, telefono, correo);
                clientes.add(cliente);
            }
            return clientes;
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void eliminarCliente(String nit) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest deleteRequest = HttpRequest.newBuilder().uri(URI.create(api_url + "/eliminarCliente.php?nit=" + nit)).build();
            HttpResponse<String> getResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = getResponse.body();
            if (jsonString.length() > 0) {
                JOptionPane.showMessageDialog(null, "Eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Algo ha ocurido mal");
            }
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizarCliente(String nitConsulta, String nombre, String direccion, String telefono, String correo) {
        try {
            JSONObject cliente = new JSONObject();
            cliente.put("nit", nitConsulta);
            cliente.put("nombre", nombre);
            cliente.put("direccion", direccion);
            cliente.put("telefono", telefono);
            cliente.put("correo", correo);
            String clienteString = cliente.toString();
            System.out.println("Nombre: " + nombre);
            System.out.println("clienteString: " + clienteString);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create(api_url + "/actualizarCliente.php"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(clienteString))
                    .build();
            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = postResponse.body();
            JSONObject jsonObject = new JSONObject(jsonString);
            String mensaje = jsonObject.getString("message");
            JOptionPane.showMessageDialog(null, mensaje);
        } catch (IOException | InterruptedException | JSONException ex) {
            ex.printStackTrace();

        }

    }
}
