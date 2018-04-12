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
	private String name;
	private int left;
	private int right;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "Tree{" +
				"idtree=" + idtree +
				", idparent=" + idparent +
				", name='" + name + '\'' +
				", left=" + left +
				", right=" + right +
				'}';
	}
}
