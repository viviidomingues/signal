package com.sample.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.sample.model.Signal;
import com.sample.model.SignalGroup;

public class SignalDaoImplement implements SignalDao {

	private static SignalDaoImplement INSTANCE = new SignalDaoImplement();
	Signal signal = new Signal();

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("HistoricalPersistenceUnit");
	private EntityManager entityManager = factory.createEntityManager();

	private Map<Long, Signal> database = new HashMap<Long, Signal>();
	private Long nextId = 0L;

	private SignalDaoImplement() {

	}

	public static SignalDaoImplement getInstance() {
		return INSTANCE;
	}

	@Override
	public Signal save(Signal signal) {
		System.out.println("============================" + signal.getId());
		EntityManager entityManager = factory.createEntityManager();
		
		try {
			entityManager.getTransaction().begin();
			System.out.println("Salvando sinal.");
			if (signal.getId() == null) {
				this.nextId = this.nextId + 1;
				entityManager.persist(signal);
			} else {
				signal = entityManager.merge(signal);
			}
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
		return signal;
	}

	@Override
	public Signal getById(Long id) {
		try {
            Signal signal = (Signal) entityManager
                       .createQuery(
                                   "SELECT descricao, ativo, informacoes from dbo.signais s where s.id = id")
                       .setParameter("id", id);

            return signal;
      } catch (NoResultException e) {
            return null;
      }
	}

	@Override
	public List<Signal> findAll() {
		return new ArrayList<Signal>(this.database.values());

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entitManager) {
		this.entityManager = entitManager;
	}
}