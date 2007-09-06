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
    
    // tamanho absoluto em pixels
    private int absTamanhoW;
    private int absTamanhoH;
    
    // imagem do bloco
    private Image blocoimage;
    
    //tamanho das laterias de cada bloco
    private int blocoH;
    private int blocoW;

    // define orientacao da parede, se vertical entao vertical = true;
    private boolean vertical;

    public Parede(Image blocoimage, int tamanho, boolean vertical, float x, float y) {
        this.blocoimage = blocoimage;
        this.tamanho = tamanho;
        this.vertical = vertical;
        this.x = x;
        this.y = y;
        
        //colocar valores corretos
        blocoH = 20;
        blocoW = 20;
        
        if(vertical) {
            absTamanhoW = tamanho * blocoW;
            absTamanhoH = blocoH;
        } else {
            absTamanhoW = blocoW;
            absTamanhoH = tamanho * blocoH;
        }
    }

    public int getH(){
        return blocoH;
    }
    
    public int getW(){
        return blocoW;
    }
    
    public int getAbsTamanhoW() {
        return absTamanhoW;
    }
    
    public int getAbsTamanhoH() {
        return absTamanhoH;                
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
        
        if ((esfera.getY() > y - esfera.getRaio()) &&
                (esfera.getY() < y + absTamanhoH + esfera.getRaio()) ) {
           
            //verifica se colide na parte lateral esquerda da parede
            if ( (esfera.getX() - x) < (esfera.getX() + 2*esfera.getRaio()) ){
                esfera.setVelX(-esfera.getVelX());
                esfera.setX(x+2*esfera.getRaio());
            }
            
            //colisao com o lado direito da parede
            else if ((x + absTamanhoW + esfera.getRaio()) > esfera.getX()){
                esfera.setVelX(-esfera.getVelX());
                esfera.setX(x);
            }
        }
        
        if ((esfera.getX() > x - esfera.getRaio()) &&
                (esfera.getX() < x + absTamanhoW + esfera.getRaio()) ) {
            
            //colisao com a parte superior da parede
            if ( (esfera.getY() - y) < (esfera.getY() + 2*esfera.getRaio()) ) {
                esfera.setVelY(-esfera.getVelY());
                esfera.setY(y - 2*esfera.getRaio());
            }
            
            //colisao com a parte inferior da parede
            else if ((y + absTamanhoH + esfera.getRaio()) > esfera.getY()) {
                esfera.setVelY(-esfera.getVelY());
                esfera.setY(y);
            }
        }
        
        
        
    }
}
