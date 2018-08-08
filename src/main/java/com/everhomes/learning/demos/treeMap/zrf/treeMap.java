


public class treeMap {
    private Long      key;
    private Long      value;
    private treeMap   left;
    private treeMap   right;
    private int       N;
    private treeMap root;  //二叉查找树的根节点

    public treeMap(Long key, Long value, int N) {
        this.key = key;
        this.value = value;
        this.N = N;
    }

    /**
     * 获取整个二叉查找树的大小
     * @return
     */
    public int size(){
        return size(root);
    }
    /**
     * 获取某一个结点为根结点的二叉查找树的大小
     * @param x
     * @return
     */
    private int size(treeMap x){
        if(x == null){
            return 0;
        } else {
            return x.N;
        }
    }

    /**
     * 查找：通过key获取value
     * @param key
     * @return
     */
    public Long get(Long key){
        return get(root, key);
    }
    /**
     * 在以 x 为根节点的子树中查找并返回Key所对应的值,如果找不到就返回null
     * 递归实现
     * @param x
     * @param key
     * @return
     */
    private Long get(treeMap x, Long key){
        if(x == null){
            return null;
        }
        //键值的比较
        int cmp = key.compareTo(x.key);
        if(cmp<0){
            return get(x.left, key);
        }else if(cmp>0){
            return get(x.right, key);
        } else{
            return x.value;
        }
    }

    /**
     * 最小键
     */
    public Long min(){
        return min(root).key;
    }
    /**
     * 返回结点x为root的二叉排序树中最小key值的treeMap
     * @param x
     * @return 返回树的最小key的结点
     */
    private treeMap min(treeMap x){
        if(x.left == null){
            return x;
        }else{
            return min(x.left);
        }
    }


    /**
     * 插入：设置键值对
     * @param key
     * @param value
     */
    public void put(Long key, Long value){
        root = put(root, key, value);
    }
    /**
     * key如果存在以 x 为根节点的子树中,则更新它的值;
     * 否则将key与value键值对插入并创建一个新的结点.
     * @param x
     * @param key
     * @param value
     * @return
     */
    private treeMap put(treeMap x, Long key, Long value){
        if( x==null ){
            x = new treeMap(key, value, 1);
            return x;
        }
        int cmp = key.compareTo(x.key);
        if(cmp<0){
            x.left = put(x.left, key, value);
        }else if(cmp>0){
            x.right = put(x.right, key, value);
        } else{
            x.value=value;//更新value的值
        }
        //设置根节点的N属性
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }


    /**
     * 删除键值最小结点
     * @param x
     * @return 返回新的二叉查找树的根节点
     */
    private treeMap removeMin(treeMap x){
        if(x.left == null){
            return x.right;//删除根节点,这时返回的是新的二叉查找树的根节点
        }
        x.left = removeMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }



    /**
     * 删除键key结点.
     * @param key
     */
    public void remove(Long key){
        root = remove(root, key);
    }
    /**
     * 删除以x为根结点的二叉查找树的key键的结点
     * @param x
     * @param key
     * @return 新的二叉查找树的根节点
     */
    private treeMap remove(treeMap x, Long key){
        if( x == null ){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if(cmp > 0){
            x.right = remove(x.right, key);
        } else {//这时删除根节点x
            if(x.left == null){
                return x.right;
            }
            if(x.right == null){
                return x.left;
            }
            //根节点有左右子树
            treeMap t = x;
            //1. 先求出x的右子树中最小键值的结点并让x指向它.
            x = min(t.right);
            //2. 将t的右子树删除最小的结点之后的根节点返回
            x.right = removeMin(t.right);
            //3. 将t的左子树给x的左子树
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
}
