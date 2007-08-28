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
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Douglas Schmidt
 */
class Logo implements Runnable {

    private Main applet;

    private LinkedList<Image> logos;

    private Graphics g;

    private Thread logoThread;

    public Logo(Main applet) {
        this.applet = applet;
        logos = new LinkedList<Image>();
        logoThread = new Thread(this);
    }

    public void addLogo(Image logo) {
        if (!logos.add(logo)) {
            System.err.println("erro: Logo<imagem> nao foi adicionado.");
        }
        Main.loading.addImage(logo, 0);
    }

    public void paint(Graphics g) {
        this.g = g;
        try {
            logoThread.start();
        } catch (Exception e) {
            System.err.println("erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Stop
     * stop to showing the logos and jump to menu option
     */
    public void stop() {
        logoThread.interrupt();
    }

    public void run() {

        applet.state = Main.LOGO;
        long stopTime;
        long startTime;

        Iterator it;

        for (it = logos.iterator(); it.hasNext();) {
            stopTime = 100;
            startTime = System.currentTimeMillis();

            while (logoThread.isAlive()) {
                if (stopTime == System.currentTimeMillis() - startTime) {
                    break;
                }
                g.drawImage((Image) it.next(), 0, 0, null);
            }
        }
        // logo deve mudar o estado para MENU, mas ainda nao foi implementado
        applet.state = Main.GAME_ON;
    }
}
