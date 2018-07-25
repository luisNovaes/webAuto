/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.daos;

import br.com.aplicacao.modelos.Escolaridade;
import br.com.aplicacao.modelos.Especialidade;
import br.com.aplicacao.modelos.Filial;
import br.com.aplicacao.modelos.Funcao;
import br.com.aplicacao.modelos.Funcionario;
import br.com.aplicacao.modelos.Pessoa;
import br.com.aplicacao.modelos.Setor;
import br.com.aplicacao.modelos.Turno;
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
public class FuncionarioDAO extends conexao {

    public String salvar() {

        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

            Pessoa pessoa = manager.find(Pessoa.class, 3L);

            Funcionario funcionario = new Funcionario();

            funcionario.setFuncao(Funcao.GERENTE);
            funcionario.setEspecialidade(Especialidade.MECATRÔNICA);
            funcionario.setEscolaridade(Escolaridade.PÓS_GRADUADO);
            funcionario.setDataAdminissao(new Date());
            funcionario.setDataDemissao(new Date());
            funcionario.setFilial(Filial.CUIABÁ);
            funcionario.setRg("02125253-25");
            funcionario.setSetor(Setor.SUPERVISAO);
            funcionario.setTurno(Turno.ADM);
            funcionario.setPessoa(pessoa);

            manager.persist(funcionario);
            tx.commit();

        } catch (Exception e) {
            System.out.println("Erro ("
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
            CriteriaQuery<Funcionario> criteriaQuery = builder.createQuery(Funcionario.class);

            Root<Funcionario> funcionario = criteriaQuery.from(Funcionario.class);
            criteriaQuery.select(funcionario);

            TypedQuery<Funcionario> query = manager.createQuery(criteriaQuery);
            List<Funcionario> funcionarios = query.getResultList();

            for (Funcionario f : funcionarios) {
                System.out.println(f.getIdfucionario() + " - "
                        + f.getPessoa().getNome() + " - "
                        + f.getPessoa().getDatabascimento() + " - "
                        + f.getDataAdminissao() + " - "
                        + f.getDataDemissao() + " - "
                        + f.getEscolaridade() + " - "
                        + f.getEspecialidade() + " - "
                        + f.getFuncao() + " - "
                        + f.getFilial() + " - "
                        + f.getSetor() + " - "
                        + f.getTurno()
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
