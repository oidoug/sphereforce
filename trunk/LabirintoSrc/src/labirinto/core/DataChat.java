/*
 * DataChat.java
 * 
 * Created on Aug 26, 2007, 8:33:23 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto.core;

/**
 *
 * @author das
 */
public class DataChat {
    
    private String message;

    public DataChat() {
    }
    
    /**
     * retorna o vetor em bytes respectivo a String mensagem da classe 
     */
    public byte[] getBytes() {
        byte byteVector[] = new byte[message.length()];
        
        byteVector = message.getBytes();
        
        return byteVector;
    }
    
    /**
     * Seta a mensagem
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Retranforma o vetor em bytes recebido em uma String mensagem
     */
    public void setData (byte[] byteVector) {
        message = byteVector.toString();
    }
    
    /**
     * Retorna String mensagem
     */
    public String getData() {
        return message;
    }

}
