package com.everhomes.learning.demos.treeMap.ycx;

public class BinSortTree {  
    private BinSortTree lChild;// 左孩子  
    private BinSortTree rChild;// 右孩子  
    private BinSortTree parent;// 父节点  
    private Long data;// 数据域  
  
    public BinSortTree(BinSortTree lChild, BinSortTree rChild, Long data) {  
        super();  
        this.lChild = lChild;  
        this.rChild = rChild;  
        this.data = data;  
    }  
  
    public BinSortTree(Long data) {  
        this(null, null, data);  
    }  
  
    public BinSortTree() {  
        super();  
    }  
  
    // 插入数据  
    //若左子树不为空,则左子树上所有节点的值均小于根节点的值；若右子树不为空,则右子树上所有节点的值均大于根节点的值。
    public boolean insert(BinSortTree tree, Long item) {  
        if (item < tree.data) {// 左边插入  
            if (tree.lChild == null) {  
                BinSortTree temp = new BinSortTree(item);  
                tree.lChild = temp;  
                temp.parent = tree;  
                return true;  
            } else {  
                insert(tree.lChild, item);  
            }  
        } else if (item > tree.data) {// 右边插入  
            if (tree.rChild == null) {  
                BinSortTree temp = new BinSortTree(item);  
                tree.rChild = temp;  
                temp.parent = tree;  
                return true;  
            } else {  
                insert(tree.rChild, item);  
            }  
        }  
        return false;  
    }  
    
    /**
	 * 删除二叉排序树中的结点
	 * 分为三种情况：（删除结点为*p ，其父结点为*f）
	 * （1）要删除的*p结点是叶子结点，只需要修改它的双亲结点的指针为空
	 * （2）若*p只有左子树或者只有右子树，直接让左子树/右子树代替*p
	 * （3）若*p既有左子树，又有右子树
	 * 		用p左子树中最大的那个值（即最右端S）代替P，删除s，重接其左子树
	 * */
    public void deleteBST(BinSortTree tree, Long item) {
    	if(tree != null) {
    		if(tree.data.equals(item)) {
        		if(tree.lChild == null && tree.rChild == null) {
        			tree.parent = null;
        		}else if(tree.lChild != null && tree.rChild == null) {
        			tree.lChild = tree;
        		}else if(tree.lChild == null && tree.rChild != null) {
        			tree.rChild = tree;
        		}else {
        			BinSortTree temp = tree.lChild;
        			while(temp.rChild != null) {
        				temp = temp.rChild;
        			}
        			tree.data = temp.data;//用p左子树中最大的那个值（即最右端S）代替P
        			temp.parent.rChild = null;//删除S节点
        		}
        	}else {
        		deleteBST(tree.lChild, item);
        		deleteBST(tree.rChild, item);
        	}
    	}
    }
  
    // 中序遍历 (对于二叉排序树 相当于从小到大输出)  
    public void inorder(BinSortTree tree) {  
        if (tree != null) {  
            inorder(tree.lChild);  
            visit(tree.data);  
            inorder(tree.rChild);  
        }  
    }  
  
    private void visit(Long data) {  
        System.out.print(data + " ");  
    }  
}