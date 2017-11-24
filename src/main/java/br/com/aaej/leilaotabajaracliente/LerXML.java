/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaracliente;

import br.com.aaej.leilaotabajaraserver.Produto;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author TI
 */
public class LerXML {

    ArrayList<Produto> produtos;

    public LerXML() {
        produtos = new ArrayList<>();
    }

    public ArrayList<Produto> getList() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new File("ListaProdutos.xml"));
            Element root = doc.getRootElement();
            List<Element> listaElementos = root.getChildren();
            for (Element elem : listaElementos) {
                Produto p = new Produto(elem.getChild("nome").getText(),
                        elem.getChild("caracteristica").getText(),
                        Double.parseDouble(elem.getChild("precoInicial").getText()));
                produtos.add(p);
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(LerXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return produtos;
    }
}
