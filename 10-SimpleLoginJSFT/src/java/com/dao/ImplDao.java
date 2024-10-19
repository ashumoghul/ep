/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import Controllers.FacesUtil;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;


/**
 *
 * @author Jhon Baron
 */
public class ImplDao<T, ID> implements IDao<T, ID> {

    public ImplDao() {
        this.emf = Persistence.createEntityManagerFactory("SimpleLoginJSFTestPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void crear(T entity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            FacesUtil.addInfoMessage("Se almacenó en el sistema");
        } catch (PersistenceException pe) {
            FacesUtil.addErrorMessage("Error de Persisntencia: " + pe.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction() != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
                em = null;
            }
        }
    }

    @Override
    public <T> T consultar(Class<T> entityClass, Object primaryKey) {
        EntityManager em = getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        T ent = em.find(entityClass, primaryKey);
        et.commit();
        em.close();
        return ent;
    }

    public <T> T consultarC(Class<T> entityClass, Object primaryKey) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        T ent = em.find(entityClass, primaryKey);
        return ent;
    }

    @Override
    public <T> T modificar(T entity) {
        T ent = null;
        EntityManager em = getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            ent = em.merge(entity);
            et.commit();
            FacesUtil.addInfoMessage("Se actualizó en el sistema");
        } catch (PersistenceException pe) {
            et.rollback();
            FacesUtil.addErrorMessage("Error de persistencia " + pe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return ent;
    }

    @Override
    public void eliminar(Object entity) {
        EntityManager em = getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.remove(entity);
            em.getTransaction().commit();
        } catch (PersistenceException pe) {
            em.getTransaction().rollback();
            FacesUtil.addErrorMessage("Error de Persistencia: " + pe.getMessage());
        } finally {
            em.close();
        }

    }

    @Override
    public List<T> consultarTodo(Class<T> entityClass) {
        List<T> entidades;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        String clase = entityClass.getSimpleName();
        entidades=em.createQuery("select t from "+clase+" t").getResultList();
        em.close();
        return entidades;
    }
}
