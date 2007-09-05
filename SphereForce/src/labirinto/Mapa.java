/*
 * Mapa.java
 *
 * Created on Sep 4, 2007, 11:37:08 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.awt.Image;
import java.util.LinkedList;
import labirinto.core.Buraco;
import labirinto.core.Esfera;
import labirinto.core.Marca;

/**
 *
 * @author Douglas Schmidt
 */
public class Mapa {

    private LinkedList paredes;
    private LinkedList buracos;

    private Image buraco;
    private Image bloco;

    private Marca largada;
    private Marca chegada;

    private Esfera redsphere;
    private Esfera bluesphere;

    private final int NUM_BURACOS = 20;

    public Mapa() {
    }

    public void addObject(Object object) {
        if (object instanceof Esfera) {
            if (bluesphere != null) {
                redsphere = (Esfera) object;
            } else {
                bluesphere = (Esfera) object;
            }
        } else if (object instanceof Marca) {
            if (largada != null) {
                chegada = (Marca) object;
            } else {
                largada = (Marca) object;
            }
        } else if (object instanceof Image) {
            if (buraco != null) {
                bloco = (Image) object;
            } else {
                buraco = (Image) object;
            }
        } else {
            System.err.println("erro: Objeto nao valido.");
        }
    }

    public void refresh() {
        
    }

    @SuppressWarnings(value = "unchecked")
    public void gerarBuracos() {
        int randX;
        int randY;
        for (int i = 0; i <= NUM_BURACOS; i++) {
            randX = (int) Math.random();
            randY = (int) Math.random();
            buracos.add(new Buraco(buraco, randX, randY));
        }
    }
}
