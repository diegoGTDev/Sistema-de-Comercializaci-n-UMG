/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

/**
 *
 * @author PC
 */
public class Cliente {
    private String NIT;
    private String Nombre;
    private String Direccion;
    private String Telefono;
    private String Correo;
    
    public Cliente(String nit, String nombre, String direccion, String telefono, String correo){
        this.NIT = nit;
        this.Nombre = nombre;
        this.Direccion = direccion;
        this.Telefono = telefono;
        this.Correo = correo;
    }
    public String getNIT(){
        return this.NIT;
    }
    
    public String getNombre(){
        return this.Nombre;
    }
    
    public String getDireccion(){
        return this.Direccion;
    }
    
    public String getTelefono(){
        return this.Telefono;
    }
    
    public String getCorreo(){
        return this.Correo;
    }
    
    public void setNIT(String NIT){
        this.NIT = NIT;
    }
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    public void setDirecion(String Direccion){
        this.Direccion = Direccion;
    }
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    public void setCorreo(String Correo){
        this.Correo = Correo;
    }
    
}
