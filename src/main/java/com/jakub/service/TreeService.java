package com.jakub.service;

import com.jakub.entity.Tree;
import com.jakub.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by jakub on 12.04.2018.
 */
@Service
public class TreeService {
	@Autowired
	TreeRepository treeRepository;

	@PersistenceContext
	EntityManager em;

	public List<Tree> findAll() {
		return treeRepository.findAll();
	}

	public Optional<Tree> findById(int id){
		Optional<Tree> tree = treeRepository.findById(id);
		return tree;
	}

	public void add(Integer idParent, String nameLeaf) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("add_nodeOrLeaf", Tree.class)
				.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Integer.class,ParameterMode.IN)
				.setParameter(1,nameLeaf)
				.setParameter(2, idParent);
		query.execute();
		em.close();
	}

	public void delete(int idNode){
		StoredProcedureQuery query = em.createStoredProcedureQuery("deleteNode", Tree.class)
				.registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
				.setParameter(1, idNode);
		query.execute();
		em.close();
	}

	public void moveNode(int idtree, int idParent, String nameLeaf) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("MoveNode",Tree.class)
				.registerStoredProcedureParameter(1,Integer.class,ParameterMode.IN)
				.registerStoredProcedureParameter(2,Integer.class,ParameterMode.IN)
				.registerStoredProcedureParameter(3,String.class,ParameterMode.IN)
				.setParameter(1,idtree)
				.setParameter(2,idParent)
				.setParameter(3,nameLeaf);
		query.execute();
		em.close();
	}
}
