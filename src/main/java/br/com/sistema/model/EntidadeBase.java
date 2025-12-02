/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.model;

/**
 *
 * @author tulio
 */
public abstract class EntidadeBase {
    int id; 
    
    public int getId() { return id;}
    public void setId(int id) {this.id = id;}
    
    public abstract void validar() throws Exception;
}
