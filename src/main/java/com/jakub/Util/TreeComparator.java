package com.jakub.Util;

import com.jakub.entity.Tree;

import java.util.Comparator;

/**
 * Created by jakub on 12.04.2018.
 */
public class TreeComparator implements Comparator<Tree>{
	@Override
	public int compare(Tree o1, Tree o2) {
		if(o1.getIdparent()>o2.getIdparent())
			return 1;
		if(o1.getIdparent()<o2.getIdparent())
			return -1;
		return 0;
	}
}
