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
    
    public void colideCom(Esfera esfera) {
        
        float tLateral, tVertical;
        
        if (isVertical()){
         tLateral = H;
         tVertical = tamanho * W;
        }
        else {
            tLateral = tamanho * H;
            tVertical = W;
        }
        
        if ((esfera.getY() > y - esfera.getRaio()) &&
                (esfera.getY() < y + tVertical + esfera.getRaio()) ) {
           
            //verifica se colide na parte lateral esquerda da parede
            if ( (esfera.getX() - x) < (esfera.getX() + 2*esfera.getRaio()) ){
                esfera.setVelX(-esfera.getVelX());
                esfera.setX(x+2*esfera.getRaio());
            }
            
            //colisao com o lado direito da parede
            else if ((x + tLateral + esfera.getRaio()) > esfera.getX()){
                esfera.setVelX(-esfera.getVelX());
                esfera.setX(x);
            }
        }
        
        if ((esfera.getX() > x - esfera.getRaio()) &&
                (esfera.getX() < x + tLateral + esfera.getRaio()) ) {
            
            //colisao com a parte superior da parede
            if ( (esfera.getY() - y) < (esfera.getY() + 2*esfera.getRaio()) ) {
                esfera.setVelY(-esfera.getVelY());
                esfera.setY(y - 2*esfera.getRaio());
            }
            
            //colisao com a parte inferior da parede
            else if ((y + tVertical + esfera.getRaio()) > esfera.getY()) {
                esfera.setVelY(-esfera.getVelY());
                esfera.setY(y);
            }
        }
        
        
        
    }
}
