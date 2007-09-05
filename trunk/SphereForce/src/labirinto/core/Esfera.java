/*
 * Esfera.java
 *
 * Created on April 22, 2007, 11:55 AM
 */

package labirinto.core;

import labirinto.*;
import java.awt.*;
import java.applet.*;


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
            float distancia = this.getDistancia();
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

        float distancia = getDistancia();

        if (distancia <= (raio*2)) {
            return true;
        } else {
            return false;
        }
    }

    // retorna distancia entre as esferas
    public float getDistancia() {

        float cateto1 = inimiga.getY() - y;
        float cateto2 = inimiga.getX() - x;
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
            float almentox = (float) (Math.abs(cat1) - Math.abs(cateto1));
            float almentoy = (float) (Math.abs(cat2) - Math.abs(cateto2));

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
        if (x >= Main.WINDOW_WIDTH - raio * 2) {
            velX = -velX;
            x = Main.WINDOW_WIDTH - raio * 2;
        }

        //colisao parede esquerda
        if (x <= 0) {
            velX = -velX;
            x = 0;
        }

        //colisao parede inferior
        if (y >= Main.WINDOW_HEIGHT - 20) {
            velY = -velY;
            y = Main.WINDOW_HEIGHT - raio * 2;
        }

        //colisao parede superior
        if (y <= 0) {
            velY = -velY;
            y = 0;
        }

   
    }
    
}
