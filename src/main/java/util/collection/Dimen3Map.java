package util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Dimen3Map <K1,K2,K3,V> {
	
	ConcurrentHashMap<K1,ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>>> diamen3Map = new ConcurrentHashMap<K1, ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>>>() ; 
	
	public void put(K1 k1 , K2 k2 , K3 k3 , V v){
		ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>> map1 = diamen3Map.get(k1) ; 
		if(map1 == null){
			ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>> tmpMap = new ConcurrentHashMap<K2, ConcurrentHashMap<K3,V>>() ; 
			map1 = diamen3Map.putIfAbsent(k1, tmpMap) ;
			if(map1 == null){
				map1 = tmpMap ; 
			}
		}
		ConcurrentHashMap<K3, V> map2  = map1.get(k2) ;
		if(map2 == null){
			ConcurrentHashMap<K3, V> tmpMap = new ConcurrentHashMap<K3,V>() ;
			map2 = map1.putIfAbsent(k2, tmpMap) ; 
			if(map2 == null){
				map2 = tmpMap; 
			}
		}
		map2.put(k3,v) ; 
	}
	
	
	public V get(K1 k1 , K2 k2 , K3 k3){
		ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>> map1 = diamen3Map.get(k1) ;
		if(map1 == null){
			return null; 
		}
		ConcurrentHashMap<K3, V> map2  = map1.get(k2) ;
		if(map2 == null){
			return null ; 
		}
		return map2.get(k3) ; 
	}
	
	
	
	public Collection<V> values(K1 k1 , K2 k2){
		ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>> map1 = diamen3Map.get(k1) ;
		if(map1 == null){
			return null; 
		}
		ConcurrentHashMap<K3, V> map2  = map1.get(k2) ;
		if(map2 == null){
			return null ; 
		}
		return map2.values() ; 
	}
	
	
	public Collection<V> values(K1 k1){
		ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>> map1 = diamen3Map.get(k1) ;
		if(map1 == null){
			return null; 
		}
		List<V> list = new ArrayList<V>() ; 
		for(ConcurrentHashMap<K3, V> map:map1.values()){
			list.addAll(map.values()) ; 
		}
		return list ; 
	}
	
	public Collection<V> values(){
		List<V> list = new ArrayList<V>() ; 
		for(ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>> map :diamen3Map.values()){
			for(ConcurrentHashMap<K3, V> map2:map.values()){
				list.addAll(map2.values()) ; 
			}
		}
		return list ; 
	}
	
	public Set<K1> keys(){
		return diamen3Map.keySet() ; 
	}
	
	public Set<Pair<K1,K2>> key2s(){
		Set<Pair<K1,K2>> set = new HashSet<Pair<K1,K2>>() ; 
		for(Map.Entry<K1,ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>>> entry :diamen3Map.entrySet()){
			for(K2 k2:entry.getValue().keySet()){
				Pair<K1,K2> pair = new Pair<K1,K2>(entry.getKey(),k2) ; 
				set.add(pair) ; 
			}
		}
		return set ; 
	}
	
	
	public Set<Tri<K1,K2,K3>> key3s(){
		Set<Tri<K1,K2,K3>> set = new HashSet<Tri<K1,K2,K3>>() ; 
		for(Map.Entry<K1,ConcurrentHashMap<K2,ConcurrentHashMap<K3,V>>> entry :diamen3Map.entrySet()){
			for(Map.Entry<K2,ConcurrentHashMap<K3, V>> entry2:entry.getValue().entrySet()){
				for(K3 k3:entry2.getValue().keySet()){
					Tri<K1,K2,K3> tri = new Tri<K1,K2,K3>(entry.getKey(),entry2.getKey(),k3) ; 
					set.add(tri) ; 
				}
			}
		}
		return set ; 
	}
	
	
	
}
