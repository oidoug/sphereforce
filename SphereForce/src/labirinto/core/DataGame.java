/*
 * Data.java
 *
 * Created on Aug 26, 2007, 8:17:36 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto.core;

import java.io.Serializable;
import labirinto.Main;

/**
 *
 * @author das
 */
public class DataGame implements Serializable {

    private float x;
    private float y;

    private float velx;
    private float vely;

    private boolean chatON = false;

    private boolean[] keyVector;

    public DataGame() {
        keyVector = new boolean[Main.NUM_OF_KEYS];
        x = 0;
        y = 0;
        velx = 0;
        vely = 0;
    }

    /* getX retorna o valor float de x no momento */
    public float getX() {
        return x;
    }

    /* getY retorna o valor float de y no momento */
    public float getY() {
        return y;
    }

    /* Geter para Velx, usado para re-atualizar parametro de velocidade da esf. remota */
    public float getVelx() {
        return velx;
    }

    /* Geter para Vely, usado para re-atualizar parametro de velocidade da esf. remota */
    public float getVely() {
        return vely;
    }

    /* getKeyVector retorna o valor bool[] de keyVector no momento */
    public boolean[] getKeyVector() {
        return keyVector;
    }
    
    /* seta se chat esta ativado nesta ponta */
    public void setChatON(boolean chatON) {
        this.chatON = chatON;        
    }
    
    public boolean getChatON() {
        return chatON;
    }

    public void setAll(boolean[] keyVector, float x, float y, float velX, float velY) {
        this.keyVector = keyVector;
        this.x = x;
        this.y = y;
        this.velx = velX;
        this.vely = velY;
    }

    public void setKeyVector(boolean[] vector) {
        keyVector = vector;
    }
}
