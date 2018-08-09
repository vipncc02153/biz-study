package com.everhomes.learning.demos.treeMap.hmb;

public interface Map<K, V> {
	
	public void put(K key, V value);

	public V get(K key);

	public void remove(K key);
	
	int size();
	
	void print();
	
	void print(Integer num);
	
    interface Entry<K,V> {
    	K getKey();
    	V getValue();
    	void setValue(V value);
    }

	
}
