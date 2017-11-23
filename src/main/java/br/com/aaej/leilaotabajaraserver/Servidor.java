/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaraserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author aluno
 */
public class Servidor extends Thread{
    int port;
    IniciarLeilaoFrame leilao;
    public Servidor(int port, IniciarLeilaoFrame leilao){
        this.port = port;
        this.leilao = leilao;
    }
        
         public void run() {
        System.out.print("\nRunning TCPServerThread on port "+port+"...");
        try {
            ServerSocket listenSocket = new ServerSocket(port, 5);
            while (true) {                
                System.out.print("\n\tWaiting a connection...");
                Socket clientSocket = listenSocket.accept();
                System.out.print("\n\t\tConnected to "
                        +clientSocket.getInetAddress().toString()+" at port "
                        +clientSocket.getPort());
                Conexao c = new Conexao(clientSocket,leilao);
                leilao.adicionarConexao(c);
                c.start();
            }
        } catch (IOException e) {
            System.out.println("IO: "+e.getMessage());
        }
    }
}

