package com.everhomes.learning.demos.treeMap.hmb;
import org.springframework.stereotype.Component;

@Component
public class TreeMapServiceImpl implements TreeMapService {

	@Override
	public void put(String key, String value) {
		Map<String, String> map = new TreeMap<>();		
		map.put(key, value);
	}

}
