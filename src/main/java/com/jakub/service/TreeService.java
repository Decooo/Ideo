package com.jakub.service;

import com.jakub.entity.Tree;
import com.jakub.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jakub on 12.04.2018.
 */
@Service
public class TreeService {
	@Autowired
	TreeRepository treeRepository;

	public List<Tree> findAll() {
		return treeRepository.findAll();
	}

}
