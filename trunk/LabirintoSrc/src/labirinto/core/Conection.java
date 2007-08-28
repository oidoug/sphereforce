/*
 * Conection.java
 *
 * Created on April 22, 2007, 11:46 PM
 */

package labirinto.core;

import java.net.DatagramSocket;
import java.net.Socket;
import labirinto.Main;

public class Conection {
    
    private DatagramSocket connUDP;
    private Socket connTCP;
    
    /** Creates a new instance of Conection */
    public Conection() {
    }
    
    // sender para dados do jogo
    public void Send(DataGame data) {
        
    }
    // sender para mensagens String
    public void Send(DataChat message) {
        
    }
    
    // receiver para dados do jogo
    public void Receive(DataGame data) {
        
    }
    // receiver para mensagens String
    public void Receive(DataChat message) {
        
    }
    
    public boolean[] getKeys() {
        boolean keyVector[] = new boolean[Main.NUM_OF_KEYS];
        
        return keyVector;
    }
    
}
