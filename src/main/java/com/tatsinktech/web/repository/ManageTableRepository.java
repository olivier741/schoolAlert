/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktech.web.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author olivier.tatsinkou
 */
@Repository
public class ManageTableRepository {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public void createTable(String tableName) {
        String s = "CREATE TABLE %s  (MSISDN VARCHAR(100) NOT NULL UNIQUE)";
        String sql = String.format(s, tableName.toLowerCase());

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();

        System.out.println(sql);

    }

    @Transactional
    public void insertTable(String tableName, String msisdn) {
        String s = "INSERT INTO %s (MSISDN) VALUES ( %s )";
        String sql = String.format(s, tableName.toLowerCase(), msisdn);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Transactional
    public void updateTable(String tableName, String msisdn_old, String msisdn_new) {
        String s = "UPDATE %s SET msisdn = %s WHERE  msisdn = %s ";
        String sql = String.format(s, tableName.toLowerCase(), msisdn_new, msisdn_old);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public void deleteTable(String tableName, String msisdn) {
        String s = "DELETE FROM %s WHERE  msisdn = %s  ";
        String sql = String.format(s, tableName.toLowerCase(), msisdn);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public void dropTable(String tableName) {

        String s = " DELETE FROM  %s ";


        String sql = String.format(s, tableName.toLowerCase());

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public List<String> getTable(String tableName) {

        List<String> result = null;
        try {
            String s = "SELECT * FROM %s ";
            String sql = String.format(s, tableName.toLowerCase());

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            result = entityManager.createNativeQuery(sql).getResultList();

            entityManager.close();
        } catch (Exception e) {

        }

        return result;
    }

    @Transactional
    public List<String> getTableByMsisdn(String tableName, String msisdn) {

        List<String> result = null;
        try {
            String s = "SELECT * FROM %s WHERE msisdn LIKE '%s'";
            String sql = String.format(s, tableName.toLowerCase(), msisdn);

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            result = entityManager.createNativeQuery(sql).getResultList();

            entityManager.close();
        } catch (Exception e) {

        }

        return result;
    }
}
