package util.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TimeOutMap<K,V> implements Map<K,V>{
	
	public Map<Node<K>, V> map = new ConcurrentHashMap<>();  
		
	private static class Node<K> {
		
		private Object key ;
		
		public Node(Object key){
			this.key = key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}
		
		
	}



	@Override
	public int size() {
		return map.size();
	}



	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}



	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(new Node<K>(key)) ; 
	}



	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) { 
		return map.get(new Node<>(key)) ; 
	}

	@Override
	public V put(K key, V value) {
		return map.put(new Node<K>(key),value) ; 
	}


	@Override
	public V remove(Object key) {
		return map.remove(new Node<K>(key)) ; 
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for(java.util.Map.Entry<? extends K, ? extends V> entry:m.entrySet()){
			put(entry.getKey(),entry.getValue()) ; 
		}
	}

	@Override
	public void clear() {
		map.clear();  
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException() ;
	}

	@Override
	public Collection<V> values() {
		return map.values() ;  
	}

	
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException() ; 
	}
}
