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
import labirinto.core.DoubleBufferApplet;

/**
 *
 * @author das
 */
public class Menu implements Runnable{
    
    private Image background;
    private Image button;
    private Image title;

    /* @TODO: tentar criar um menu dinamico que pode ser criado apenas com parametros */
    public Menu(String args[]) {
        
        // menu dinamico!!! uau
        
        Thread thread;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
        
        /* @TODO: implementar seleçao de server ou cliente, opções extras e help */
        
    }
    
    public void paint(Graphics g) {
        
    }
    
    
}
