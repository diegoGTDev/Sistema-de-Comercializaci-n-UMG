/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

/**
 *
 * @author PC
 */
public class Producto {
    String Codigo;
    String Nombre;
    int Disponible;
    int Precio_Compra;
    int Precio_Venta;
    String Proveedor;
    int Proveedor_id;

    public Producto(String Codigo, String Nombre, int Disponible, int Precio_Compra, int Precio_Venta, String Proveedor) {
        this.Codigo = Codigo;
        this.Nombre = Nombre;
        this.Precio_Compra = Precio_Compra;
        this.Precio_Venta = Precio_Venta;
        this.Proveedor = Proveedor;
        this.Disponible = Disponible;
    }
    
    public Producto(){
        
    }

    public String getCodigo() {
        return Codigo;
    }

    public String getNombre() {
        return Nombre;
    }
    public int getDisponible() {
        return Disponible;
    }
    public int getPrecio_Compra() {
        return Precio_Compra;
    }

    public int getPrecio_Venta() {
        return Precio_Venta;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setPrecio_Compra(int Precio_Compra) {
        this.Precio_Compra = Precio_Compra;
    }

    public void setPrecio_Venta(int Precio_Venta) {
        this.Precio_Venta = Precio_Venta;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }

    public void setDisponible(int Disponible) {
        this.Disponible = Disponible;
    }

    public int getProveedor_id() {
        return Proveedor_id;
    }

    public void setProveedor_id(int Proveedor_id) {
        this.Proveedor_id = Proveedor_id;
    }
    
    
}
