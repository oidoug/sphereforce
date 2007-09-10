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

/**
 *
 * @author Douglas Schmidt
 */
public class ChatReceive implements Runnable{

    private Thread receive;
    private ConectionTcp tcp;
    private Chat chat;
    private String message;
    
    public ChatReceive(ConectionTcp tcp, Chat chat) {
        receive = new Thread();
        
        this.tcp = tcp;
        this.chat = chat;
        
        receive.start();
    }
    
    public void stop() {
        receive.interrupt();
    }

    public void run() {
        while(!receive.interrupted()) {

            System.out.println("esperando string do chat");

            try {
                message = tcp.getString();
                System.out.println("mensagem recebida! "+ message);

                chat.remoteMessage(message);
                
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
