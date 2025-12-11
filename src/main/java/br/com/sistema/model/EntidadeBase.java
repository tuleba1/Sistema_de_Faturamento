
package br.com.sistema.model;


public abstract class EntidadeBase {
    int id; 
    
    public int getId() { return id;}
    public void setId(int id) {this.id = id;}
    
    public abstract void validar() throws Exception;
}
