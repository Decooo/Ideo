package com.jakub.entity;

import javax.persistence.*;

/**
 * Created by jakub on 12.04.2018.
 */
@Entity
@Table(name = "tree")
public class Tree {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idtree;
	private int idparent;
	private String name_leaf;
	private int number_left;
	private int number_right;

	public int getIdtree() {
		return idtree;
	}

	public void setIdtree(int idtree) {
		this.idtree = idtree;
	}

	public int getIdparent() {
		return idparent;
	}

	public void setIdparent(int idparent) {
		this.idparent = idparent;
	}

	public String getName_leaf() {
		return name_leaf;
	}

	public void setName_leaf(String name_leaf) {
		this.name_leaf = name_leaf;
	}

	public int getNumber_left() {
		return number_left;
	}

	public void setNumber_left(int number_left) {
		this.number_left = number_left;
	}

	public int getNumber_right() {
		return number_right;
	}

	public void setNumber_right(int number_right) {
		this.number_right = number_right;
	}

	@Override
	public String toString() {
		return name_leaf;
	}
}
