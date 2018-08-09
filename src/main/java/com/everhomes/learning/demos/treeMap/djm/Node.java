package com.everhomes.learning.demos.treeMap.djm;


/**
 * <p>Title: Node</p>
 * @author djm
 * @date 2018年8月8日
 * @version 1.0
 * <p>Description: 二叉树的结点定义</p>  
 */
public class Node {
	private int value;
	private Node left;
	private Node right;

	public Node() {
	}

	public Node(Node left, Node right, int value) {
		this.left = left;
		this.right = right;
		this.value = value;
	}

	public Node(int value) {
		this(null, null, value);
	}

	public Node getLeft() {
		return this.left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return this.right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [value=" + value + ", left=" + left + ", right=" + right + "]";
	}
	
}