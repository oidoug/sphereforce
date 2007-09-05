/*
 * Menu.java
 *
 * Created on Aug 26, 2007, 4:37:24 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author das
 */
public class Menu implements KeyListener {
    
    private final long OUT_TIME = 5000;
    
    private final int BackGW = 50;
    private final int BackGH = 50;

    private Image background;
    private Image button;
    private Image title;

    private Main applet;

    /* @TODO: tentar criar um menu dinamico que pode ser criado apenas com parametros */
    public Menu(Main applet) {
        this.applet = applet;

//        Thread thread;
//        thread = new Thread(this);
//        thread.start();
    }

    void setImages(Image image, Image image0, Image image1) {
        this.background = image;
        this.title = image0;
        this.button = image1;

        Main.loading.addImage(image, 0);
        Main.loading.addImage(image0, 0);
        Main.loading.addImage(image1, 0);
    }

    public void paint(Graphics g) {
        int x = 0;
        int y = 0;

        long stopTime = OUT_TIME;
        long startTime = System.currentTimeMillis();
        while (y < Main.WINDOW_HEIGHT) {
            x = 0;
            while (x < Main.WINDOW_WIDTH) {
                g.drawImage(background, x, y, null);
                x = x + BackGW;
            }
            y = y + BackGH;
        }
        while (this.applet.state == Main.LOGO) {
            if (stopTime == System.currentTimeMillis() - startTime) {
                break;
            }
        }
        // ao fim de tudo, o estado passa para GAME_ON
        applet.state = Main.GAME_ON;
    }

    public void keyTyped(KeyEvent arg0) {
        
    }

    public void keyPressed(KeyEvent arg0) {
    }

    public void keyReleased(KeyEvent arg0) {
    }
}
