/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

/**
 *
 * @author PC
 */
public class DetalleVenta {
    String codigoProducto;
    String nombreProducto;
    int unidades;
    int precio_unitario;
    int subTotal;

    public DetalleVenta(String codigoProducto, int unidades, int precio_unitario, int subTotal) {
        this.codigoProducto = codigoProducto;
        this.unidades = unidades;
        this.precio_unitario = precio_unitario;
        this.subTotal = subTotal;
    }
    
    public DetalleVenta(){
        
    }
    public void CalcularSubTotal(){
        int subTotalOperation = this.unidades * this.precio_unitario;
        this.setSubTotal(subTotalOperation);
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public int getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(int precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }
    
    
}
