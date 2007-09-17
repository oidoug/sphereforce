/*
 * Menu.java
 *
 * Created on Aug 26, 2007, 4:37:24 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;

/**
 *
 * @author das
 */
public class Menu {
    
    private final long OUT_TIME = 5000;
    private int selected_button;
    
    private Image background;
    private Image button_up;
    private Image button_down;
    private Image title;
    private Image help_image;
    
    private String[] itens_menu;
    
    private Main applet;
    
    private boolean HELP = false;
    
    /* @TODO: tentar criar um menu dinamico que pode ser criado apenas com parametros */
    public Menu(Main applet, String[] itens) {
        this.applet = applet;
        
        this.selected_button = 0;
        
        this.itens_menu = itens;
        
        //        Thread thread;
        //        thread = new Thread(this);
        //        thread.start();
    }
    
    void setImages(Image image, Image image0, Image image1, Image image2, Image image3) {
        this.background = image;
        this.title = image0;
        this.button_up = image1;
        this.button_down = image2;
        this.help_image = image3;
        
        //        Main.loading.addImage(image, 0);
        //        Main.loading.addImage(image0, 0);
        //        Main.loading.addImage(image1, 0);
    }
    
    public void paint(Graphics g) {
        if(!HELP) {
            /** BEGIN background */
            int x = 0;
            int y = 0;
            
            while (y < Constantes.WINDOW_HEIGHT) {
                x = 0;
                while (x < Constantes.WINDOW_WIDTH) {
                    g.drawImage(background, x, y, null);
                    x = x + Constantes.BACKGROUND_W;
                }
                y = y + Constantes.BACKGROUND_H;
            }
            /** END background */
            
            // logo
            g.drawImage(title, Constantes.WINDOW_WIDTH / 2 - 200, 10, applet);
            
            /** BEGIN buttons */
            for (int i = 0; i < itens_menu.length; i++) {
                if (i == selected_button) {
                    g.drawImage(button_down, Constantes.WINDOW_WIDTH / 2 - Constantes.BOTTON_W / 2, i * Constantes.BOTTON_H * 2 + 350, applet);
                } else {
                    g.drawImage(button_up, Constantes.WINDOW_WIDTH / 2 - Constantes.BOTTON_W / 2, i * Constantes.BOTTON_H * 2 + 350, applet);
                }
                g.setColor(Color.GREEN);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(itens_menu[i], Constantes.WINDOW_WIDTH / 2 - Constantes.BOTTON_W / 2 + 20, i * Constantes.BOTTON_H * 2 + 375);
            }
            /** END buttons */
        } else {
            /** BEGIN background */
            int x = 0;
            int y = 0;
            
            while (y < Constantes.WINDOW_HEIGHT) {
                x = 0;
                while (x < Constantes.WINDOW_WIDTH) {
                    g.drawImage(background, x, y, null);
                    x = x + Constantes.BACKGROUND_W;
                }
                y = y + Constantes.BACKGROUND_H;
            }
            /** END background */
            g.drawImage(help_image, 0, 0, applet);
            
        }
    }
    
    public void keyDownTyped() {
        if (selected_button == itens_menu.length - 1) {
            selected_button = 0;
        } else {
            selected_button++;
        }
    }
    
    public void keyUpTyped() {
        if (selected_button == 0) {
            selected_button = itens_menu.length - 1;
        } else {
            selected_button--;
        }
    }
    
    public void keyEnterTyped() {
        if(HELP != true) {
            if (selected_button == 0) {
                applet.startsAsServer();
            }
            if (selected_button == 1) {
                applet.startsAsClient();
            }
            if (selected_button == 2) {
                HELP = true;
            }
        }
    }
    
    public void keyEscapeTyped() {
        if(HELP == true) HELP = false;
        else {
            System.exit(0);
        }
    }
}
