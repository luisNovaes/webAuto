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
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
}
