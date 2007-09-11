/*
 * Chat.java
 *
 * Created on Sep 8, 2007, 2:04:08 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import labirinto.core.ConectionTcp;
import labirinto.core.DataChat;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Douglas Schmidt
 */
public class Chat {
    
    private Main applet;
    private Image chat_image;
    private ConectionTcp conn;
    private ChatReceive receiveThread;
    
    private String input;
    private LinkedList<DataChat> outputs;
    
    private ExecutorService threadExecutor = Executors.newFixedThreadPool(1);
    
    public Chat(Main applet, Image chat_image) {
        this.applet = applet;
        this.chat_image = chat_image;
        input = new String();
        outputs = new LinkedList<DataChat>();
    }
    
    public void paint(Graphics g) {
        if (!receiveThread.isRunning()) {
            receiveThread.recover();
        }
        
        g.drawImage(chat_image, 0, 0, applet);
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        if (input.length() > Constantes.MAX_INPUT_CHAR) {
            g.drawString(input.substring(input.length() - Constantes.MAX_INPUT_CHAR, input.length()), Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_INPUT_INIT_Y);
        } else {
            g.drawString(input, Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_INPUT_INIT_Y);
        }
        

        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i).getComando() == Constantes.CHAT_SERVER) {
                g.setColor(Color.BLUE);
            } else if (outputs.get(i).getComando() == Constantes.CHAT_CLIENT) {
                g.setColor(Color.RED);                
            }
            g.drawString(outputs.get(i).getMessage(), Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_OUTPUT_INIT_Y + Constantes.CHAT_STRING_OUTPUT_SPACELINE * i);
        }
    }
    
    public void keyEnterTyped() {
        DataChat datac = new DataChat();
        if(outputs.size() > Constantes.MAX_OUTPUT_LIST_SIZE) {
            outputs.removeFirst();
        }
        if (applet.servidor)
            datac.setComando(Constantes.CHAT_SERVER);
        else
            datac.setComando(Constantes.CHAT_CLIENT);
        datac.setMessage(input);
        outputs.addLast(datac);
        try {
            conn.Send(datac);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        input = "";
    }
    
    public void keyEscapeTyped() {
        applet.state = Main.GAME_ON;
    }
    
    public void remoteMessage(DataChat datac) {
        
        if(datac.getComando() == Constantes.CHAT_STOP) {
            applet.chatNow(false);
            //applet.state = Main.GAME_ON;
        } else {
            applet.chatNow(true);
            if(datac.getComando() != Constantes.CHAT_START){
                outputs.addLast(datac);
            }
        }
        System.out.println("Remote comando: " + datac.getComando());
        System.out.println("Remote message: " + datac.getMessage());
    }
    
    void concatInInputMessage(char keyText) {
        input = input.concat(String.valueOf(keyText));
        System.out.println("catenado: "+ String.valueOf(keyText) + " : " +  input);
    }
    
    void unConcatInInputMessage() {
        input = input.substring(0, input.length() - 1);
    }
    
    void connect(ConectionTcp conection) {
        System.out.println("dentro do connet da classe CHAT");
        this.conn = conection;
        receiveThread = new ChatReceive(conn, this);
        
        threadExecutor.execute(receiveThread);
        
    }
    
    public void stopThread(){
        threadExecutor.shutdown();
        receiveThread = null;
        System.out.println("dei shutdown");
    }
    
    
    public void restartThread(){
        receiveThread.recover();
    }
}
