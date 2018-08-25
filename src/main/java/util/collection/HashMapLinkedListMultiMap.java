package util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HashMapLinkedListMultiMap<K,V> extends AbstractMultiMap<K, V>{
	
	private Map<K,Collection<V>> map = new HashMap<>() ; 
	
	@Override
	protected Map<K, Collection<V>> getMap() {
		return map;
	}

	@Override
	protected Collection<V> createCollection() {
		return new LinkedList<>();
	}

}
