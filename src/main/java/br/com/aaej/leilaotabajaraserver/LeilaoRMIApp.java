/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaraserver;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author TI
 */
public class LeilaoRMIApp extends UnicastRemoteObject implements LeilaoInterface {

    IniciarLeilaoFrame leilao;

    public LeilaoRMIApp(IniciarLeilaoFrame leilao) throws RemoteException {
        this.leilao = leilao;
    }

    @Override
    public String Send(String data) throws RemoteException {
        String[] msg = data.split(";");

        switch (Integer.parseInt(msg[0])) {
            case 1:
                //this.nome = msg[1];
                leilao.adicionarComprador(msg[1]);
                leilao.atualizarConexao();
                return "1;Registrado!";
            case 2:
                Produto p = leilao.produtos.get(Integer.parseInt(msg[1]));
                if (p.isFinalizado()) {
                    return "1;Este produto foi Finalizado!";
                } else {
                    if (p.getPrecoVencedor() >= Double.parseDouble(msg[2])) {
                        return "1;Valor invalido";
                    } else {
                        
                        p.setNomeVencedor(msg[3]);
                        p.setPrecoVencedor(Double.parseDouble(msg[2]));
                        leilao.atualizarProdutos();
                        leilao.atualizarClientes("2;" + msg[1] + ";" + msg[2] + ";" + msg[3]);
                        return "1;Lance Aceito";
                    }
                }            
            default:
                return "1;opção invalida";
        }

        /* Send the response to client */
    }
}