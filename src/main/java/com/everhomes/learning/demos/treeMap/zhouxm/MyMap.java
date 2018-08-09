package com.everhomes.learning.demos.treeMap.zhouxm;


/**
 * @author feiyue
 * @className MyMap
 * @description
 * @date 2018/8/6
 **/
public class MyMap {

    private Node root;

    public MyMap(Node root) {
        this.root = root;
    }

    /**
    * @author feiyue
    * @description  根据插入Node的 key 的大小, 插入数据
    * @date  2018/8/7
    * @param
    * @return void
    **/
    public void put(Node node){

        //定义一个存放临时根节点的 MyMap - n 和 上一个根节点的 node - prev
        Node n = root;
        Node prev = null;
        while(n !=null){
            prev = n;
            if(node.getKey() < n.getKey())
                n = n.getLeft();
            else if(node.getKey() > n.getKey())
                n = n.getRight();
            else
                return;
        }
        node.setParent(prev);
        if(root == null)
            root = node;
        else if(node.getKey() < prev.getKey())
            prev.setLeft(node);
        else prev.setRight(node);
    }

    /**
    * @author feiyue
    * @description  根据要查找node的 key, 进行数据的检索
    * @date  2018/8/7
    * @param
    * @return com.everhomes.learning.demos.treeMap.zhouxm.MyMap.MyMap
    **/
    public Node get(int key){
        Node temp = root;
        while(null != temp){
            if(temp.getKey() == key)
                return temp;
            else if(temp.getKey() > key)
                temp = root.getLeft();
            else
                temp = root.getRight();
        }
        return null;
    }

    /**
    * @author feiyue
    * @description 根据要移除node的 key, 进行数据的移除
    * @date  2018/8/7
    * @param
    * @return boolean
    **/
    public void remove(int key){
        removeNode(this.root, key);
    }

    private boolean removeNode(Node node, int key){
        if(node == null)
            return false;
        else{
            if(key == node.getKey())
                return deleteNode(node);
            else if(key < node.getKey())
                return removeNode(node.getLeft(), key);
            else
                return removeNode(node.getRight(), key);
        }
    }

    private boolean deleteNode(Node node){
        Node temp = null;

        // 右子树空, 用要删除的节点的左子树替换该节点
        if(node.getRight() == null){
            node = node.getLeft();
        }

        // 左子树空, 用要删除的节点的右子树替换该节点
        else if(node.getLeft() == null){
            node = node.getRight();
        }

        // 左右子树不为空, 用该根节点左子树最大值取代根节点
        else{
            temp = node;

            // 找到左子树最右节点
            Node s = node;
            s = s.getLeft();
            while(s.getRight() != null){
                temp = s;
                s = s.getRight();
            }
            node.setValue(s.getValue());
            node.setKey(s.getKey());

            // 替换节点的移除
            if(temp != node){
                temp.setRight(s.getLeft());
            }
            else{
                temp.setLeft(s.getLeft());
            }
        }
        return true;
    }

}
