package com.everhomes.learning.demos.treeMap.hlm;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 插入子节点方法 addNode(Node pNode , Node node ,Position position)
 * pNode 完父节点；node 即将要插入的节点；position；表示插入的位置，左left,右right
 * 着色方法 colorNode(Node node , Color color)
 * node 即将着色的节点；color 表示着色 红red 黑 black
 *
 */
public class MyTreeMap  {

    Logger log = LoggerFactory.getLogger(MyTreeMap.class) ;

    /**
     * 根节点
     */
    private Node root ;

    /**
     * 元素个数
     */
    private transient int size ;

    public int getSize(){
        return this.size ;
    }
    /**
     * 添加k-v方法
     * @param k
     * @param v
     * @return
     */
    public Object put(Object k ,Object v){

        Node node = new Node(k , v);

        //没有根那么传进来的就是根
        if(root == null){
            root = node ;
            size ++ ;
            return root.getKey();
        }

      // return addNode(root , node);
       return addRedBlankNode(root , node);
    }

    /**
     * 通过key获取value方法
     * @param k
     * @return
     */
    public Object get(Object k){

        Node node = get(this.root ,hash(k));
        return node==null? null : node.getValue() ;
    }

    /**
     * 通过key删除节点方法
     * @param k
     * @return
     */
    public Object remove(Object k){
        Node node = get(this.root ,hash(k));
        if(node == null){
            return null ;
        }
        Object returnValue = node.getValue();
        deleteNode(node);
        return returnValue;
    }



    private Node get(Node node ,int k){
        if(node == null){
            return null;
        }
        int tarkey = k ;
        int rootkey = hash(node.getKey());
        //命中马上返回
        if(tarkey == rootkey){
            return node ;
        }
        //在左分支
        if(tarkey < rootkey){
            return get(node.getLeft(),k);
        }
        //在右分支
        else if(tarkey > rootkey){
            return get(node.getRight(),k);
        }

        return null ;
    }
    /**
     * 返回root的中序遍历
     * @return
     */
    public List<Object> listNode(){

        if(this.root == null){
            return null;
        }
        List<Object> resultList = new ArrayList<Object>();
        midIteractor(this.root ,resultList );
        return resultList ;
    }

    /**
     * 中序遍历：左——>根——>右
     * @param node
     * @param resultList
     */
    private void midIteractor(Node node , List<Object> resultList){
        if(node == null){
            return ;
        }
        //当再无左子节点时，时候保存它的值了
        if(node.getLeft() != null ){
            midIteractor(node.getLeft() ,resultList);

        }

        resultList.add(node);

        if(node.getRight() != null){
            midIteractor(node.getRight() ,resultList);
        }
        return ;
    }

    /**
     * 往root所在树枝上插入node
     * @param root 检索起点
     * @param node 即插入的节点
     * @return 插入成功则返回插入节点的key,失败返回null
     */
    private Object addNode(Node root ,Node node){
        //起点不存在，那么返回null
        if(root == null ){
            return null ;
        }
        //插入节点为空，那无法插入
        if(node == null ){
            return null ;
        }
        Object targetKey = node.getKey();
        int tarint = hash(targetKey);
        Object rootKey = root.getKey();
        int rootint = hash(rootKey);
        //小于,左分支
        if(tarint < rootint){
            //存在左子节点时，递归检索进去
            if(root.getLeft() != null){
               return addNode(root.getLeft() , node);
            }else{
                root.setLeft(node);
                node.setParent(root);
                size ++ ;
                return node.getKey();
            }
        }

        //大于,右分支
        if(tarint > rootint){
            //存在右子节点时，递归检索进去
            if(root.getRight() != null){
                return addNode(root.getRight() , node);
            }else{
                root.setRight(node);
                node.setParent(root);
                size ++ ;
                return node.getKey();
            }
        }

        return null ;
    }

    /**
     * 按红黑树的方式添加节点
     * @param root
     * @param node
     * @return
     */
    private Object addRedBlankNode(Node root ,Node node){
        //起点不存在，那么返回null
        if(root == null ){
            return null ;
        }
        //插入节点为空，那无法插入
        if(node == null ){
            return null ;
        }

        Object targetKey = node.getKey();
        int tarint = hash(targetKey);
        Object rootKey = root.getKey();
        int rootint = hash(rootKey);
        //相等，刚覆盖更新
        if(tarint == rootint){
            root.setValue(node.getValue());
            return node.getValue();
        }
        //小于,左分支
        if(tarint < rootint){
            //存在左子节点时，递归检索进去
            if(root.getLeft() != null){
                return addRedBlankNode(root.getLeft() , node);
            }else{
                root.setLeft(node);
                node.setParent(root);

            }
        }

        //大于,右分支(由于只构建2-3树，所以添加右分支)
        if(tarint > rootint){
            //存在右子节点时，递归检索进去
            if(root.getRight() != null){
                return addRedBlankNode(root.getRight() , node);
            }else{
                root.setRight(node);
                node.setParent(root);
            }
        }

        //修复平衡
        fixBalance(node);
        size ++ ;
        return node.getKey();

    }
    /**
     * 处理Key 的统一方法
     * @param k
     * @return
     */
    private int hash(Object k){
        if(k==null){
           throw new RuntimeException("key can not be null") ;
        }
        return k.hashCode();
    }

    /**
     * 右旋，
     */
    private void turnRight(Node node){
        Node root = node.getParent();
       // Node p_root = root.getParent();

        node.setParent(root.getParent());
        root.setLeft(node.getRight());
        node.setRight(root);
        root.setParent(node);

        if(node.getParent()!= null){
            //左支
            if(Node.LEFT == checkLR(node,node.getParent())){
                node.getParent().setLeft(node);
            }else{
                node.getParent().setRight(node);
            }
        }else{//说明遇到根节点了
            this.root = node ;
        }

    }
    /**
     * 左旋，
     */
    private void turnLeft(Node node){
        Node root = node.getParent();
       // Node p_root = root.getParent();

        node.setParent(root.getParent());
        root.setRight(node.getLeft());
        node.setLeft(root);
        root.setParent(node);

        if(node.getParent()!= null){
            //左支
            if(Node.LEFT == checkLR(node,node.getParent())){
                node.getParent().setLeft(node);
            }else{
                node.getParent().setRight(node);
            }
        }else{//说明遇到根节点了
            this.root = node ;
        }

    }


    /**
     * 调整树结构及按需重新着色
     * @param node
     */
    private void fixBalance(Node node){
        //第一种情况，到顶了，node就是根了，那么要考虑把node的颜色变黑，
        if(node.getParent() == null){
            node.setColor(Node.BLANK);
        }
        //左支
       else if(Node.LEFT == checkLR(node,node.getParent())){
            //父节点为黑时，如果无左子节点或左子节点为黑，直接颜色置为红
            if(Node.BLANK == node.getParent().getColor() && (
                    (node.getLeft() !=null && node.getLeft().getColor()==Node.BLANK)
                            ||node.getLeft() == null)){
                node.setColor(Node.RED);
            }
            //父节点为黑时，但其左子节点为红，由于不能连续两红
            else if(Node.BLANK == node.getParent().getColor() &&
                    node.getLeft() !=null && node.getLeft().getColor()==Node.RED ){
                node.getLeft().setColor(Node.BLANK);
                turnRight(node);
                fixBalance(node);

            }
            //父节点为红时,其祖父节点是一定存在的
            //此时父节点那一层肯定是满的，只有进行变换了，看情况如何变换
            else if(Node.RED == node.getParent().getColor()){
                    Node root = node.getParent();
                    root.setColor(Node.BLANK);
                    turnRight(root);
                    fixBalance(root);
            }


        }
        //右支 (想办法变为左支)
        else if(Node.RIGHT == checkLR(node,node.getParent())){
            Node root = node.getParent();
            if(node.getLeft()==null ||node.getLeft().getColor() == Node.BLANK){
                if(root.getColor() == Node.RED){
                    turnLeft(node);
                    fixBalance(node);
                }else{
                    turnLeft(node);
                    fixBalance(root);
                }

            }
             else{
                node.getLeft().setColor(Node.BLANK);
                turnRight(node.getLeft());
                turnLeft(node.getLeft());
                fixBalance(node.getLeft());

            }
        }

    }

    /**
     * 删除节点
     * @param node
     */
    private void deleteNode(Node node){

        //当被删除节点为叶子节点时，直接删除
        if(node.getLeft()==null && node.getRight()==null){
            if(node.getParent() == null ){
                this.root =null ;
            }
           else if(checkLR(node,node.getParent())) {
               node.getParent().setRight(null);
           }
           else {
               node.getParent().setLeft(null);
           }
        }

        //当删除节点只有一个子节点时
        if(node.getLeft()==null && node.getRight()==null){


        }
    }
    /**
     * 判断两父子节点是左支关系 还是右支关系
     * @param root
     * @param p_root
     * @return
     */
    private boolean checkLR(Node root ,Node p_root){
        if(hash(root.getKey())<hash(p_root.getKey())){
        //if(p_root.getLeft() == root ){
            return Node.LEFT ;
        }
        else{
            return Node.RIGHT ;
        }

    }
}
