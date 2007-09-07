/*
 * Stones.java
 *
 * Created on Sep 6, 2007, 4:24:58 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.awt.Image;
import labirinto.core.Parede;
import labirinto.core.Marca;

/**
 *
 * @author Douglas Schmidt
 */
public class Stones extends Cenario {

    public Stones(Image background, Image buraco, Image bloco, Image pedra, Image marca_inicio, Image marca_fim, int dificuldade) {
        
        super(background, buraco, bloco, pedra, marca_inicio, marca_fim, dificuldade);

        /* Precisa-se criar as marcas de inicio e fim do cenario */
        super.inicio = new Marca(marca_inicio, 40, 40);
        super.fim = new Marca(marca_fim, Constantes.WINDOW_WIDTH - 100 - 40, Constantes.WINDOW_HEIGHT - 50 - 40);

        labirinto();
        //super.buracos();
        super.pedras();
    }

    private void labirinto() {

        paredes.add(new Parede(bloco, 40, false, 0, Constantes.TAMANHO_BLOCO * 29, 1));
        paredes.add(new Parede(bloco, 30, true, 0, 0, 2));
        paredes.add(new Parede(bloco, 40, false, 0, 0, 3));
        paredes.add(new Parede(bloco, 30, true, Constantes.TAMANHO_BLOCO * 39, 0, 4));

        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 8, Constantes.TAMANHO_BLOCO, 5));
        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 16, Constantes.TAMANHO_BLOCO * 10, 6));
        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 24, Constantes.TAMANHO_BLOCO, 7));
        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 31, Constantes.TAMANHO_BLOCO * 10, 8));
    }
}
