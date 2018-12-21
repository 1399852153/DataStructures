package com.xiongyx.datastructures.map;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * @Author xiongyx
 * on 2018/12/21.
 *
 * Map ADT接口
 */
public interface Map <K,V>{
    /**
     * 存入键值对
     * @param key   key值
     * @param value value
     * @return 被覆盖的的value值
     */
    V put(K key,V value);

    /**
     * 移除键值对
     * @param key   key值
     * @return 被删除的value的值
     */
    V remove(K key);

    /**
     * 获取key对应的value值
     * @param key   key值
     * @return      对应的value值
     */
    V get(K key);

    /**
     * 是否包含当前key值
     * @param key   key值
     * @return      true：包含 false：不包含
     */
    boolean containsKey(K key);

    /**
     * 是否包含当前value值
     * @param value   value值
     * @return        true：包含 false：不包含
     */
    boolean containsValue(V value);

    /**
     * 获得当前map存储的键值对数量
     * @return 键值对数量
     * */
    int size();

    /**
     * 当前map是否为空
     * @return  true：为空 false：不为空
     */
    boolean isEmpty();

    /**
     * 清空当前map
     */
    void clear();

    /**
     * 获得迭代器
     * @return 迭代器对象
     */
    Iterator iterator();
}
