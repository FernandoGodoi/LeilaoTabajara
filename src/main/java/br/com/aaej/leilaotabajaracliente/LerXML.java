/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaracliente;

import br.com.aaej.leilaotabajaraserver.Produto;
import java.util.ArrayList;

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
        }
        return produtos;
    }
}
