/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaraserver;

/**
 *
 * @author TI
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface LeilaoInterface extends Remote{
    
    public String Send(String argA) throws RemoteException;
    
}
