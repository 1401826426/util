package util.collection;

import java.util.Collection;
import java.util.Map;

public interface MultiMap<K,V> extends Map<K,Collection<V>>{
	
	public void putOne(K k , V v) ; 
	
}
