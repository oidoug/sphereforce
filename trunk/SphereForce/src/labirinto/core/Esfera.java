/*
 * Esfera.java
 *
 * Created on April 22, 2007, 11:55 AM
 */

package labirinto.core;

import labirinto.*;
import java.awt.*;
import java.applet.*;
import java.util.LinkedList;


public class Esfera {

    private float x;
    private float y;

    private float velX;
    private float velY;

    private float W;
    private float H;

    private float raio;

    private Image sphereImage;

    private Esfera inimiga;


    /** Creates a new instance of Esfera */
    public Esfera(Image bah, int posx, int posy) {
        x = posx;
        y = posy;
        velX = velY = 0;

        sphereImage = bah;
        Main.loading.addImage(sphereImage, 0);

        W = sphereImage.getWidth(null);
        H = sphereImage.getHeight(null);

        /* tem q da uma olhada nessas funcoes ai depois pra ve
         * se tem como faze elas funcionarem
         */
        
        //raio = H / 2;
        
        raio = 10;
    }


    /** Refresh all the sphere contents,
     *  geting the new volocity and axis
     */
    public void refresh(boolean[] keyVector, Esfera inimiga) {

        this.inimiga = inimiga;

        if (keyVector[Main.UP]) {
            velY -= Main.ACELER;
        }
        if (keyVector[Main.DOWN]) {
            velY += Main.ACELER;
        }
        if (keyVector[Main.LEFT]) {
            velX -= Main.ACELER;
        }
        if (keyVector[Main.RIGHT]) {
            velX += Main.ACELER;
        }
        //Refresh new positions
        x += velX;
        y += velY;

        //Active "Atrito" constant
        velX *= Main.ATRITO;
        velY *= Main.ATRITO;

        
//Test and works with the colision with the other sphere
        if (inimiga != null) {
            colideEsferas();
        }

 
        this.trataLaterais();
    }

    /** Refresh all the sphere content based on data received from socket
     *  connection
     */
    public void refresh(Conection conn) {
        refresh(conn.getKeys(), null);
    }


    public void setXY(float valorX, float valorY) {
        x = valorX;
        y = valorY;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVelXY(float valorVelX, float valorVelY) {
        velX = valorVelX;
        velY = valorVelY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public float getH() {
        return H;
    }

    public float getW() {
        return W;
    }

    public float getRaio() {
        return raio;
    }

    public void paint(Graphics g) {
        g.drawImage(sphereImage, (int) x, (int) y, null);
    }

    public void colideEsferas() {   
        boolean colidiu = verificaColisaoBolas();
        if (colidiu) {
            float dx = inimiga.getX() - x;
            float dy = inimiga.getY() - y;
            float distancia = this.getDistancia(x, y, inimiga.getX(), inimiga.getY());
            float ax = dx / distancia; //   dx/d
            float ay = dy / distancia; //   dy/d
            float va1 = velX * ax + velY * ay;
            float vb1 = -velX * ay + velY * ax;
            float va2 = inimiga.getVelX() * ax + inimiga.getVelY() * ay;
            float vb2 = -inimiga.getVelX() * ay + inimiga.getVelY() * ax;

            //velocidades decompostas sobre o novo eixo
            float vaP1 = va1 + (va2 - va1);
            float vaP2 = va2 + (va1 - va2);

            // desfaz as projecoes
            velX = vaP1*ax - vb1*ay;
            velY = vaP1*ay + vb1*ax;
            inimiga.setVelXY(vaP2*ax - vb2*ay, vaP2*ay + vb2*ax);
  
            // checa e desgruda as esferas se necessario
            desgrudaEsferas();
            
        }
    }

    // checa se esferas colidiram
    public boolean verificaColisaoBolas() {

        float distancia = getDistancia(x, y, inimiga.getX(), inimiga.getY());

        if (distancia <= (raio*2)) {
            return true;
        } else {
            return false;
        }
    }

    // retorna distancia entre as esferas
    public float getDistancia(float x1, float y1, float x2, float y2) {

        float cateto1 = y2 - y1;
        float cateto2 = x2 - x1;
        return (float) Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
    }


    // desgruda as esferas caso seja necessario
    public void desgrudaEsferas() {
        float cateto1 = inimiga.getX() - x;
        float cateto2 = inimiga.getY() - y;
        float distancia = (float) Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
        
        if (distancia < 20) {
            
            double theta1 = Math.asin(cateto1 / distancia);
            double theta2 = Math.asin(cateto2 / distancia);
            distancia = 2*raio;
            float cat1 = ((float) Math.sin(theta1)) * distancia;
            float cat2 = ((float) Math.sin(theta2)) * distancia;
            float almentox = (java.lang.Math.abs(cat1) - java.lang.Math.abs(cateto1));
            float almentoy = (java.lang.Math.abs(cat2) - java.lang.Math.abs(cateto2));

            // hauhauahuahua  isso aki eh criatividade
            if (x < inimiga.getX()) {
                x = x - almentox / 2;
                inimiga.setXY(inimiga.getX() + almentox / 2, inimiga.getY());
            } else {
                inimiga.setXY(inimiga.getX() - almentox / 2, inimiga.getY());
                x = x + almentox / 2;
            }

            if (y < inimiga.getY()) {
                y = y - almentoy / 2;
                inimiga.setXY(inimiga.getX(), inimiga.getY() + almentoy / 2);
            } else {
                inimiga.setXY(inimiga.getX(), inimiga.getY() - almentoy / 2);
                y = y + almentoy / 2;
            }
        }
    }

    public void trataLaterais() {
        //colisao parede direita
        if (x >= Constantes.WINDOW_WIDTH - raio * 2) {
            velX = -velX;
            x = Constantes.WINDOW_WIDTH - raio * 2;
        }

        //colisao parede esquerda
        if (x <= 0) {
            velX = -velX;
            x = 0;
        }

        //colisao parede inferior
        if (y >= Constantes.WINDOW_HEIGHT - 20) {
            velY = -velY;
            y = Constantes.WINDOW_HEIGHT - raio * 2;
        }

        //colisao parede superior
        if (y <= 0) {
            velY = -velY;
            y = 0;
        }

    }

    public void trataBuracos(LinkedList<Buraco> buracos) {
      for (Buraco hole : buracos){
        float distancia = getDistancia(x, y, hole.getX(), hole.getY());
        if (distancia < raio) 
            x = Constantes.SPHERE_INIT_POINT_X;
            y = Constantes.SPHERE_INIT_POINT_Y;
      }
    }
    
    public void trataPedras(LinkedList<Pedra> pedras) {
      for (Pedra hole : pedras){
        float distancia = getDistancia(x, y, hole.getX(), hole.getY());
        if (distancia < raio + hole.getRaio()){
            velX = -velX;
            velY = -velY;
            desgrudaPedra(hole);
        } 
            
      }
    }
    
    public void desgrudaPedra(Pedra pedra){
        float cateto1 = pedra.getX() - x;
        float cateto2 = pedra.getY() - y;
        float distancia = (float) Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
        
        if (distancia < raio + pedra.getRaio()) {
            
            double theta1 = Math.asin(cateto1 / distancia);
            double theta2 = Math.asin(cateto2 / distancia);
            distancia = raio + pedra.getRaio();
            float cat1 = ((float) Math.sin(theta1)) * distancia;
            float cat2 = ((float) Math.sin(theta2)) * distancia;
            float almentox = (java.lang.Math.abs(cat1) - java.lang.Math.abs(cateto1));
            float almentoy = (java.lang.Math.abs(cat2) - java.lang.Math.abs(cateto2));
            
            if (x > pedra.getX())
                x = x + almentox;
            else 
                x = x - almentox;
            
            if (y > pedra.getY())
                y = y + almentoy;
            else 
                y = y - almentoy;
            
            
        }
    }
    public void trataParedes(LinkedList<Parede> paredes) {
     for (Parede hole : paredes) {
        
         
        if (    (y >= hole.getY() - 2*raio) && 
                (y <= hole.getY() + hole.getAbsTamanhoH()) &&
                (x >= hole.getX() -2*raio ) && 
                (x <= hole.getX() + hole.getAbsTamanhoW()  )     ) {
                
                float xb = x + raio;
                float yb = y + raio;
                
                float m1x = hole.getAbsTamanhoW() / 2;
                float m1y = hole.getY();
                
                float m2x = hole.getX();
                float m2y = hole.getAbsTamanhoH() / 2;
                
                float m3x = hole.getAbsTamanhoW() / 2;
                float m3y = hole.getY() + hole.getAbsTamanhoH();
                
                float m4x = hole.getX() + hole.getAbsTamanhoW();
                float m4y = hole.getAbsTamanhoH() / 2;
                
                float d1 = getDistancia(xb, yb, m1x, m1y);
                float d2 = getDistancia(xb, yb, m2x, m2y);
                float d3 = getDistancia(xb, yb, m3x, m3y);
                float d4 = getDistancia(xb, yb, m4x, m4y);
                
                float menord = d1;
                
                if (menord > d2) menord = d2;
                if (menord > d3) menord = d3;
                if (menord > d4) menord = d4;
                
                if (menord == d1){
                    System.out.printf("\n bateu em cima da parede: %d",hole.getId());
                    velY = -velY;
                    y = hole.getY() - 2*raio;
                }
                
                if (menord == d2){
                    System.out.printf("\n bateu a esquerda da parede: %d",hole.getId());
                    velX = -velX;
                    x = hole.getX() - 2*raio;
                }
                
                if (menord == d3){
                    System.out.printf("\n bateu em baixo da parede: %d",hole.getId());
                    velY = -velY;
                    y = hole.getY() + hole.getAbsTamanhoH();
                }
                
                if (menord == d4){
                    System.out.printf("\n bateu a direita da parede: %d",hole.getId());
                    velX = -velX;
                    x = hole.getX() + hole.getAbsTamanhoW();
                }
                
                break;
        } 
        
     }//for
  
    }//trataParedes
}