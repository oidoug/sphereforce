/*
 * ConectionTcp.java
 *
 * Created on September 9, 2007, 8:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package labirinto.core;

/**
 *
 * @author r
 */

import java.io.*;
import java.net.*;
import labirinto.Constantes;
import labirinto.Main;
import java.awt.*;

public class ConectionTcp {
    

    private ServerSocket serverSocket;
    private Socket socket;
    private String message;
    
    private ObjectOutputStream output;
    private ObjectInputStream input;
    
    private int porta;
    private boolean servidor;
    
    private int qntBuraco;
    private int qntPedra;
    
    private boolean chatON;
    
    private int port = 4000;
    
    
    /** Creates a new instance of Conection */
    
    //cria instancia para o servidor
    public ConectionTcp(int port) throws Exception {
        servidor = true;
        try {
            serverSocket = new ServerSocket(port,1);
            socket = serverSocket.accept(); //espera cliente conectar
            inicializaStreams();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //cria instancia para o cliente
    public ConectionTcp(String ip, int port) throws Exception {
        servidor = false;
        InetAddress ipAddr = InetAddress.getByName(ip);
        try {
            socket = new Socket(ip, port);
            inicializaStreams();    
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void inicializaStreams() throws Exception {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
  
    // sender para mensagens String
    public void Send(DataChat data) throws Exception {
        try {
            output.writeObject(data);
            output.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    

    // receiver para mensagens String
    public DataChat getData() throws Exception {
        DataChat data = new DataChat();
        try {
            data = (DataChat) input.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    
    //manda a quantiadde de buracos e pedras pro cliente
    public void Send(int nburacos, int npedras) throws Exception{
        DataCord gameQnts = new DataCord();
        gameQnts.setXY(nburacos,npedras);
        try {
            output.writeObject(gameQnts);
            output.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void receiveQnts() throws Exception {
        DataCord gameQnts = new DataCord();
        try {
            gameQnts = (DataCord) input.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        this.qntBuraco = (int) gameQnts.getX();
        this.qntPedra = (int) gameQnts.getY();        
    }
    
    public void receiveSmt() throws Exception {
        
        try {
            Object object = (Object) input.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
     
    }
    
    public void limpaSaida(){
        try {
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public int getQntBuraco(){
        return qntBuraco;
    }
    
    public int getQntPedra(){
        return qntPedra;
    }
    
    public void setChatON(boolean chatON) {
        this.chatON = chatON;
    }
    
    public void Send(Buraco buraco) throws Exception {
        DataCord cord = new DataCord();
        cord.setXY(buraco.getX(),buraco.getY());
        try {
            output.writeObject(cord);
            output.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }        
    }
    
    public void Send(Pedra pedra) throws Exception {
        DataCord cord = new DataCord();
        cord.setXY(pedra.getX(), pedra.getY());
        try {
            output.writeObject(cord);
            output.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
      
    public Buraco getHole(Image b) throws Exception {
        DataCord cord = new DataCord();
        try {
            cord = (DataCord) input.readObject();        
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Buraco buraco = new Buraco(b, (int) cord.getX(), (int) cord.getY() );        
        return buraco;
    }
    
    public Pedra getStone(Image p) {
        DataCord cord = new DataCord();
        try {
            cord = (DataCord) input.readObject();        
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Pedra pedra = new Pedra(p, (int) cord.getX(), (int) cord.getY());        
        return pedra;
    }
           
}
