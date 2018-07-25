/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.utilidades;

import br.com.aplicacao.modelos.Agendamento;
import br.com.aplicacao.modelos.Pecas;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author luis.silva
 */
public class inserirPecas {

    public static void main(String[] args) {
        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {
            Agendamento agendamento = manager.find(Agendamento.class, 1L);

            Pecas pecas = new Pecas();
            pecas.setNome("Jogo Velas");
            pecas.setModelo("45AC");
            pecas.setFabricante("Moura");
            pecas.setValorPeca(new BigDecimal(100));
            pecas.setObservacao("testada e ok");
            //pecas.setAgendamento(agendamento);

            manager.persist(pecas);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Erro (" + e.getMessage()
                    + ") ao tentar inserir dados no banco");
        } finally {
            manager.close();
            conexao.close();

        }

    }

}
