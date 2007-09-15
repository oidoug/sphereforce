/*
 * Grass.java
 *
 * Created on September 11, 2007, 5:39 AM
 *
 * This content can be protected with right commons, creative commons or
 * some free license. Check the main doc for more information. Thanks.
 */

package labirinto;

import java.awt.Image;
import java.util.LinkedList;
import labirinto.core.Buraco;
import labirinto.core.Marca;
import labirinto.core.Parede;
import labirinto.core.Pedra;

/**
 *
 * @author Douglas Schmidt
 */
public class Grass extends Cenario{
    
    /** Creates a new instance of Grass */
    public Grass(Image background, Image buraco, Image bloco, Image pedra, Image marca_inicio, Image marca_fim,
            int dificuldade) {
                super(background, buraco, bloco, pedra, marca_inicio, marca_fim, dificuldade);

        /* Precisa-se criar as marcas de inicio e fim do cenario */
        super.inicio = new Marca(marca_inicio, 25, 530 );
        super.fim = new Marca(marca_fim, 670, 25);

        labirinto();
        
 
    }


    private void labirinto() {

        paredes.add(new Parede(bloco, 40, false, 0, Constantes.TAMANHO_BLOCO * 29, 100));
        paredes.add(new Parede(bloco, 30, true, 0, 0, 200));
        paredes.add(new Parede(bloco, 40, false, 0, 0, 300));
        paredes.add(new Parede(bloco, 30, true, Constantes.TAMANHO_BLOCO * 39, 0, 400));

        paredes.add(new Parede(bloco, 6, false, Constantes.TAMANHO_BLOCO * 0, Constantes.TAMANHO_BLOCO * 23, 1));
        paredes.add(new Parede(bloco, 6, false, Constantes.TAMANHO_BLOCO * 11, Constantes.TAMANHO_BLOCO * 25, 2));
        paredes.add(new Parede(bloco, 7, false, Constantes.TAMANHO_BLOCO * 5, Constantes.TAMANHO_BLOCO * 18, 3));
        paredes.add(new Parede(bloco, 6, false, Constantes.TAMANHO_BLOCO * 17, Constantes.TAMANHO_BLOCO * 20, 4));
        paredes.add(new Parede(bloco, 7, false, Constantes.TAMANHO_BLOCO * 28, Constantes.TAMANHO_BLOCO * 24, 5));
        paredes.add(new Parede(bloco, 6, false, Constantes.TAMANHO_BLOCO * 34, Constantes.TAMANHO_BLOCO * 17, 6));
        paredes.add(new Parede(bloco, 7, false, Constantes.TAMANHO_BLOCO * 28, Constantes.TAMANHO_BLOCO * 12, 7));
        paredes.add(new Parede(bloco, 19, false, Constantes.TAMANHO_BLOCO * 0, Constantes.TAMANHO_BLOCO * 13, 8));
        paredes.add(new Parede(bloco, 11, false, Constantes.TAMANHO_BLOCO * 18, Constantes.TAMANHO_BLOCO * 15, 9));
        paredes.add(new Parede(bloco, 7, false, Constantes.TAMANHO_BLOCO * 5, Constantes.TAMANHO_BLOCO * 4, 10));
        paredes.add(new Parede(bloco, 13, false, Constantes.TAMANHO_BLOCO * 9, Constantes.TAMANHO_BLOCO * 7, 11));
        paredes.add(new Parede(bloco, 19, false, Constantes.TAMANHO_BLOCO * 21, Constantes.TAMANHO_BLOCO * 5, 12));
        paredes.add(new Parede(bloco, 8, true, Constantes.TAMANHO_BLOCO * 11, Constantes.TAMANHO_BLOCO * 18, 13));
        paredes.add(new Parede(bloco, 10, true, Constantes.TAMANHO_BLOCO * 23, Constantes.TAMANHO_BLOCO * 20, 14));
        paredes.add(new Parede(bloco, 13, true, Constantes.TAMANHO_BLOCO * 28, Constantes.TAMANHO_BLOCO * 12, 15));
        paredes.add(new Parede(bloco, 3, true, Constantes.TAMANHO_BLOCO * 18, Constantes.TAMANHO_BLOCO * 13, 16));
        paredes.add(new Parede(bloco, 5, true, Constantes.TAMANHO_BLOCO * 21, Constantes.TAMANHO_BLOCO * 5, 17));
        paredes.add(new Parede(bloco, 5, true, Constantes.TAMANHO_BLOCO * 5, Constantes.TAMANHO_BLOCO * 4, 18));
        
        
        
    }
    
}
