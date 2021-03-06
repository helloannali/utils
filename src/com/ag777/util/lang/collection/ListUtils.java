package com.ag777.util.lang.collection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 有关 <code>List</code> 列表工具类。
 * 
 * @author ag777
 * @version create on 2017年09月22日,last modify at 2019年02月12日
 */
public class ListUtils {

	private ListUtils(){}
	
	private static <T>List<T> newList() {
		return newArrayList();
	}
	
	public static <T>ArrayList<T> newArrayList() {
		return CollectionAndMapUtils.newArrayList();
	}
	
	public static <T>LinkedList<T> newLinkedList() {
		return CollectionAndMapUtils.newLinkedList();
	}
	
	public static <T>Vector<T> newVector() {
		return CollectionAndMapUtils.newVector();
	}
	
	/**
	 * @see com.ag777.util.lang.collection.CollectionAndMapUtils#newCopyOnWriteArrayList()
	 * @return
	 */
	public static <T>CopyOnWriteArrayList<T> newCopyOnWriteArrayList() {
		return CollectionAndMapUtils.newCopyOnWriteArrayList();
	}
	
	public static Object newArray(Class<?> clazz, int length) {
		return CollectionAndMapUtils.newArray(clazz, length);
	}
	
	/**
	 * 复制数组
	 * <p>
	 * 详见Arrays.copyOf(T[] original, int newLength)方法
	 * </p>
	 * 
	 * @param original
	 * @param newLength
	 * @return
	 */
	public static <T>T[] copyArray(T[] original, int newLength) {
		return Arrays.copyOf(original, newLength);
	}
	
	public static <E>boolean isEmpty(Collection<E> collection) {
		return CollectionAndMapUtils.isEmpty(collection);
	}
	
	public static <T>boolean isEmpty(T[] array) {
		return CollectionAndMapUtils.isEmpty(array);
	}
	
	/**
	 * 判断对象是否是数组
	 * <p>
	 * 如果对象为null,则返回false
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isArray(Object obj) {
		if(obj==null) {
			return false;
		}
		return obj.getClass().isArray();
	}
	
	 /**
     * 拆分字符串组成列表
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * 		List<String> list = ListUtils.of("1,2,3", ",");
     *		结果为包含"1","2"和"3"的列表
     *		值得注意的是由于调用的是String.split(String regex)方法所以参数separator为正则表达式,注意字符串转义
     * </pre>
     * </p>
     * </p>
     */
	public static List<String> ofList(String src, String separator) {
		List<String> result = newList();
		if(src == null || src.isEmpty()) {
			return result;
		}else {
			String[] group = src.split(separator);
			for (String item : group) {
				result.add(item);
			}
		}
		return result;
	}
	
	/**
	 * 拆分字符串组成数组
	 * @param src
	 * @param separator
	 * @return
	 */
	public static String[] ofArray(String src, String separator) {
		if(src == null || src.isEmpty()) {
			return new String[]{};
		} else {
			return src.split(separator);
		}
	}
	
	 /**
     * 数组转列表
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * 		List<Integer> list = ListUtils.toFinalList(new Integer[]{1,2,3});
     *		之后可以随意操作该列表
     * </pre>
     * </p>
     * </p>
     */
	public static <T>List<T> ofList(T[] items) {
		List<T> result = newList();
		if(items != null && items.length > 0) {
			for (T item : items) {
				result.add(item);
			}
		}
		return result;
	}
	
	 /**
     * 数组转定长列表(后续不可改变列表长度，否则报错)
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * 		List<Integer> list = ListUtils.toFinalList(new Integer[]{1,2,3});
     *		这时如果调用list.add(3)或者list.remove(2)就会报错
     * </pre>
     * </p>
     * </p>
     */
	public static <T>List<T> ofFinalList(T[] items) {
		if(items == null) {
			return newList();
		}
		return Arrays.asList(items);
	}
	
	/**
	 * 构建list
	 * <p>
	 * 	含任意多项
	 * </p>
	 * 
	 */
	@SafeVarargs
	public static <T>List<T> of(T... items) {
		List<T> list = newList();
		if(items != null && items.length > 0) {
			for (T item : items) {
				list.add(item);
			}
		}
		return list;
	}
	
	 /**
     * 数组转列表
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * 		现有key-value 为[{a:1,b:2},{a:2},{a:3}]的列表list
     *		ListUtils.of(list, "a");
     *		结果将是值[1,2,3] 的列表
     * </pre>
     * </p>
     * </p>
     */
	public static <K,V>List<Object> ofList(List<Map<K, V>> list, K key) {
		List<Object> resultList = newList();
		for (Map<K, V> item : list) {
			try {
				Object obj = MapUtils.get(item, key);
				if(obj != null) {
					resultList.add(obj);
				}
			}catch(Exception ex) {
				//如果类型不匹配则不加入列表
			}
			
		}
		return resultList;
	}
	
	/**
     *  将list<Map>中每个map的第一个值(非空)整合成一个列表,适用于单列list
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * 		现有key-value 为[{a:1},{a:2},{a:3}]的列表list
     *		ListUtils.of(list);
     *		结果将是值[1,2,3] 的列表
     * </pre>
     * </p>
     * </p>
     */
	public static <K,V>List<Object> ofList(List<Map<K,V>> list) {
		List<Object> resultList = newList();
		if(list == null) {
			return resultList;
		}
		for (Map<K,V> item : list) {
			Iterator<K> itor = item.keySet().iterator();
			if(itor.hasNext()) {
				K key = itor.next();
				try {
					Object obj = item.get(key);
					resultList.add(obj);
				}catch(Exception ex) {
					//如果类型不匹配则不加入列表
				}
			}
		}
		return resultList;
	}
	
	 /**
     * 根据分隔符拆分列表获取字符串
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * 		ListUtils.of(new Integer[]{1,2,3});
     *		ListUtils.toString(list,",");
     *		得到的结果是1,2,3
     * </pre>
     * </p>
     * </p>
     */
	public static <T>String toString(List<T> list, String separator) {
		if(CollectionAndMapUtils.isEmpty(list)) {	//列表为空则返回空字符串
			return "";
		}
		StringBuilder sb = null;
		for (T item : list) {
			if(sb == null) {
				sb = new StringBuilder();
			} else if(separator != null) {
				sb.append(separator);
			}
			if(item != null) {
				sb.append(item.toString());
			} else {
				sb.append("null");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 根据分隔符拆分数组获取字符串
	 * @param array
	 * @param separator
	 * @return
	 */
	public static <T>String toString(T[] array, String separator) {
		if(CollectionAndMapUtils.isEmpty(array)) {	//列表为空则返回空字符串
			return "";
		}
		StringBuilder sb = null;
		for (T item : array) {
			if(sb == null) {
				sb = new StringBuilder();
			} else if(separator != null) {
				sb.append(separator);
			}
			if(item != null) {
				sb.append(item.toString());
			} else {
				sb.append("null");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 统计list中每一项及其出现次数
	 * <p>
	 * toCountMap(of("a","b","a"))=>{"a":2,"b":1}
	 * </p>
	 * 
	 * @param list
	 * @return
	 */
	public <T>Map<T, Long> toCountMap(List<T> list) {
		return list.stream().collect(Collectors.groupingBy(p -> p,Collectors.counting()));
	}
	
	/**
	 * 去重
	 * <p>
	 * 解决结果不符合预期的bug,详见{@link #remove(List, Object)}
	 * </p>
	 */
    public static <T>List<T> distinct(List<T> list) {
    	if(list == null) {
			return null;
		}
        Set<T> set = new HashSet<T>();
        for (int i = list.size() - 1; i >= 0; i--) {	//倒序遍历，为了能删除数据
        	T item = list.get(i);
        	boolean a = set.add(item);
        	if (!a) {	//如果set中不能加入数据，说明重复了，需要移除
            	remove(list, item);
            }
        }
        return list;
    }
	
	/**
     * 删除值为null的项
     * <p>
     * 	用Iterator遍历
     * </p>
     */
    public static <T>List<T> removeNull(List<T> list) {
    	if(list == null) {
    		return null;
    	}
    	list.removeIf(item->item==null);
        return list;
    }
	
    /**
     * 删除列表中某项元素，避免list<Integer> 的下标陷阱
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * List<Integer> list = newList();
		list.add(1);
		list.add(2);
		list.add(3);
		这时我们需要删除值为2的元素,于是我们调用
		list.remove(2);
		结果是1,2而不是预期的1,3
		通常情况下,我们需要用list.remove((Object)2);来得到预期的结果
     * </pre>
     * </p>
     * </p>
     */
    public static <T>List<T> remove(List<T> list, Object item) {
    	if(list == null) {
    		return null;
    	}
    	list.remove(item);
    	return list;
    }
    
    
    /**
     * 列表分段(将一个列表，每limit长度分一个列表并将这些新列表整合到一个列表里),多用于数据库批量插入拆分
     * <p>
     * 	该方法原用于解决一次性批量插入数据到数据库时数据量太大导致插入失败的异常，将列表数据分次插入
     * 例如：
     * </p>
     * 
     * <pre>
     * 		现有值为[1,2,3]的列表list,执行
     * 		ListUtils.splitList(list, 2);
     * 		将会得到值为[[1,2],[3]]的嵌套列表
     * 		值得注意的是如果传入的limit不为正数，则会抛出RuntimeException
     * </pre>
     * 
     */
	public static <T>List<List<T>> splitList(List<T> list, int limit) {
		if(list == null) {
			return null;
		}
		if(limit <= 0) {
			throw new RuntimeException("参数limit必须大于0");
		} 
		List<List<T>> result = newList();
		int size = list.size()/limit+1;
		for (int i = 0; i < size; i++) {
			int min = limit*i;
			int max = limit*(i+1);
			max = max>list.size()?list.size():max;
			if(max > min) {
				List<T> item = list.subList(min, max);
				result.add(item);
			}
			
		}
		return result;
	}
	
	/**
	 * 拆分数组
	 * @param array
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>T[][] splitArray(T[] array, int limit) {
		if(array == null) {
			return null;
		}
		if(limit <= 0) {
			throw new RuntimeException("参数limit必须大于0");
		}
		
		Class<T> clazz = getClass(array);
		List<T[]> list = newArrayList();
		List<T> itemList = newArrayList();
		for(int i=0; i<array.length; i++) {
			if(i != 0 && i%limit == 0) {
				list.add(toArray(itemList, clazz));
				itemList = newArrayList();
			}
			T item = array[i];
			itemList.add(item);
		}
		list.add(toArray(itemList, clazz));
		T[][] temp = (T[][]) newArray(getArrayClass(clazz), list.size());
		return list.toArray(temp);
	}
	
	/**
	 * 截取数组中的一部分作为新数组,类比subString方法
	 * <p>
	 * 利用System.arraycopy()的api做数组复制实现
	 *  begin和limit最好能保证在数组能截取的范围内
	 *  如果不行
	 *  <ul>
	 *  <li>当传入数组为null或者为空时返回空数组</li>
	 *  <li>新数组长度不一定为limit,不会返回null</li>
	 *  <li>begin如果小于0, 强制begin为0</li>
	 *  <li>limit如果超过原数组限制,新数组的长度为有效部分,最短为0</li>
	 *  </ul>
	 * </p>
	 * 
	 * <p>
     * 例如:
     * ListUtils.subArray(new Integer[]{1}, 0, 3)=>[1]
     * ListUtils.subArray(new Integer[]{1}, 1, 3)=>[]
     * </p>
	 * 
	 * @param array
	 * @param begin
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>T[] subArray(T[] array, int begin, int limit) {
		if(array == null || array.length == 0) {
			return (T[]) new Object[0];
		}
		if(begin<0){
			begin=0;
		}
		int maxLength = array.length-begin;
		if(limit>maxLength) {
			limit = maxLength;
		}
		if(limit>0) {
			Object[] result = new Object[limit];
			System.arraycopy(array, begin, result, 0, limit);
			return (T[]) result;
		}
		return (T[]) new Object[0];
	}
	
	/**
     * 删除符合条件的列表项
     * 
     * <pre>
     * 		filter返回true时则会删除该项
     * </pre>
     * </p>
     * </p>
     */
	public static <T>List<T> removeIf(List<T> list, Predicate<T> filter) {
		if(list == null) {
			return null;
		}
		list.removeIf(filter);
		return list;
	}
	
	
	/**
	 * 将列表转化为数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>T[] toArray(List<T> list, Class<T> clazz) {
		if(list == null) {
			return null;
		}
		if(list.isEmpty()) {
			return (T[]) newArray(clazz, 0);
		}
		return list.toArray((T[]) newArray(clazz, list.size()));
	}
	
	/**
     * 列表转List<Map<String, Object>>
     * <p>
     * 例如：
     * </p>
     * 
     * <pre>
     * 		现有值为[1,2,3]的列表list,执行
     * 		ListUtils.toMap(list, "a");
     * 		将会得到值为[{a:1},{a:2},{a:3}]的列表
     * 		值得注意的是如果传入的key为null的话返回也会是null,传入的list为null返回的将是空列表
     * </pre>
     * </p>
     * </p>
     */
	public static <T>List<Map<String, Object>> toListMap(List<T> list, String key) {
		if(key == null) {	//键为null的情况
			return null;
		}
		if(list == null) {
			return newList();
		}
		
		List<Map<String, Object>> newList =newList();
		for (T item : list) {
			Map<String,	Object> map = CollectionAndMapUtils.newHashMap();
			map.put(key, item);
			newList.add(map);
		}
		return newList;
	} 
	
	//--复制
	
	/**
	 * 深度拷贝（有问题，会报错）
	 * @return
	 */
//	public static <T>List<T> copy(List<T> list) {
//		List<T> newList = emptyToCopy(list);
////		Collections.addAll(newList,  toArray(list)); 
//		Collections.copy(newList, list);	//深拷贝，不光拷贝的是src的元素（引用），src内每个元素的所指向的对象都进行一次拷贝。即是两个list的每个元素所指向的不是同一内存
//		return newList;
//	}
	
	/**
	 * 深拷贝(原理是序列化和反序列化)
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static <T extends Serializable> List<T> deepCopy(List<T> src) throws Exception {
		try {
		    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
		    ObjectOutputStream out = new ObjectOutputStream(byteOut);  
		    out.writeObject(src);  
		  
		    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
		    ObjectInputStream in = new ObjectInputStream(byteIn);  
		    @SuppressWarnings("unchecked")  
		    List<T> dest = (List<T>) in.readObject();  
		    return dest;
		} catch(IOException|ClassNotFoundException ex) {
			throw ex;
		}
	}
	
	//--排序
	
	/**
	 * 列表倒序排列
	 */
	public static <T>List<T> sortReverse(List<T> list) {
		if(list == null) {
			return null;
		}
		Collections.reverse(list);
		return list;
	}
	
	/**
	 * 列表随机排序
	 */
	public static <T>List<T> sortShuffle(List<T> list) {
		if(list == null) {
			return list;
		}
		Collections.shuffle(list);
		return list;
	}
	
	/**
	 * 判断字符串在数组中的位置,无视大小写
	 * 
	 * @param list
	 * @param item
	 * @return
	 */
	public static Optional<Integer> inListIgnoreCase(List<String> list, String item) {
		if(isEmpty(list) || item == null) {
			return Optional.empty();
		}
		for(int i=0; i<list.size(); i++) {
			if(item.equalsIgnoreCase(list.get(i))) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 获取一个元素在数组中的位置
	 * <p>
	 * 	用equals实现比较<br/>
	 * 	通过调用isPresent()方法直接获取是否在数组中,不需要判断值是否大于-1
	 * </p>
	 * @param array
	 * @param item
	 * @return
	 */
	public static <T>Optional<Integer> inArray(T[] array, Object item) {
		if(array == null || item==null) {
			return Optional.empty();
		}
		for(int i=0;i<array.length;i++) {
			if(item.equals(array[i])) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 获取字符串在字符串数组里的位置,大小写无视
	 * @param array
	 * @param item
	 * @return
	 */
	public static Optional<Integer> inArrayIgnoreCase(String[] array, String item) {
		if(array == null || item==null) {
			return Optional.empty();
		}
		for(int i=0;i<array.length;i++) {
			if(item.equalsIgnoreCase(array[i])) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 获取一个元素在数据堆中的位置
	 * <p>
	 * 	用equals实现比较<br/>
	 * 	通过调用isPresent()方法直接获取是否在数组中,不需要判断值是否大于-1
	 * </p>
	 * @param target	目标元素
	 * @param objs 数据堆
	 * @return
	 */
	public static <T>Optional<Integer> in(Object target, Object... objs) {
		if(objs == null || objs.length == 0 || target == null) {
			return Optional.empty();
		}
		for(int i=0;i<objs.length;i++) {
			if(target.equals(objs[i])) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
	
	/**
	 * 获取一个元素在数据堆中的位置,无视大小写
	 * <p>
	 * 	用equals实现比较<br/>
	 * 	通过调用isPresent()方法直接获取是否在数组中,不需要判断值是否大于-1
	 * </p>
	 * 
	 * @param target
	 * @param objs
	 * @return
	 */
	public static Optional<Integer> inIgnoreCase(String target, String... objs) {
		if(objs == null || objs.length == 0 || target == null) {
			return Optional.empty();
		}
		for(int i=0;i<objs.length;i++) {
			if(target.equalsIgnoreCase(objs[i])) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
	
	
	/**
	 * 获取数组类型
	 * @param array
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>Class<T> getClass(T[] array) {
		if(array == null) {
			return null;
		}
		return (Class<T>) array.getClass().getComponentType();
	}
	
	//--获取
	/**
	 * 获取列表第index项的值，避免空指针
	 * @param list
	 * @param index
	 * @return
	 */
	public static <T>T get(List<T> list, int index) {
		if(list == null || list.size()<=index) {
			return null;
		}
		return list.get(index);
	}
	
	/**
	 * 获取数组第index项的值，避免空指针
	 * @param arrays
	 * @param index
	 * @return
	 */
	public static <T>T get(T[] arrays, int index) {
		if(arrays == null || arrays.length<=index) {
			return null;
		}
		return arrays[index];
	}
	
	/*----内部方法----*/
	/**
	 * 获取镜像的空列表，支持arrayList,vector和linklist
	 * @param list
	 * @return
	 */
	private static <T>List<T> emptyToCopy(List<T> list) {
		if(list == null) {	//所有方法都要做空指针判断
			return null;
		}
		List<T> newList;
		if(list instanceof Vector) {	//继承于线程安全列表
			newList = CollectionAndMapUtils.newVector();
		}else if(list instanceof LinkedList) {
			newList = CollectionAndMapUtils.newLinkedList();
		}else {
			newList = newArrayList();
		}
		return newList;
	}
	
	/**
	 * 获取一个类型对应的数组类型
	 * @param clazz
	 * @return
	 */
	private static Class<?> getArrayClass(Class<?> clazz) {
		return newArray(clazz, 0).getClass();
	}
}
