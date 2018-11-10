package util.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class RotateMap<K,V>{
	
	private LinkedList<Map<K,V>> list = new LinkedList<>() ; 
	
	public RotateMap(int num){
		if(num < 2){
			throw new RuntimeException("map num need greater than 2") ; 
		}
		for(int i = 0;i < num;i++){
			list.add(new HashMap<K,V>())  ;
		}
	}
	
	public void put(K k,V v){
		Iterator<Map<K,V>> it = list.iterator() ;
		Map<K,V> firstMap = it.next() ; 
		firstMap.put(k, v) ; 
		while(it.hasNext()){
			Map<K,V> map = it.next() ; 
			map.remove(k) ; 
		}
	}
	
	public V get(K k){
		for(Map<K,V> map:list){
			if(map.containsKey(k)){
				return map.get(k) ; 
			}
		}
		return null ; 
	}
	
	public void rotate(){
		list.removeLast(); 
		list.addFirst(new HashMap<K,V>());
	}
	
}

