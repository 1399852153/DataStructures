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

        EntryNode(K key, V value,EntryNode<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
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

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * nearestEntryNode 和目标节点的相对位置
     * */
     private enum RelativePosition {
        /**
         * 左节点
         * */
        LEFT,

        /**
         * 右节点
         * */
        RIGHT,

        /**
         * 当前节点
         * */
        CURRENT;
    }
    /**
     * 查找目标节点 返回值
     * */
    private static class TargetEntryNode<K,V>{
        /**
         * 距离目标节点最近的节点
         * */
        private EntryNode<K,V> nearestEntryNode;

        /**
         * 相对位置
         * */
        private RelativePosition relativePosition;

        TargetEntryNode(EntryNode<K,V> nearestEntryNode, RelativePosition relativePosition) {
            this.nearestEntryNode = nearestEntryNode;
            this.relativePosition = relativePosition;
        }
    }

    /**
     * 获得key对应的目标节点
     * @param key   对应的key
     * @return      对应的目标节点
     *               返回null代表 目标节点不存在
     * */
    private TargetEntryNode<K,V> getTargetEntryNode(K key){
        int compareResult = 0;
        EntryNode<K,V> parent = null;
        EntryNode<K,V> currentNode = this.root;
        while(currentNode != null){
            parent = currentNode;
            //:::当前key 和 currentNode.key进行比较
            compareResult = compare(key,currentNode.key);
            if(compareResult > 0){
                //:::当前key 大于currentNode 指向右边节点
                currentNode = currentNode.right;
            }else if(compareResult < 0){
                //:::当前key 小于currentNode 指向右边节点
                currentNode = currentNode.left;
            }else{
                return new TargetEntryNode<>(currentNode, RelativePosition.CURRENT);
            }
        }

        if(compareResult > 0){
            return new TargetEntryNode<>(parent, RelativePosition.RIGHT);
        }else if(compareResult < 0){
            return new TargetEntryNode<>(parent, RelativePosition.LEFT);
        }else{
            throw new RuntimeException("状态异常");
        }
    }

    /**
     * key值进行比较
     * */
    @SuppressWarnings("unchecked")
    private int compare(K k1,K k2){
        return ((Comparable) k1).compareTo(k2);
    }

    @Override
    public V put(K key, V value) {
        if(this.root == null){
            compare(key,key);
            this.root = new EntryNode<>(key,value);
            this.size++;
            return null;
        }

        //:::获得目标节点
        TargetEntryNode<K,V> targetEntryNode = getTargetEntryNode(key);
        if(targetEntryNode.relativePosition == RelativePosition.CURRENT){
            //:::目标节点存在于当前容器

            //:::暂存之前的value
            V oldValue = targetEntryNode.nearestEntryNode.value;
            //:::替换为新的value
            targetEntryNode.nearestEntryNode.value = value;
            //:::返回之前的value
            return oldValue;
        }else{
            //:::目标节点不存在于当前容器
            EntryNode<K,V> nearestEntryNode = targetEntryNode.nearestEntryNode;
            if(targetEntryNode.relativePosition == RelativePosition.LEFT){
                //:::目标节点位于左边
                nearestEntryNode.left = new EntryNode<>(key,value,nearestEntryNode);
            }else{
                //:::目标节点位于右边
                nearestEntryNode.right = new EntryNode<>(key,value,nearestEntryNode);
            }

            this.size++;
            return null;
        }
    }

    @Override
    public V remove(K key) {
        if(this.root == null){
            return null;
        }

        //:::查询目标节点
        TargetEntryNode<K,V> targetEntryNode = getTargetEntryNode(key);
        if(targetEntryNode.relativePosition != RelativePosition.CURRENT){
            //:::没有找到目标节点
            return null;
        }else{
            //:::todo 删除目标节点

            this.size--;
            return targetEntryNode.nearestEntryNode.value;
        }
    }

    @Override
    public V get(K key) {
        if(this.root == null){
            return null;
        }

        //:::查询目标节点
        TargetEntryNode<K,V> targetEntryNode = getTargetEntryNode(key);
        if(targetEntryNode.relativePosition != RelativePosition.CURRENT){
            //:::没有找到目标节点
            return null;
        }else{
            return targetEntryNode.nearestEntryNode.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return (get(key) != null);
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

    @Override
    public String toString(){


        return "";
    }

    private String show(EntryNode<K,V> entryNode){
        return entryNode.toString();
    }
}
