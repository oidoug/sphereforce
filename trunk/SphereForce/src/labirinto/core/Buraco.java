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
    
    public boolean colideCom(Esfera esfera) {
      float cateto1 = x - esfera.getX();
      float cateto2 = y - esfera.getY();
      float distancia = (float) Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
      if (distancia < raio + esfera.getRaio())
          return true;
      else        
        return false;
    }
    
    // o buraco ao ser construido nao pode estar no mesmo lugar que a parede
    public boolean colideCom(Parede parede) {
        
        boolean colide = false;
        
        if ((y > parede.getY() - raio) &&
                (y < parede.getY() + parede.getAbsTamanhoH() + raio)){
           
            if ( (x - parede.getX()) < (x + 2*raio) ){
                colide =  true;
            }
            
            else if ((parede.getX() + parede.getAbsTamanhoW() + raio) > x){
                colide = true;
            }
        }
        
        if ((x > parede.getX() - raio) &&
                (x < parede.getX() + parede.getAbsTamanhoW() + raio) ) {
            
            if ( (y - parede.getY()) < (y + 2*raio) ) {
                colide = true;
            }
            
            else if ((parede.getY() + parede.getAbsTamanhoH() + raio) > y) {
                colide = true;
            }
        }
        
        return colide;
    }
    
    // o buraco nao deve ser construido se estiver na mesma posicao de uma marca
    public boolean colideCom(Marca marca) {
        //implementei supondo q a marca eh um retangulo com altura e largura definidas
        //assim verifico as distancias do ponto central da bola com as laterais da maca
        
        // tirei a bool colide pq era desnecessaria. Se colidir em um dos testes
        // nao precisa checar com os outros
        boolean colide = false;
        
        if ((y > marca.getY() - raio) &&
                (y < marca.getY() + marca.getH() + raio)){
           
            if ( (x - marca.getX()) < (x + 2*raio) ){
                colide =  true;
            }
            
            else if ((marca.getX() + marca.getW() + raio) > x){
                colide = true;
            }
        }
        
        if ((x > marca.getX() - raio) &&
                (x < marca.getX() + marca.getW() + raio) ) {
            
            if ( (y - marca.getY()) < (y + 2*raio) ) {
                colide = true;
            }
            
            else if ((marca.getY() + marca.getH() + raio) > y) {
                colide = true;
            }
        }
        
        return colide;
    }
}
