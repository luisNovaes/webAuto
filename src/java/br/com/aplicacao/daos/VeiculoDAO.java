/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.daos;

import br.com.aplicacao.modelos.Montadora;
import br.com.aplicacao.modelos.Pessoa;
import br.com.aplicacao.modelos.TipoVeiculo;
import br.com.aplicacao.modelos.Veiculo;
import br.com.aplicacao.utilidades.conexao;
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
public class VeiculoDAO extends conexao {

    public String salvar() {

        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

            Pessoa proprietario = manager.find(Pessoa.class, 2L);

            Veiculo veiculo = new Veiculo();
            veiculo.setPropietario(proprietario);
            veiculo.setPlaca("DEF-1234");
            veiculo.setTipoveiculo(TipoVeiculo.FROTISTA);
            veiculo.setMontadora(Montadora.VOLKSWAGEM);
            veiculo.setModelo("Gol");
            veiculo.setAnoFabricacao(2012);
            veiculo.setObservacao("Veiculo em garantia de serviço");

            manager.persist(veiculo);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Erro (" + e.getMessage()
                    + ") ao tentar salver no banco!");

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
            CriteriaQuery<Veiculo> criteriaQuery = builder.createQuery(Veiculo.class);

            Root<Veiculo> veiculo = criteriaQuery.from(Veiculo.class);
            criteriaQuery.select(veiculo);

            TypedQuery<Veiculo> query = manager.createQuery(criteriaQuery);
            List<Veiculo> veiculos = query.getResultList();

            for (Veiculo v : veiculos) {
                System.out.println(v.getIdVeiculo() + " - "
                        + v.getModelo() + " - "
                        + v.getPlaca() + " - "
                        + v.getAnoFabricacao() + " - "
                        + v.getTipoveiculo() + " - "
                        + v.getMontadora() + " - "
                        + v.getAnoFabricacao() + " - "
                        + v.getPropietario().getNome() + " - "
                        + v.getObservacao() + " - "
                        + v.getAgendamento().getDataAgenda() + " - "
                        + v.getAgendamento().getHoraAgenda()
                );
            }

        } catch (Exception e) {
            System.out.println("Erro (" + e.getMessage()
                    + ") ao consultar veículo no banco!");

        } finally {
            manager.close();
            conexao.close();
        }

        return "Operação realizada com sucesso!";
    }
}
