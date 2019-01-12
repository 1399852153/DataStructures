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
     * target 和目标节点的相对位置
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
         * 目标节点
         * */
        private EntryNode<K,V> target;

        /**
         * 目标节点的双亲节点
         * */
        private EntryNode<K,V> parent;

        /**
         * 相对位置
         * */
        private RelativePosition relativePosition;

        TargetEntryNode(EntryNode<K, V> target, EntryNode<K, V> parent, RelativePosition relativePosition) {
            this.target = target;
            this.parent = parent;
            this.relativePosition = relativePosition;
        }
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
            V oldValue = targetEntryNode.target.value;
            //:::替换为新的value
            targetEntryNode.target.value = value;
            //:::返回之前的value
            return oldValue;
        }else{
            //:::目标节点不存在于当前容器
            EntryNode<K,V> parent = targetEntryNode.parent;
            if(targetEntryNode.relativePosition == RelativePosition.LEFT){
                //:::目标节点位于左边
                parent.left = new EntryNode<>(key,value,parent);
            }else{
                //:::目标节点位于右边
                parent.right = new EntryNode<>(key,value,parent);
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
            //:::找到了目标节点

            //:::从二叉树中删除目标节点
            deleteEntryNode(targetEntryNode);

            this.size--;
            return targetEntryNode.target.value;
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
            return targetEntryNode.target.value;
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
        return midTraverse(this.root);
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
                return new TargetEntryNode<>(currentNode, parent, RelativePosition.CURRENT);
            }
        }

        if(compareResult > 0){
            return new TargetEntryNode<>(null, parent, RelativePosition.RIGHT);
        }else if(compareResult < 0){
            return new TargetEntryNode<>(null, parent, RelativePosition.LEFT);
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

    /**
     * 中序遍历 递归简单实现
     * */
    private String midTraverse(EntryNode<K,V> entryNode){
        if(entryNode == null){
            return "";
        }

        String preStr = midTraverse(entryNode.left);
        String successStr = midTraverse(entryNode.right);

        return preStr + entryNode + successStr;
    }

    /**
     * 将目标节点从二叉搜索树中删除
     * @param targetEntryNode 需要被删除的节点
     * */
    private void deleteEntryNode(TargetEntryNode<K,V> targetEntryNode){
        /*
         * 删除二叉搜索树节点
         * 	1.无左右孩子
         * 		直接删除
         * 	2.只有左孩子或者右孩子
         * 		将唯一的孩子和parent节点直接相连
         * 	3.既有左孩子，又有右孩子
         * 		找到距离自己的直接后继（左侧的最右节点/右侧的最左节点）
         * 		将自己和直接后继进行交换，转换为第1或第2种情况，并将自己删除
         * */

        EntryNode<K,V> parent = targetEntryNode.parent;
        EntryNode<K,V> target = targetEntryNode.target;

        //:::无左右孩子
        if(target.left == null && target.right == null){
            RelativePosition relativePosition = getRelativeByParent(parent,target);
            //:::直接删除,断开和双亲节点的联系
            if(relativePosition == RelativePosition.LEFT){
                parent.left = null;
            }else{
                parent.right = null;
            }

            target.parent = null;
            return;
        }

        //:::只有左孩子或者右孩子

    }

    private RelativePosition getRelativeByParent(EntryNode<K,V> parent,EntryNode<K,V> target){
        if(parent.left == target){
            return RelativePosition.LEFT;
        }else if(parent.right == target){
            return RelativePosition.RIGHT;
        }else{
            throw new RuntimeException("状态异常");
        }
    }
}
