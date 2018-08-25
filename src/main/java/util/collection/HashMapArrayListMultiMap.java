package util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HashMapArrayListMultiMap<K,V> extends AbstractMultiMap<K, V>{
	
	private Map<K,Collection<V>> map = new HashMap<>(); 
	
	@Override
	protected Map<K, Collection<V>> getMap() {
		return map;
	}

	@Override
	protected Collection<V> createCollection() {
		return new ArrayList<V>();
	}

}
