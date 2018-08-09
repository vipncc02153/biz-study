package com.everhomes.learning.demos.treeMap.hmb;



public class TreeMap<K, V> implements Map<K, V> {

	private int size = 0;
	
	public static void main(String[] args) {
		Map<Long, Byte> map = new  TreeMap<>();
		
		int len = 1000000;
		long begin = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			map.put(Math.round(Math.random()*10000000), (byte)1);
		}
		long end = System.currentTimeMillis();
		System.out.println("时间："+(end - begin)+"ms");
		
		map.print(1000);
	}

	private Entry<K, V> root;

	public TreeMap() {
	}

	@Override
//	@TimeCountAble(key = "put")
	public void put(K key, V value) {
		if (null == root) {
			root = new Entry<>(key, value, null);
			size = 1;
			return;
		}

		put(root, key, value);
	}
	

	private Entry<K, V> put(Entry<K, V> toCompare, K key, V value) {

		int ret = compare(key, toCompare.getKey());
		if (0 == ret) {
			toCompare.setValue(value);
			return toCompare;
		}

		if (ret < 0) {

			if (null == toCompare.left) {
				size++;
				return toCompare.left = new Entry<>(key, value, toCompare);
			}

			return put(toCompare.left, key, value);
		}

		if (null == toCompare.right) {
			size++;
			return toCompare.right = new Entry<>(key, value, toCompare);
		}

		return put(toCompare.right, key, value);
	}

	private int compare(K key, K key2) {
		Comparable<? super K> k = (Comparable<? super K>) key;
		return k.compareTo(key2);
	}

	@Override
//	@TimeCountAble(key = "get")
	public V get(K key) {
		return get(root, key);
	}

	private V get(Entry<K, V> toCompare, K key) {
		Entry<K, V> ret = search(toCompare, key);
		return null == ret ? null : ret.getValue();
	}

	private Entry<K, V> search(Entry<K, V> toCompare, K key) {
		if (null == toCompare) {
			return null;
		}

		int ret = compare(key, toCompare.getKey());
		if (0 == ret) {
			return toCompare;
		}

		if (ret < 0) {
			return search(toCompare.left, key);
		}

		return search(toCompare.right, key);
	}

	@Override
//	@TimeCountAble(key = "remove")
	public void remove(K key) {
		Entry<K, V> ret = search(root, key);
		if (null == ret) {
			return;
		}
		
		System.out.println("remove "+ret.getKey()+":"+ret.getValue());
		size--;
		
		if (root == ret) {
			removeRoot();
			return;
		}

		if (ret.parent.left == ret) {
			removeLeft(ret);
			return;
		}

		removeRight(ret);
		return;
	}

	private void removeRoot() {
		
		Entry<K, V> leftBoy = root.left;
		Entry<K, V> rightBoy = root.right;
		
		if (null == leftBoy && null == rightBoy) {
			root = null;
			return;
		}
		
		if (null == leftBoy) {
			emptyNode(root);
			root = rightBoy;
			rightBoy.parent = null;
			return;
		}
		
		if (null == rightBoy) {
			emptyNode(root);
			root = leftBoy;
			leftBoy.parent = null;
			return;
		}
		
		//随便旋转一个方向
		Entry<K, V> toRemove = rotateRightRoot();
		
		//删除
		removeRight(toRemove);
	}

	private Entry<K, V> rotateRightRoot() {
		Entry<K, V> leftBoy = root.left;
		
		root.parent = leftBoy;
		root.left = leftBoy.right;
		if (null != leftBoy.right) {
			leftBoy.right.parent = root;
		}
		
		leftBoy.parent = null;
		leftBoy.right = root;
		
		root = leftBoy;
		
		return root.right;
	}

	private void removeRight(Entry<K, V> toRemove) {

		Entry<K, V> leftBoy = toRemove.left;

		if (null != leftBoy) {
			if (null == toRemove.right) {
				toRemove.right = leftBoy;
			}  else {
				// 将左儿子放到右儿子的最左下角
				Entry<K, V> tmpPos = put(toRemove.right, leftBoy.key, leftBoy.value);
				leftBoy.parent = tmpPos.parent;
				if (tmpPos.parent.left == tmpPos) {
					tmpPos.parent.left = leftBoy;
				} else {
					tmpPos.parent.right = leftBoy;
				}

				emptyNode(tmpPos); // 清空该节点
				size --;
			}
		}

		// 进行节点删除
		toRemove.parent.right = toRemove.right; // 设置父节点
		if (null != toRemove.right) {
			toRemove.right.parent = toRemove.parent; // 设置子节点
		}

		emptyNode(toRemove); // 清空该节点
	}
	
	private void removeLeft(Entry<K, V> toRemove) {

		Entry<K, V> rightBoy = toRemove.right;

		if (null != rightBoy) {
			if (null == toRemove.left) {
				toRemove.left = rightBoy;
			} else {
				// 将右儿子放到左儿子的最右下角
				Entry<K, V> tmpPos = put(toRemove.left, rightBoy.key, rightBoy.value);
				rightBoy.parent = tmpPos.parent;
				if (tmpPos.parent.right == tmpPos) {
					tmpPos.parent.right = rightBoy;
				} else {
					tmpPos.parent.left = rightBoy;
				}

				emptyNode(tmpPos); // 清空该节点
				size --;
			}
		}

		// 进行节点删除
		toRemove.parent.left = toRemove.left; // 设置父节点
		if (null != toRemove.left) {
			toRemove.left.parent = toRemove.parent; // 设置子节点
		}

		emptyNode(toRemove); // 清空该节点
	}

	private void emptyNode(Entry<K, V> toEmpty) {
		if (null == toEmpty) {
			return;
		}

		toEmpty.parent = null;
		toEmpty.left = null;
		toEmpty.right = null;
		toEmpty.key = null;
		toEmpty.value = null;
	}

	static final class Entry<K, V> implements Map.Entry<K, V> {

		private K key;
		private V value;

		private Entry<K, V> left;
		private Entry<K, V> right;
		private Entry<K, V> parent;

		Entry(K key, V value, Entry<K, V> parent) {
			
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder total = new StringBuilder();
		print(root, total, null);
		return total.toString();
	}

	private void print(Entry<K, V> current, StringBuilder total, Integer maxNum) {
		if (null == current || total.length() >= maxNum) {
			return;
		}

		print(current.left, total, maxNum);
		total.append(current.getKey() + ":" + current.getValue()+"\r\n");
		print(current.right, total, maxNum);
	}

	@Override
	public void print() {
		System.out.println("size:"+this.size);
		print(null);
	}
	
	@Override
	public void print(Integer num) {
		StringBuilder total = new StringBuilder();
		print(root, total, num);
		System.out.println(total.toString());
	}
}
