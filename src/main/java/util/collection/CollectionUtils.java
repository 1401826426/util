package util.collection;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class CollectionUtils {
	
	public static boolean isEmpty(Collection<?> collection){
		return collection == null || collection.isEmpty(); 
	}
	
	public static boolean isEmpty(Map<?,?> map){
		return map == null || map.isEmpty() ; 
	}
	
	public <T> T getFirstObject(List<T> list){
		if(list == null || list.size() == 0){
			return null ; 
		}
		return list.get(0) ; 
	}
	
	public static <T> List<T> arrayToList(T[] array){
		return Arrays.asList(array);
	}
	
	public static <E,T extends E> void addArrayIntoCollection(T[] array,Collection<E> collection){
		if (collection == null) {
			throw new IllegalArgumentException("Collection must not be null");
		}
		if(array != null){
			for(T t:array){
				collection.add(t) ; 
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <K,V> void addPropertiesToMap(Properties props,Map<K,V> map){
		if (map == null) {
			throw new IllegalArgumentException("Map must not be null");
		}
		if(props != null){
			Enumeration<?> en = props.propertyNames() ;
			while(en.hasMoreElements()){
				Object key =  en.nextElement() ; 
				Object value = props.get(key) ;
				map.put((K)key, (V)value)  ; 
			}
		}
	}
	/**
	 * ����source���Ƿ��������target������Ԫ��
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean containsAll(Collection<?> source , Collection<?> target){
		if(target == null){
			return true ; 
		}
		if(source == null){
			return false  ; 
		}
		for(Object object:target){
			if(!source.contains(object)){
				return false; 
			}
		}
		return true ; 
	}
	
	public static Class<?> findCommonElementType(Collection<?> collection){
		if(isEmpty(collection)){
			return null; 
		}
		Class<?> clazz = null ; 
		for(Object val:collection){
			if(val != null){
				if(clazz == null){
					clazz = val.getClass() ; 
				}else if(clazz != val.getClass()){
					return null ; 
				}
			}
		}
		return null ; 
	}
	
	public static <T> Enumeration<T> collectionToEnumeration(Collection<T> collection){
		final Iterator<T> it = collection.iterator() ; 
		Enumeration<T> en = new Enumeration<T>() {

			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}

			@Override
			public T nextElement() {
				return it.next() ; 
			}
		};
		return en ; 
	}
	
	public static <T> Enumeration<T> arrayToEnumeration(T[] strs){
		
		List<T> list = Arrays.asList(strs) ;  
		return collectionToEnumeration(list) ; 
	}
	
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Pattern pattern = Pattern.compile("(https?://(\\w+)(\\.\\w+)*)/.*") ;
//		patternPrintObjectTree(pattern) ; 
		Matcher matcher = pattern.matcher("https://test.abs.abs/dddd") ; 
//		System.err.println(matcher.find(1)) ;
		if(matcher.find()){
			for(int i = 0;i <= matcher.groupCount();i++){
				System.err.println(matcher.group(i) +"   "+ "===");
			}
		}
//		Son<Integer> son = new Son<I nteger>() ; 
//		son.getClass().getGenericSuperclass() ; 		
//		Map<String,String> map = new HashMap<String,String>() ;
//		List<String> list = new ArrayList<String>() ; 
//		System.err.println(map.getClass());
//		System.err.println(map.getClass().getClass());
//		System.err.println(map.getClass().getGenericSuperclass());
//		Type type = son.getClass().getGenericSuperclass();
//		System.err.println(type);
//		System.err.println(type.getClass().getName());
//		if(type instanceof ParameterizedType){
//			ParameterizedType parameterizedTypes = (ParameterizedType)type ; 
//			System.err.println((((ParameterizedType) type).getRawType().getTypeName()));
//			System.out.println(((Class)parameterizedTypes.getActualTypeArguments()[0]).getName());
////			Type[] types = parameterizedTypes.getActualTypeArguments() ;
////			for(Type ttt:types){				
////				
////				
////			}
//			ParameterizedType parameterizedType = (ParameterizedType)type ; 
//			Type[] detailTypes = parameterizedType.getActualTypeArguments() ;
//			System.err.println(detailTypes.length);
//			for(Type detailType:detailTypes){
//				int t = 0 ; 
//				t++ ; 
//				System.err.println(detailType.getTypeName());
//				System.err.println(detailType.getClass().getName());
//				if(detailType instanceof ParameterizedType){
//					ParameterizedType parameterizedType2 = (ParameterizedType)detailType ; 
//					Class<?> tmpClass = (Class<?>)parameterizedType2.getActualTypeArguments()[0];
//					System.err.println(tmpClass.getName());
//				}
//			}
		}

	public static String bytesToHexStr(byte[] bytes) {
		StringBuilder sb = new StringBuilder("") ; 
		for(byte b:bytes){
			sb.append(String.format("%x", b)) ; 
		}
		return sb.toString() ; 
	}
		
//		map.getClass()
//		System.err.println(clazz instanceof ParameterizedType);
//		clazz 
//		TypeVariable<?>[] types = clazz.getTypeParameters() ;
//		clazz.get
//		clazz.get
//		clazz.getT
//		for(TypeVariable<?> type:types){ 
//			System.err.println(type.getName() + " "
//		+ type.getTypeName() 
//		+" " + type.getGenericDeclaration()
//		+" ");
//		}
//	}
}











