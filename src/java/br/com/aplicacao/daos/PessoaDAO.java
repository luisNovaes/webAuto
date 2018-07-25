/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.daos;

import br.com.aplicacao.modelos.Estado;
import br.com.aplicacao.modelos.Pessoa;
import br.com.aplicacao.modelos.TipoPessoa;
import br.com.aplicacao.utilidades.conexao;
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
public class PessoaDAO extends conexao {

    public String salvar() {

        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome("Marcos");
            pessoa.setDatabascimento(new Date());
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
                    + ") ao tentar gravar Pessoa no banco!");
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
            CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);

            Root<Pessoa> pessoa = criteriaQuery.from(Pessoa.class);
            criteriaQuery.select(pessoa);

            TypedQuery<Pessoa> query = manager.createQuery(criteriaQuery);
            List<Pessoa> pessoas = query.getResultList();

            for (Pessoa p : pessoas) {
                System.out.println(p.getIdPessoa() + " - "
                        + p.getNome() + " - "
                        + p.getDatabascimento() + " - "
                        + p.getTipoPessoa() + " - "
                        + p.getCpf_cnpj() + " - "
                        + p.getEmail() + " - "
                        + p.getTelefone() + " - "
                        + p.getEndereco() + " - "
                        + p.getCidade() + " - "
                        + p.getEstado() + " - "
                        + p.getObservacao()
                );
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
