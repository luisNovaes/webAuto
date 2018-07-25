/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.daos;

import br.com.aplicacao.modelos.Agendamento;
import br.com.aplicacao.modelos.Fatura;
import br.com.aplicacao.modelos.StatusDoc;
import br.com.aplicacao.utilidades.conexao;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author luis.silva
 */
public class FaturaDAO extends conexao {

    public String salvar() {

        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

            Agendamento agenda = manager.find(Agendamento.class, 2L);

            Fatura fatura = new Fatura();

            fatura.setAgendamento(agenda);
            fatura.setStatusDoc(StatusDoc.OUTROS);
            fatura.setValorTotDoc(new BigDecimal(250));

            manager.persist(fatura);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Erro (" + e.getMessage()
                    + ") ao tentar gravar no banco!");

        } finally {
            manager.close();
            conexao.close();
        }

        return "Operação realizada com sucesso!";
    }
}