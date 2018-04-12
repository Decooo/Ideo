package com.jakub.service;

import com.jakub.entity.Tree;
import com.jakub.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jakub on 12.04.2018.
 */
@Service
public class TreeService {
	@Autowired
	TreeRepository treeRepository;

	@PersistenceContext
	EntityManager em;
	//private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(this);

	public List<Tree> findAll() {
		return treeRepository.findAll();
	}

	public void add(Integer idParent, String nameLeaf) {
		//EntityManager entityManager = entityManagerFactory.createEntityManager();
		StoredProcedureQuery query = em.createStoredProcedureQuery("add_nodeOrLeaf", Tree.class)
				.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Integer.class,ParameterMode.IN)
				.setParameter(1,nameLeaf)
				.setParameter(2, idParent);
		query.execute();
		em.close();
	}

}
