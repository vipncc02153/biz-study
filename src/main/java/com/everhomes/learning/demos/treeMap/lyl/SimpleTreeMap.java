package com.everhomes.learning.demos.treeMap.lyl;

public class SimpleTreeMap<K extends Comparable, V> {

    static class Node {
        Object key;
        Object value;
        Node parent;
        Node left;
        Node right;

        public Node(Object key,Object value, Node parent, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return "[key=" + key + ", value="+ value +"]";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() == Node.class) {
                Node target = (Node) obj;
                return key.equals(target.key)&&value.equals(target.value) && left == target.left && right == target.right && parent == target.parent;
            }
            return false;
        }

    }

    private Node root;

    // 两个构造器用于创建排序二叉树
    public SimpleTreeMap() {
        root = null;
    }

    public SimpleTreeMap(K key, V value) {
        root = new Node(key,value, null, null, null);
    }

    // 添加节点
    public void put(K key, V value) {
        // 如果根节点为null
        if (root == null) {
            root = new Node(key,value, null, null, null);
        } else {
            Node current = root;
            Node parent = null;
            int cmp = 0;
            // 搜索合适的叶子节点，以该叶子节点为父节点添加新节点
            while (current != null) {
                parent = current;
                cmp = key.compareTo(current.key);
                // 如果新节点的值大于当前节点的值
                if (cmp > 0) {
                    // 以右子节点作为当前节点
                    current = current.right;
                } else {
                    // 如果新节点的值小于当前节点的值
                    // 以左节点作为当前节点
                    current = current.left;
                }
            }
            // 创建新节点
            Node newNode = new Node(key,value, parent, null, null);
            // 如果新节点的值大于父节点的值
            if (cmp > 0) {
                // 新节点作为父节点的右子节点
                parent.right = newNode;
            } else {
                // 如果新节点的值小于父节点的值
                // 新节点作为父节点的左子节点
                parent.left = newNode;
            }
        }
    }

    // 删除节点
    public void remove(K key) {
        Node target = getNode(key);
        if (target == null) {
            return;
        }
        if (target.left == null && target.right == null) {
            // 被删除节点是根节点
            if (target == root) {
                root = null;
            } else {
                // 被删除节点是父节点的左子节点
                if (target == target.parent.left) {
                    // 将target的父节点的left设为null
                    target.parent.left = null;
                } else {
                    // 将target的父节点的right设为null
                    target.parent.right = null;
                }
                target.parent = null;
            }
        } else if (target.left == null && target.right != null) {
            if (target == root) {
                root = target.right;
            } else {
                // 被删除节点是父节点的左子节点
                if (target == target.parent.left) {
                    // 让target的父节点的left指向target的右子树
                    target.parent.left = target.right;
                } else {
                    // 让target的父节点的right指向target的右子树
                    target.parent.right = target.right;
                }
                // 让target的右子树的parent指向target的parent
                target.right.parent = target.parent;
            }
        } else if (target.left != null && target.right == null) {
            // 左子树不为空，右子树为空
            // 被删除节点是根节点
            if (target == root) {
                root = target.left;
            } else {
                // 被删除节点是父节点的左子节点
                if (target == target.parent.left) {
                    // 让target的父节点的left指向target的左子树
                    target.parent.left = target.left;
                } else {
                    // 让target的父节点的right指向target的左子树
                    target.parent.right = target.left;
                }
                // 让target的左子树的parent指向target的parent
                target.left.parent = target.parent;
            }
        } else {
            //从target的左子树中找到最大的节点进行替换
            Node leftMaxNode = target.left;
            while (leftMaxNode.right != null) {
                leftMaxNode = leftMaxNode.right;
            }
            // 替换节点
            leftMaxNode.parent.right = null;
            leftMaxNode.parent = target.parent;
            if (target == target.parent.left) {
                target.parent.left = leftMaxNode;
            } else {
                target.parent.right = leftMaxNode;
            }
            leftMaxNode.left = target.left;
            leftMaxNode.right = target.right;
            target.parent = target.left = target.right = null;
        }
    }

    // 根据给定的值搜索节点
    public Node getNode(K key) {
        // 从根节点开始搜索
        Node p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            // 如果搜索的值小于当前p节点的值
            if (cmp < 0) {
                // 向左子树搜索
                p = p.left;
            } else if (cmp > 0) {
                // 如果搜索的值大于当前p节点的值
                // 向右子树搜索
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    public Object get(K key) {
        Node current = getNode(key);
        if (current != null) {
            return current.value;
        }
        return null;
    }

}