/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.utilidades;

import br.com.aplicacao.modelos.Estado;
import br.com.aplicacao.modelos.Pessoa;
import br.com.aplicacao.modelos.TipoPessoa;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author luis.silva
 */
public class inserirPessoas {

    public static void main(String[] args) {
        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome("Marcos");
            pessoa.setTipoPessoa(TipoPessoa.FISICA);
            pessoa.setCpf_cnpj("123456789-12");
            pessoa.setEmail("fernando@gmail.com");
            pessoa.setTelefone("(65)999275216");
            pessoa.setEndereco("Rua das Arvores, numero 1000");
            pessoa.setCidade("São Paulo");
            pessoa.setEstado(Estado.SP);
            pessoa.setObservacao("cliente em trânsito");

            manager.persist(pessoa);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Erro (" + e.getMessage()
                    + ") ao tentar gravar no banco!");
        } finally {
            manager.close();
            conexao.close();

        }

    }
}
