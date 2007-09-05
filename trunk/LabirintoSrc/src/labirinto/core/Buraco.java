/*
 * Buraco.java
 *
 * Created on September 4, 2007, 9:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package labirinto.core;


import labirinto.*;
import java.awt.*;
import java.applet.*;
/**
 *
 * @author r
 */
public class Buraco {
    
    private Image buracoImage;
    private float x, y;
    private int caiu;
    
    /** Creates a new instance of Buraco */
    public Buraco(Image bah, int posx, int posy) {
        x = posx;
        y = posy;
        caiu = 0;
        buracoImage = bah;
        
    }

    public void setXY(float valorx, float valory){
        x = valorx;
        y = valory;
    }
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public void setCaiu(int valor){
        caiu = valor;
    }
    
    public int getCaiu(){
        return caiu;
    }
        
    public void paint(Graphics g) {
        g.drawImage(buracoImage, (int) x, (int) y, null);
    }
    
    public boolean colideCom(Esfera esfera) {
        // implementar checagem de colisao com esfera
        
        return false;
    }
    
    // o buraco ao ser construido nao pode estar no mesmo lugar que a parede
    public boolean colideCom(Parede parede) {
        
        return false;
    }
    
    // o buraco nao deve ser construido se estiver na mesma posicao de uma marca
    public boolean colideCom(Marca marca) {
        
        return false;
    }
}
