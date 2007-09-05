/*
 * Parede.java
 *
 * Created on Sep 4, 2007, 4:24:54 PM
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
public class Parede {

    // posição x e y referente a posicao da parede na tela
    private float x;
    private float y;
    // tamanho referece a quantidade de blocos que compoe a parede
    private int tamanho;
    // imagem do bloco
    private Image blocoimage;
    
    //tamanho das laterias de cada bloco
    private float H;
    private float W;

    // define orientacao da parede, se vertical entao vertical = true;
    private boolean vertical;

    public Parede(Image blocoimage, int tamanho, boolean vertical, float x, float y) {
        this.blocoimage = blocoimage;
        this.tamanho = tamanho;
        this.vertical = vertical;
        this.x = x;
        this.y = y;
        
        //colocar valores corretos
        H = 20;
        W = 20;
    }

    public float getH(){
        return H;
    }
    
    public float getW(){
        return W;
    }
    
    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    // pinta parede na tela
    public void paint(Graphics g) {
        
    }
    
    public boolean colideCom(Esfera esfera) {
        // deve-se tratar a colisao com a esfera
        
        return false;
    }
}
