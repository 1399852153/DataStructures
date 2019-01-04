package com.xiongyx.datastructures.map;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * @Author xiongyx
 * @Date 2019/1/4
 *
 * 二叉搜索树实现
 */
public class TreeMap<K,V> implements Map<K,V>{

    /**
     * 根节点
     * */
    private Entry<K,V> root;

    /**
     * 当前二叉树的大小
     * */
    private int size;

    /**
     * 二叉搜索树 内部节点
     * */
    static class Entry<K,V> implements Map.EntryNode<K,V>{
        K key;
        V value;

        /**
         * 左孩子节点
         * */
        Entry<K,V> left;

        /**
         * 右孩子节点
         * */
        Entry<K,V> right;

        /**
         * 双亲节点
         * */
        Entry<K,V> parent;

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<EntryNode<K, V>> iterator() {
        return null;
    }
}
