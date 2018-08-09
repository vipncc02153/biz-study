package com.everhomes.learning.demos.treeMap.djm;


/**
 * <p>Title: test</p>
 * @author djm
 * @date 2018年8月8日
 * @version 1.0
 * <p>Description: 测试类</p>  
 */
public class test {

	public static void main(String[] args) {
		BinarySortTree bst = new BinarySortTree();
		/** 构建的二叉树没有相同元素 */
		int[] num = { 100, 88, 70, 800, 100, 44, 66, 59, 1, 999, -10, 0, -2, 2 };
		
		 for(int i = 0; i < num.length; i++){ bst.insertBST(num[i]); }
		 

		//bst.createBinarySortTreeArray(num);

		bst.nrInOrderTraverse();
		System.out.println(bst.searchBST(10));

		System.out.println(bst.searchBST(bst.getRootNode(), 100));

		bst.deleteBST2(2);
		
		bst.nrInOrderTraverse();
	}
}
