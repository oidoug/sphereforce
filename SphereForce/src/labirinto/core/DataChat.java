/*
 * DataChat.java
 * 
 * Created on Aug 26, 2007, 8:33:23 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto.core;

import java.io.Serializable;
/**
 *
 * @author das
 */
public class DataChat implements Serializable{
    
    private String message;
    
    private int comando;

    public DataChat() {
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setComando(int valor){
        this.comando = valor;
    }
    
    public int getComando(){
        return comando;
    }

}
