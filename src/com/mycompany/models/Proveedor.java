/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

/**
 *
 * @author PC
 */
public class Proveedor {
    int proveedor_id;
    String nombre;
    String direccion;
    String telefono;

    public Proveedor(String nombre, String direccion, String telefono) {
        
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    public Proveedor(){
        
    }
    public int getProveedor_id() {
        return proveedor_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setProveedor_id(int proveedor_id) {
        this.proveedor_id = proveedor_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
