package util.collection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapLinkedListMultiMap<K,V> extends AbstractMultiMap<K, V>{
	
	private  Map<K,Collection<V>> map = new TreeMap<>() ;

	@Override
	protected Collection<V> createCollection() {
		return new LinkedList<V>();
	}

	@Override
	protected Map<K, Collection<V>> getMap() {
		return map ; 
	}

}
