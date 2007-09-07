/*
 * Logo.java
 *
 * Created on Aug 28, 2007, 10:51:32 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

/**
 *
 * @author Douglas Schmidt
 */
class Logo {

    private final long OUT_TIME = 4000;

    private long stopTime;
    private long startTime;
    private boolean novoLogo = true;

    private int img_size = 0;

    private Main applet;

    private LinkedList<Image> logos;

    public Logo(Main applet) {
        this.applet = applet;
        logos = new LinkedList<Image>();
    }

    @SuppressWarnings(value = "unchecked")
    public void addLogo(Image logo) {
        addLogo(logo, 0);
    }

    @SuppressWarnings(value = "unchecked")
    public void addLogo(Image logo, int size) {
        try {
            logos.addLast(logo);
        } catch (ExceptionInInitializerError ex) {
            System.err.println("erro: init list " + ex.getMessage());
        }
        this.img_size = size;
    }

    public void paint(Graphics g) {
        if (!applet.keyWasPressed()) {

            if (novoLogo) {
                stopTime = OUT_TIME;
                startTime = System.currentTimeMillis();
            }
            if (stopTime >= System.currentTimeMillis() - startTime && logos.isEmpty() == false) {
                if (img_size == 0) {
                    g.drawImage(logos.getFirst(), 0, 0, null);
                } else {
                    g.drawImage(logos.getFirst(), Constantes.WINDOW_WIDTH / 2 - (img_size / 2), Constantes.WINDOW_HEIGHT / 2 - (img_size / 2), applet);
                }
                novoLogo = false;
            } else if (!logos.isEmpty()) {
                logos.removeFirst();
                novoLogo = true;
            } else {
                // logo deve mudar o estado para MENU, mas ainda nao foi implementado
                applet.state = Main.MENU;
            }
        } else {
            applet.state = Main.MENU;
        }
    }

    /** Stop
     * stop to showing the logos and jump to menu option
     */
    public void stop() {
        this.applet.state = Main.MENU;
    }
}
