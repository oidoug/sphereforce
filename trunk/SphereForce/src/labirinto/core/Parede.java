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

        if (vertical) {
            absTamanhoW = tamanho * blocoW;
            absTamanhoH = blocoH;
        } else {
            absTamanhoW = blocoW;
            absTamanhoH = tamanho * blocoH;
        }
    }

    public int getH() {
        return blocoH;
    }

    public int getW() {
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
        for (int i = 0; i < tamanho; i++) {
            if (vertical) {
                g.drawImage(blocoimage,(int) x + (i * blocoH),(int) y, null);
            } else {
                g.drawImage(blocoimage,(int) x,(int) y + (i * blocoW), null);
            }
        }
    }

    
}
