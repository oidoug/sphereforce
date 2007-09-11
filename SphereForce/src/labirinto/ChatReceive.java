/*
 * ChatReceive.java
 * 
 * Created on Sep 8, 2007, 2:38:08 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import labirinto.core.ConectionTcp;
import labirinto.core.DataChat;

/**
 *
 * @author Douglas Schmidt
 */
public class ChatReceive implements Runnable{

    private Thread receive;
    private ConectionTcp tcp;
    private Chat chat;
    private DataChat datac;
    
    public ChatReceive(ConectionTcp tcp, Chat chat) {
        receive = new Thread();
        
        this.tcp = tcp;
        this.chat = chat;
        System.out.println("depois do new e antes do start");
        //receive.start();
        System.out.println("passo do start, OUVINDO NA PORTA TCP");
    }
    
    public void stop() {
        receive.interrupt();
    }

    public void run() {
        while(true) {
            System.out.println("esperando string do chat");

            try {
                datac = tcp.getData();
                chat.remoteMessage(datac);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean isRunning() {
        return !receive.isInterrupted();
    }
    
    public void recover() {
        receive.start();
    }
}
