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
    private EntryNode<K,V> root;

    /**
     * 当前二叉树的大小
     * */
    private int size;

    /**
     * 默认构造函数
     * */
    public TreeMap() {
        this.size = 0;
    }

    /**
     * 二叉搜索树 内部节点
     * */
    static class EntryNode<K,V> implements Map.EntryNode<K,V>{
        K key;
        V value;

        EntryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 左孩子节点
         * */
        EntryNode<K,V> left;

        /**
         * 右孩子节点
         * */
        EntryNode<K,V> right;

        /**
         * 双亲节点
         * */
        EntryNode<K,V> parent;

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

    /**
     * 获得key对应的目标节点
     * @param key   对应的key
     * @return      对应的目标节点
     *
     * */
    private EntryNode<K, V> getTargetEntryNode(K key){
        //:::如果根节点为空
        if(this.root == null){
            //:::返回根节点
            return this.root;
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        EntryNode<K,V> targetEntryNode = getTargetEntryNode(key);

        V oldValue = targetEntryNode.value;
        targetEntryNode.setValue(value);
        return oldValue;
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
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = null;
    }

    @Override
    public Iterator<Map.EntryNode<K, V>> iterator() {
        return null;
    }
}
