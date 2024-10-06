/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author PC
 */
public class HeaderColor extends DefaultTableCellRenderer {

    public HeaderColor() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        setBackground(new java.awt.Color(32, 136, 203));
        setFont(new Font("Segoe UI", Font.BOLD, 12));
 
//you can change the color that u want 
        return this;
    }
}
