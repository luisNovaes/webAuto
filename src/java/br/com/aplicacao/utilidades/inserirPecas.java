/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.utilidades;

import br.com.aplicacao.daos.PecasDAO;

/**
 *
 * @author luis.silva
 */
public interface inserirPecas {

    public static void main(String[] args) {

        PecasDAO dao = new PecasDAO();
        dao.salvar();

    }

}
