package util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Dimen2Map <K1,K2,V>{
	
	ConcurrentHashMap<K1,Map<K2,V>> dimen2map = new ConcurrentHashMap<K1,Map<K2,V>>() ;
	
	public V get(K1 k1,K2 k2){
		Map<K2,V> map = dimen2map.get(k1) ; 
		if(map == null){
			return null ; 
		}
		return map.get(k2) ; 
	}
	
	public void put(K1 k1 , K2 k2 , V v){
		Map<K2,V> map = dimen2map.get(k1) ;  
		if(map == null){
			map = new ConcurrentHashMap<K2, V>() ; 
			Map<K2,V> tmpMap = dimen2map.putIfAbsent(k1, map) ;
			if(tmpMap != null){
				map = tmpMap ; 
			}
		}
		map.put(k2, v) ; 
	}
	
	public Collection<V> getValues(K1 k1){
		Map<K2,V> map = dimen2map.get(k1) ; 
		if(map == null){
			return null ; 
		}
		return map.values() ; 
	}
	
	public Collection<V> getValues(){
		List<V> list = new ArrayList<V>() ; 
		for(Map<K2,V> map:dimen2map.values()){
			list.addAll(map.values()) ; 
		}
		return list ; 
	}
	
	public Set<K1> getK1(){
		return dimen2map.keySet(); 
	}
	
}






























