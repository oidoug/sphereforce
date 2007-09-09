/*
 * ChatReceive.java
 * 
 * Created on Sep 8, 2007, 2:38:08 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import labirinto.core.Conection;

/**
 *
 * @author Douglas Schmidt
 */
public class ChatReceive implements Runnable{

    private Thread receive;
    private Conection tcp;
    private String message;
    
    public ChatReceive(Conection tcp) {
        receive = new Thread();
        this.tcp = tcp;
        receive.start();
    }
    
    public void stop() {
        receive.interrupt();
    }

    public void run() {
        while(!receive.interrupted()) {
            tcp.getData(message);
        }
    }
    
    public boolean isRunning() {
        return !receive.isInterrupted();
    }
    
    public void recover() {
        receive.start();
    }
}
