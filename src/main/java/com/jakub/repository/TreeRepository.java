package com.jakub.repository;

import com.jakub.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jakub on 12.04.2018.
 */
@Repository
public interface TreeRepository extends JpaRepository<Tree, Integer> {
}
