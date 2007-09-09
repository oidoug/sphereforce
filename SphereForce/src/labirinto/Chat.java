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
import labirinto.core.Conection;
import labirinto.core.DataChat;

/**
 *
 * @author Douglas Schmidt
 */
public class Chat {

    private Main applet;
    private Image chat_image;
    private Conection conn;
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
        g.setFont(new Font("Arial", Font.BOLD, 16));
        if (input.length() > Constantes.MAX_INPUT_CHAR) {
            g.drawString(input.substring(input.length() - Constantes.MAX_INPUT_CHAR, input.length()), Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_INPUT_INIT_Y);
        } else {
            g.drawString(input, Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_INPUT_INIT_Y);
        }

        int stringscreenposition = 0;
        for (String output : outputs) {
            stringscreenposition++;
//            if (output.length() / Constantes.MAX_INPUT_CHAR > 1) {
            for (int i = 0; i < (int) output.length() / Constantes.MAX_INPUT_CHAR; i++) {
                g.drawString(output.substring(Constantes.MAX_INPUT_CHAR * i, Constantes.MAX_INPUT_CHAR * i + Constantes.MAX_INPUT_CHAR), Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_INPUT_INIT_Y + Constantes.CHAT_STRING_OUTPUT_SPACELINE * i);
            }
//            }
        }
        System.out.println("ATUALIZANDO CHAT: INPUT: " + input);
    }

    public void keyEnterTyped() {
        outputs.addLast(input);
        DataChat data = new DataChat();
        data.setMessage(input);
        conn.Send(data);
        input = "";
    }

    public void keyEscapeTyped() {
        if (outputs.size() >= Constantes.MAX_OUTPUT_LIST_SIZE) {
            outputs.clear();
        }
        applet.state = Main.GAME_ON;
    }

    public void remoteMessage(String message) {
        outputs.addLast(message);
    }

    void concatInInputMessage(String keyText) {
        input.concat(keyText);
        System.out.println("catenado: "+ keyText);
    }

    void connect(Conection conection) {
        this.conn = conection;
        receiveThread = new ChatReceive(conn);
    }
}
