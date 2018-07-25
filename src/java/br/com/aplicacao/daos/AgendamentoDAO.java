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
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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

}
