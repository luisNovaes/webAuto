/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aplicacao.daos;

import br.com.aplicacao.modelos.Pecas;
import br.com.aplicacao.modelos.Usuario;
import br.com.aplicacao.utilidades.conexao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author luis.silva
 */
public class usuarioDAO {

    public String salvar(String nome, String senha) {

        nome = "nome";
        senha = "senha";

        EntityManager manager = conexao.getEntityManager();
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {
            Usuario usuario = manager.find(Usuario.class, 1L);

            usuario.setNome(nome);
            usuario.setSenha(senha);

            manager.persist(usuario);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Erro (" + e.getMessage()
                    + ") ao tentar cadastrar usuario no banco");

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
            CriteriaQuery<Pecas> criteriaQuery = builder.createQuery(Pecas.class);

            Root<Pecas> pecas = criteriaQuery.from(Pecas.class);
            criteriaQuery.select(pecas);

            TypedQuery<Pecas> query = manager.createQuery(criteriaQuery);
            List<Pecas> pecass = query.getResultList();

            for (Pecas pe : pecass) {
                System.out.println(pe.getIdPeca() + " - "
                        + pe.getNome() + " - "
                        + pe.getModelo() + " - "
                        + pe.getFabricante() + " - "
                        + pe.getValorPeca() + " - "
                        + pe.getObservacao()
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

    public String buscarPecaPorNome() {
        EntityManager manager = conexao.getEntityManager();

        try {
            Query query = manager.createQuery(
                    " from Pecas where nome = :nome");

            query.setParameter("nome", "Lâmpada");
            List pecass = query.getResultList();

            if (pecass.isEmpty()) {
                System.out.println("Peça não cadastrada!");
            } else {

                for (Object obj : pecass) {
                    Pecas p = (Pecas) obj;

                    System.out.println(p.getIdPeca() + " - - "
                            + p.getNome() + " - "
                            + p.getModelo() + " - "
                            + p.getFabricante() + " - "
                            + p.getValorPeca() + " - "
                            + p.getAgendamento().getIdOS() + " - "
                            + p.getAgendamento().getFuncionarios().getPessoa().getNome() + " - "
                            + p.getAgendamento().getVeiculo().getPlaca() + " - "
                            + p.getObservacao()
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Erro (" + e.getMessage()
                    + ") ao tentar buscar Peças!");

        } finally {
            manager.close();
            conexao.close();
        }

        return "Operação realizada com sucesso!";

    }
}
