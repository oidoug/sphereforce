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
    private LinkedList<String> outputs;
    
    public Chat(Main applet, Image chat_image) {
        this.applet = applet;
        this.chat_image = chat_image;
        input = new String();
        outputs = new LinkedList<String>();
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
        
        boolean colorDif = false;
        for (int i = 0; i < outputs.size(); i++) {
            if (applet.servidor) {
                g.setColor(Color.BLUE);
                colorDif = false;
            } else {
                g.setColor(Color.RED);
                colorDif = true;
            }
            g.drawString(outputs.get(i), Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_OUTPUT_INIT_Y + Constantes.CHAT_STRING_OUTPUT_SPACELINE * i);
        }
    }
    
    public void keyEnterTyped() {
        if(outputs.size() > Constantes.MAX_OUTPUT_LIST_SIZE) {
            outputs.removeFirst();
        }
        outputs.addLast(input);
        try {
            conn.Send(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        input = "";
    }
    
    public void keyEscapeTyped() {
        applet.state = Main.GAME_ON;
    }
    
    public void remoteMessage(String message) {
        if(message.contains("&")) {
            applet.chatNow(false);
            //applet.state = Main.GAME_ON;
        } else {
            applet.chatNow(true);
            //if(!message.equals("$"))
            outputs.addLast(message);
        }
        System.out.println("Remote message: " + message);
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
        ExecutorService threadExecutor = Executors.newFixedThreadPool(1);
        threadExecutor.execute(receiveThread);
        
    }
}
