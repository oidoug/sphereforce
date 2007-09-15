/*
 * Cenario_Stones.java
 *
 * Created on Sep 6, 2007, 4:20:23 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;
import labirinto.core.*;

/**
 *
 * @author Douglas Schmidt
 */
public class Cenario {
    
    protected LinkedList<Parede> paredes;
    protected LinkedList<Buraco> buracos;
    protected LinkedList<Pedra> pedras;
    protected Esfera bluesphere;
    protected Esfera redsphere;
    
    protected ConectionTcp conTcp;
    protected ConectionUdp conUdp;
    
    protected Marca inicio;
    protected Marca fim;
    
    protected Image background;
    protected Image buraco;
    protected Image bloco;
    protected Image pedra;
    protected Image marca_inicio;
    protected Image marca_fim;
    
    protected boolean servidor;
    
    protected int dificuldade = 1; // 1 - 5
    
    protected int bluePoint;
    protected int redPoint;
    
    public Cenario(Image background, Image buraco, Image bloco, Image pedra, Image marca_inicio, Image marca_fim, int dificuldade) {
        
        paredes = new LinkedList<Parede>();
        buracos = new LinkedList<Buraco>();
        pedras = new LinkedList<Pedra>();
        
        this.background = background;
        this.buraco = buraco;
        this.bloco = bloco;
        this.pedra = pedra;
        this.marca_inicio = marca_inicio;
        this.marca_fim = marca_fim;
        
        this.dificuldade = dificuldade;
        
        zeraPonits();
    }
    
    public void setServidor(boolean server){
        servidor = server;
    }
    
    public void setBluesphere(Esfera esfera){
        bluesphere = esfera;
    }
    
    public void setRedsphere(Esfera esfera){
        redsphere = esfera;
    }
    
    public void setConTcp(ConectionTcp conn){
        conTcp = conn;
    }
    
    public void setConUdp(ConectionUdp conn){
        conUdp = conn;
    }
    
    public Image getBlocoImg() {
        return bloco;
    }
    
    public Image getBackgroundImg() {
        return background;
    }
    
    public Image getBuracoImg() {
        return buraco;
    }
    
    public Image getPedraImg() {
        return pedra;
    }
    
    public LinkedList<Parede> getParedes() {
        return paredes;
    }
    
    public LinkedList<Buraco> getBuracos() {
        return buracos;
    }
    
    public LinkedList<Pedra> getPedras() {
        return pedras;
    }
    
    public void setBuracos(LinkedList<Buraco> holes){
        buracos = holes;
    }
    
    public void setPedras(LinkedList<Pedra> stones){
        pedras = stones;
    }
    
    private void zeraPonits(){
        bluePoint = 0;
        redPoint = 0;
    }
    /** pinta todos os elementos do cenario */
    public int paint(Graphics g) {
        
        
        pintaBackground(g);
        pintaBuracos(g);
        pintaPedras(g);
        pintaParedes(g);
        pintaMarcas(g);
        pintaEsferas(g);
        pintaScores(g);
        
        if (redPoint == Constantes.POINTS_TO_WIN) // points_to_win
            return Constantes.CLIENT_WIN;
        else if (bluePoint == Constantes.POINTS_TO_WIN) //points to win
            return Constantes.SERVER_WIN;
        else
            return Constantes.NOBODY_WIN;  
    }
    
    private void pintaBackground(Graphics g){
        int x = 0;
        int y = 0;
        while (y < Constantes.WINDOW_HEIGHT) {
            
            x = 0;
            
            while (x < Constantes.WINDOW_WIDTH) {
                
                g.drawImage(background, x, y, null);
                x = x + 50;
            }
            y = y + 50;
        }
    }
    private void pintaBuracos(Graphics g){
        for (Buraco hole : buracos) {
            hole.paint(g);
        }
    }
    
    private void pintaParedes(Graphics g){
        for (Parede wall : paredes) {
            wall.paint(g);
        }
    }
    
    private void pintaPedras(Graphics g){
        for (Pedra stone : pedras) {
            stone.paint(g);
        }
    }
    
    private void pintaMarcas(Graphics g){
        inicio.paint(g);
        fim.paint(g);
    }
    
    private void pintaScores(Graphics g){
        // pinta o score
        if (servidor){
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(bluePoint),Constantes.WINDOW_WIDTH/2-50,Constantes.TAMANHO_BLOCO*3);
            g.setColor(Color.RED);
            g.drawString(String.valueOf(redPoint),Constantes.WINDOW_WIDTH/2+10,Constantes.TAMANHO_BLOCO*3);
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.RED);
            g.drawString(String.valueOf(redPoint),Constantes.WINDOW_WIDTH/2-50,Constantes.TAMANHO_BLOCO*3);
            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(bluePoint),Constantes.WINDOW_WIDTH/2+10,Constantes.TAMANHO_BLOCO*3);
            
        }
    }
    
    private void pintaEsferas(Graphics g){
        
        trataColisoes();
        //verifica o fim
        bluesphere.paint(g);
        redsphere.paint(g);
        
    }
    /**
     * Method: buracos()
     * Gera uma lista de buracos divididos em regioes definidas por
     * Constantes.DIVISOES_CANVAS;
     * Para gerar um buraco o mesmo nao deve colidir com nenhum outro elemento.
     */
    protected void buracos() {
        int randX;
        int randY;
        int nBuracosOk = 0;
        Random generator = new Random();
        Buraco hole;
        
        do {
            randX = generator.nextInt(Constantes.WINDOW_WIDTH);
            randY = generator.nextInt(Constantes.WINDOW_HEIGHT);
            
            hole = new Buraco(buraco, randX, randY);
            
            if (!hole.colideCom(inicio) && !hole.colideCom(fim) && !hole.colideCom(paredes)) {
                nBuracosOk++;
                buracos.add(hole);
            }
        } while (nBuracosOk <= dificuldade);
    }
    
    protected void pedras() {
        
        int randx;
        int randy;
        
        Pedra stone;
        Random generator = new Random();
        
        int nPedrasOk = 0;
        
        do {
            
            randx = generator.nextInt(Constantes.WINDOW_WIDTH);
            randy = generator.nextInt(Constantes.WINDOW_HEIGHT);
            
            stone = new Pedra(pedra, randx, randy);
            
            if (!stone.colideCom(inicio) && !stone.colideCom(fim) && !stone.colideComParedes(paredes) && !stone.colideComBuracos(buracos)) {
                nPedrasOk++;
                pedras.add(stone);
            }
        } while (nPedrasOk <= dificuldade);
    }
    
    protected int getQntBuracos(){
        return buracos.size();
    }
    
    protected int getQntPedras(){
        return pedras.size();
    }
    
    protected Marca getFim(){
        return fim;
    }
    
    protected void gerarCenario(){
        this.buracos();
        this.pedras();
    }
    
    protected void gerarCenario( LinkedList<Buraco> buracos, LinkedList<Pedra> pedras){
        this.setBuracos(buracos);
        this.setPedras(pedras);
    }
    
    protected void zerarCenario(){
        this.buracos.clear();
        this.pedras.clear();
    }
    
    protected void zeraPoints(){
        bluePoint = 0;
        redPoint = 0;
    }
    
    public void trataColisoes() {
        bluesphere.trataBuracos(buracos, inicio);
        redsphere.trataBuracos(buracos, inicio);
        
        bluesphere.trataParedes(paredes);
        redsphere.trataParedes(paredes);
        
        bluesphere.trataPedras(pedras);
        redsphere.trataPedras(pedras);
        
        if ( bluesphere.trataMarca(fim) ){
            this.bluePoint++;
            //this.playFinishSound();
            
            respaw();
        } else if (redsphere.trataMarca(fim)){
            this.redPoint++;
            //this.playFinishSound();
            
            respaw();
        }
    }
    
    public void respaw(){
        
        bluesphere.setXY((int) inicio.getX() + 15, (int) inicio.getY() + 12);
        bluesphere.setVelXY(0,0);
        redsphere.setXY((int) inicio.getX() + 55, (int) inicio.getY() + 12);
        redsphere.setVelXY(0,0);
        
    }
}
