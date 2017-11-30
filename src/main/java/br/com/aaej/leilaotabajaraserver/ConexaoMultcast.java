/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaraserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class ConexaoMultcast extends Thread {

    int porta;
    String nome;
    String grupo;
    byte[] buffer;
    DatagramPacket pacote;
    Boolean stop = true;
    MulticastSocket socket;
    DatagramPacket msgOut;

    public ConexaoMultcast(int porta, String nome, String grupo) {
        this.porta = porta;
        this.nome = nome;
        this.grupo = grupo;
    }
    public void parar(){
        try {
            socket.leaveGroup(InetAddress.getByName(grupo));
            socket.close();
            stop = false;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConexaoMultcast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConexaoMultcast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void enviarMensagem(byte[] data) {
        
        try {
            msgOut = new DatagramPacket(data, data.length, InetAddress.getByName(grupo), porta);
            socket.send(msgOut);
        } catch (IOException ex) {
            System.out.println("ConectarThread - deu pal para enviar mensagem");
        }
    }
    public void run() {
        try {
            InetAddress group = InetAddress.getByName(grupo);
            socket = new MulticastSocket(porta);
            socket.joinGroup(group);
            System.out.println("Join multcast");            
  
            while (stop) {
                buffer = new byte[1024];
                pacote = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacote);
                 buffer = pacote.getData();                
                String mensagem = new String(buffer);
                System.out.println(mensagem);
                pacote = new DatagramPacket(buffer, buffer.length);

            }

        } catch (Exception e) {
            System.out.println("Deu pal no ConectarThread RUN():" + e);
        }
    }
}
