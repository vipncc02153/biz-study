package com.everhomes.learning.demos.treeMap.djm;

import java.util.Stack;

/**
 * <p>Title: BinarySortTree 二叉排序树）</p>
 * @author djm
 * @date 2018年8月8日
 * @version 1.0
 * <p>Description: </p>  
 */

public class BinarySortTree {

	private Node root = null;

	public Node getRootNode() {
		return this.root;
	}

	/** 向二叉排序树中插入结点 */
	public void insertBST(int key) {
		Node p = root;
		/** 记录查找结点的前一个结点 */
		Node prev = null;
		/** 一直查找下去，直到到达满足条件的结点位置 */
		while (p != null) {
			prev = p;
			if (key < p.getValue())
				p = p.getLeft();
			else if (key > p.getValue())
				p = p.getRight();
			else
				return;
		}
		/** prve是要安放结点的父节点，根据结点值得大小，放在相应的位置 */
		if (root == null)
			root = new Node(key);
		else if (key < prev.getValue())
			prev.setLeft(new Node(key));
		else
			prev.setRight(new Node(key));
	}

	/** 数组形式创建二叉树 */
	public void createBinarySortTreeArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				root = new Node(array[i]);
				continue;
			}
			Node node = root;
			while (true) {
				if (array[i] < node.getValue()) {
					if (node.getLeft() == null) {
						node.setLeft(new Node(array[i]));
						break;
					} else {
						node = node.getLeft();
					}
				} else if (array[i] == node.getValue()) {
					System.out.println("已存在" + array[i] + "，不再插入二叉排序树");
					break;
				} else {
					if (node.getRight() == null) {
						node.setRight(new Node(array[i]));
						break;
					} else {
						node = node.getRight();
					}
				}
			}
		}
	}

	/** 查找二叉排序树中是否有key值 */
	public boolean searchBST(int key) {
		Node current = root;
		while (current != null) {
			if (key == current.getValue())
				return true;
			else if (key < current.getValue())
				current = current.getLeft();
			else
				current = current.getRight();
		}
		return false;
	}

	public boolean searchBST(Node node, int key) {
		if (node == null) {
			return false;
		} else if (key == node.getValue()) {
			return true;
		} else if (key < node.getValue()) {
			return searchBST(node.getLeft(), key);
		} else {
			return searchBST(node.getRight(), key);
		}
	}

	/**
	 * 删除二叉排序树中的结点 分为三种情况：（删除结点为*p ，其父结点为*f） （1）要删除的*p结点是叶子结点，只需要修改它的双亲结点的指针为空
	 * （2）若*p只有左子树或者只有右子树，直接让左子树/右子树代替*p （3）若*p既有左子树，又有右子树
	 * 用p左子树中最大的那个值（即最右端S）代替P，删除s，重接其左子树
	 */
	public void deleteBST(int key) {
		deleteBST(root, key);
	}

	private boolean deleteBST(Node node, int key) {
		if (node == null)
			return false;
		else {
			if (key == node.getValue()) {
				return delete(node);
			} else if (key < node.getValue()) {
				return deleteBST(node.getLeft(), key);
			} else {
				return deleteBST(node.getRight(), key);
			}
		}
	}

	private boolean delete(Node node) {
		Node temp = null;
		/**
		 * 右子树空，只需要重接它的左子树 如果是叶子结点，在这里也把叶子结点删除了
		 */
		if (node.getRight() == null) {
			temp = node;
			node = node.getLeft();
		}
		/** 左子树空， 重接它的右子树 */
		else if (node.getLeft() == null) {
			temp = node;
			node = node.getRight();
		}
		/** 左右子树均不为空 */
		else {
			temp = node;
			Node s = node;
			/** 转向左子树，然后向右走到“尽头” */
			s = s.getLeft();
			while (s.getRight() != null) {
				temp = s;
				s = s.getRight();
			}
			node.setValue(s.getValue());
			if (temp != node) {
				temp.setRight(s.getLeft());
			} else {
				temp.setLeft(s.getLeft());
			}
		}
		return true;
	}

	/**
	 * 中序非递归遍历二叉树 获得有序序列
	 */
	public void nrInOrderTraverse() {
		Stack<Node> stack = new Stack<Node>();
		Node node = root;
		while (node != null || !stack.isEmpty()) {
			while (node != null) {
				stack.push(node);
				node = node.getLeft();
			}
			node = stack.pop();
			System.out.print(node.getValue() +" ");
			node = node.getRight();
		}
	}
	
	public boolean deleteBST2(int key) {
		Node node = root;
		// 存储 其双亲结点，如果是根节点，则双亲结点为null
		Node parentNode = null;

		while (node != null) {
			if (key < node.getValue()) {
				parentNode = node;
				node = node.getLeft();
			} else if (key > node.getValue()) {
				parentNode = node;
				node = node.getRight();
			} else {
				break;
			}
		}

		// 没有找到要删除的node
		if (node == null) {
			return false;
		}

		// 右子树为空 ，重接左子树
		if (node.getRight() == null) {
			if (parentNode != null) {
				if (parentNode.getLeft() == node)
					parentNode.setLeft(node.getLeft());
				else
					parentNode.setRight(node.getLeft());
			} else {
				root = node.getLeft();
			}
			// 左子树为空，重接右子树
		} else if (node.getLeft() == null) {
			if (parentNode != null) {
				if (parentNode.getLeft() == node)
					parentNode.setLeft(node.getRight());
				else
					parentNode.setRight(node.getRight());
			} else {
				root = node.getRight();
			}
			// 左右子树都不为空 ，可以将 要删除结点 按中序遍历的前驱或后继 替代 要删除结点的位置，此处取前驱
		} else {

			// 取前驱结点 ，先转左，然后一直到最右
			Node replaceNode = node.getLeft();
			Node replaceParentNode = node;

			if (replaceNode.getRight() != null) {
				replaceParentNode = replaceNode;
				replaceNode = replaceNode.getRight();
			}
			// 获取到前驱结点极其双亲结点

			// 如果前驱结点的双亲结点是 要删除的结点
			if (replaceParentNode == node)
				replaceParentNode.setLeft(replaceNode.getLeft());
			else
				replaceParentNode.setRight(replaceNode.getLeft());

			node.setValue(replaceNode.getValue());
		}

		return true;
	}

}
