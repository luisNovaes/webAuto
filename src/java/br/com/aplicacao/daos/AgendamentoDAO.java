/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.daos;

import br.com.aplicacao.modelos.Agendamento;
import br.com.aplicacao.modelos.Funcionario;
import br.com.aplicacao.modelos.HoraAgenda;
import br.com.aplicacao.modelos.Pecas;
import br.com.aplicacao.modelos.TipoServico;
import br.com.aplicacao.modelos.Veiculo;
import br.com.aplicacao.utilidades.conexao;
import java.math.BigDecimal;
import java.util.Date;
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
public class AgendamentoDAO extends conexao {

    public String salvar() {

        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

            Funcionario funcionario = manager.find(Funcionario.class, 2L);
            Pecas pecas = manager.find(Pecas.class, 3L);
            Veiculo veiculo = manager.find(Veiculo.class, 3L);

            Agendamento agenda = new Agendamento();

            agenda.setVeiculo(veiculo);
            agenda.setDataAgenda(new Date());
            agenda.setHoraAgenda(HoraAgenda.manha);
            agenda.setFuncionarios(funcionario);
            agenda.setPecas(pecas);
            agenda.setTipoServico(TipoServico.ÉLETRICO);
            agenda.setValorTotalServico(new BigDecimal(1000));
            agenda.setObservacao("veiculo em garantia");

            manager.persist(agenda);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Erro (" + e.getMessage()
                    + ") ao tentar gravar dadosno banco!");

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
            CriteriaQuery<Agendamento> criteriaQuery = builder.createQuery(Agendamento.class);

            Root<Agendamento> agendamento = criteriaQuery.from(Agendamento.class);
            criteriaQuery.select(agendamento);

            TypedQuery<Agendamento> query = manager.createQuery(criteriaQuery);
            List<Agendamento> agendamentos = query.getResultList();

            for (Agendamento a : agendamentos) {
                System.out.println(a.getDataAgenda() + " - "
                        + a.getHoraAgenda() + " - "
                        + a.getVeiculo().getPlaca() + " - "
                        + a.getVeiculo().getPropietario().getNome() + " - "
                        + a.getVeiculo().getPropietario().getCpf_cnpj() + " - "
                        + a.getVeiculo().getPropietario().getTelefone() + " - "
                        + a.getVeiculo().getPropietario().getEmail() + " - "
                        + a.getTipoServico() + " - "
                        + a.getFuncionarios().getPessoa().getNome() + " - "
                        + a.getPecas().getNome() + " - "
                        + a.getValorTotalServico() + " - "
                        + a.getObservacao().toUpperCase()
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
