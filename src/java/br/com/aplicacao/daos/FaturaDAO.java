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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    public String buscarTodos() {
        EntityManager manager = conexao.getEntityManager();

        try {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Fatura> criteriaQuery = builder.createQuery(Fatura.class);

            Root<Fatura> fatura = criteriaQuery.from(Fatura.class);
            criteriaQuery.select(fatura);

            TypedQuery<Fatura> query = manager.createQuery(criteriaQuery);
            List<Fatura> faturas = query.getResultList();

            for (Fatura fa : faturas) {
                System.out.println(fa.getCodDocumento() + " - "
                        + fa.getAgendamento().getVeiculo().getPlaca() + " - "
                        + fa.getAgendamento().getVeiculo().getPropietario().getNome() + " - "
                        + fa.getAgendamento().getIdOS() + " - "
                        + fa.getAgendamento().getTipoServico() + " - "
                        + fa.getAgendamento().getPecas().getNome() + " - "
                        + fa.getAgendamento().getFuncionarios().getPessoa().getNome() + " - "
                        + fa.getAgendamento().getValorTotalServico() + " - "
                        + fa.getValorTotDoc() + " - "
                        + fa.getStatusDoc());
            }

        } catch (Exception e) {
            System.out.println("Erro (" + e.getMessage()
                    + ") ao consultar pessoa no banco!");

        } finally {
            manager.close();
            conexao.close();
        }

        return "Operação realizada com sucesso!";
    }
}
