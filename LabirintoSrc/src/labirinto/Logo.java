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
import java.util.ListIterator;

/**
 *
 * @author Douglas Schmidt
 */
class Logo {

    private final long OUT_TIME = 100;

    private long stopTime;
    private long startTime;
    private boolean novoLogo = true;

    private Main applet;

    private LinkedList<Image> logos;

    public Logo(Main applet) {
        this.applet = applet;
        logos = new LinkedList<Image>();
    }

    @SuppressWarnings(value = "unchecked")
    public void addLogo(Image logo) {
        try {
            logos.add(logo);
        } catch (ExceptionInInitializerError ex) {
            System.err.println("erro: init list " + ex.getMessage());
        }
    }

    public void paint(Graphics g) {

//        for (Image logo : logos) {
        if (novoLogo) {
            stopTime = OUT_TIME;
            startTime = System.currentTimeMillis();
        }
//            g.drawImage(logo, 0, 0, null);
//
//            if(this.applet.state != Main.LOGO) break;
//
//            while (this.applet.state == Main.LOGO) {
//                if (stopTime == System.currentTimeMillis() - startTime) {
//                    break;
//                }
//            }
//
//        }
        if (stopTime >= System.currentTimeMillis() - startTime) {
            g.drawImage(logos.getFirst(), 0, 0, null);
            novoLogo = false;
        } else if (logos.isEmpty()) {
            // logo deve mudar o estado para MENU, mas ainda nao foi implementado
            applet.state = Main.MENU;
        } else {
            logos.removeFirst();
            novoLogo = true;
        }
    }

    /** Stop
     * stop to showing the logos and jump to menu option
     */
    public void stop() {
        this.applet.state = Main.MENU;
    }
}
