package com.everhomes.learning.demos.treeMap.ycx;

public class Test {

	public static void main(String[] args) {
		Long[] arr={63L,90L,70L,55L,67L,42L,98L,83L,10L,45L,58L};
		BinSortTree tree=new BinSortTree(arr[0]);//将第一个数字作为根节点
		for (Long i : arr) {
			tree.insert(tree, i);
		}
		tree.inorder(tree);
		
		tree.insert(tree, 60L);
		System.out.println("\n插入60之后的二叉排序树：");
		tree.inorder(tree);
		
		tree.deleteBST(tree, 90L);
		System.out.println("\n删除90之后的二叉排序树：");
		tree.inorder(tree);
		
		System.out.println("\n查询60是否在二叉排序树中：" + tree.searchBST(tree, 60L));
		System.out.println("\n查询90是否在二叉排序树中：" + tree.searchBST(tree, 90L));
	}

}
