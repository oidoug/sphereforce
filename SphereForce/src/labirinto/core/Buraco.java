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
    private int x, y;
    private int caiu;
    private float raio;
    
    /** Creates a new instance of Buraco */
    public Buraco(Image bah, int posx, int posy) {
        x = posx;
        y = posy;
        caiu = 0;
        buracoImage = bah;
        
        raio = 10;
    }
    public void setRaio(float valor){
        raio = valor;
    }

    public float getRaio(){
        return raio;
    }
    public void setXY(int valorx, int valory){
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
    
    
    
    // o buraco ao ser construido nao pode estar no mesmo lugar que a parede
    public boolean colideCom(Parede parede) {
        if (    (y >= parede.getY() - 2*raio) && 
                (y <= parede.getY() + parede.getAbsTamanhoH()) &&
                (x >= parede.getX() -2*raio ) && 
                (x <= parede.getX() + parede.getAbsTamanhoW()  )     )  
            return true;
        else
            return false;
    }
    
    // o buraco nao deve ser construido se estiver na mesma posicao de uma marca
    public boolean colideCom(Marca marca) {
        if (    (y >= marca.getY() - 2*raio) && 
                (y <= marca.getY() + marca.getH()) &&
                (x >= marca.getX() -2*raio ) && 
                (x <= marca.getX() + marca.getW()  )     )  
            return true;
        else
            return false;
    }
}