/*
 * Marca.java
 * 
 * Created on Sep 4, 2007, 4:39:04 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto.core;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Douglas Schmidt
 */
public class Marca {
    
    // define posicao da marca na tela
    private float x,y;
    
    // define imagem para a marca
    private Image marca;

    public Marca(Image marca) {
        this.marca = marca;
    }
    
    // pinta marca na tela
    public void paint(Graphics g) {
        g.drawImage(marca, (int)x, (int)y, null);
    }
    
    public boolean colideCom(Esfera esfera){
        // tratar colisao com esfera
        return false;
    }

}
