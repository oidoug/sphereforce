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
    private float H;
    private float W;
    
    // define imagem para a marca
    private Image marca;

    public Marca(Image marca, float posx, float posy) {
        x = posx;
        y = posy;
        
        //por os valores corretos
        H = 20;
        W = 50;
        
        this.marca = marca;
    }
    public void setX(float valor){
        x = valor;    
    }
    
    public void setY(float valor){
        y  = valor;
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public float getH(){
        return H;
    }
    
    public float getW(){
        return W;
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
