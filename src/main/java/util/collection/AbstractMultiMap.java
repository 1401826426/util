package util.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMultiMap<K,V> implements MultiMap<K,V>{

	
	protected abstract Map<K,Collection<V>> getMap() ; 
	
	@Override
	public int size() {
		return getMap().size();
	}

	@Override
	public boolean isEmpty() {
		return getMap().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return getMap().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		Collection<Collection<V>> values = values() ; 
		if(values == null){
			return false ; 
		}
		for(Collection<V> collection:values){
			if(collection.contains(value)){
				return true ; 
			}
		}
		return false;
	}

	@Override
	public Collection<V> get(Object key) {
		return getMap().get(key);
	}

	@Override
	public Collection<V> put(K key, Collection<V> value) {
		return getMap().put(key, value);
	}

	@Override
	public Collection<V> remove(Object key) {
		return getMap().remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends Collection<V>> m) {
		getMap().putAll(m);
	}

	@Override
	public void clear() {
		getMap().clear();
	}

	@Override
	public Set<K> keySet() {
		return getMap().keySet();
	}

	@Override
	public Collection<Collection<V>> values() {
		return getMap().values();
	}

	@Override
	public Set<java.util.Map.Entry<K, Collection<V>>> entrySet() {
		return getMap().entrySet();
	}

	@Override
	public void putOne(K k, V v) {
		Collection<V> collection = getMap().get(k) ; 
		if(collection == null){
			collection = createCollection() ;  
		}
		collection.add(v) ; 
	}

	protected abstract  Collection<V> createCollection()  ; 

}
