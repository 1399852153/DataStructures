package com.xiongyx.datastructures.map;

import com.xiongyx.datastructures.exception.IteratorStateErrorException;
import com.xiongyx.datastructures.iterator.Iterator;
import java.util.Comparator;
import java.util.Objects;

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
     * 比较器(初始化之后，不能改)
     * */
    private final Comparator<? super K> comparator;

    /**
     * 当前二叉树的大小
     * */
    private int size;

    /**
     * 默认构造函数
     * */
    public TreeMap() {
        this.comparator = null;
    }

    /**
     * 指定了比较器的构造函数
     * */
    public TreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
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
     * 二叉搜索树 迭代器实现
     * */
    private class Itr implements Iterator<Map.EntryNode<K,V>>{
        /**
         * 当前迭代节点
         * */
        private EntryNode<K,V> currentNode;

        /**
         * 下一个节点
         * */
        private EntryNode<K,V> nextNode;

        private Itr() {
            //:::初始化时，nextNode指向第一个节点
            this.nextNode = TreeMap.this.getFirstNode();
        }

        @Override
        public boolean hasNext() {
            return (this.nextNode != null);
        }

        @Override
        public Map.EntryNode<K, V> next() {
            this.currentNode = this.nextNode;

            this.nextNode = TreeMap.this.getSuccessor(this.nextNode);

            return this.currentNode;
        }

        @Override
        public void remove() {
            if(this.currentNode == null){
                throw new IteratorStateErrorException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }

            //:::判断当前被删除的节点是否同时存在左右孩子
            if(this.currentNode.left != null && this.currentNode.right != null){
                /*
                    同时存在左右孩子的节点删除时会和直接后继(nextNode)进行交换
                    因此nextNode指向当前节点
                 */
                this.nextNode = this.currentNode;
            }
            //:::删除当前节点
            TreeMap.this.deleteEntryNode(this.currentNode);

            //:::currentNode设置为null，防止反复调用remove方法
            this.currentNode = null;
        }
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
            deleteEntryNode(targetEntryNode.target);

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
        //:::寻找到第一个节点
        EntryNode<K,V> entryNode = getFirstNode();

        //:::从第一个节点开始，遍历整颗二叉搜索树
        while(entryNode != null){
            if(Objects.equals(entryNode.value,value)){
                //:::当前节点value匹配，返回true
                return true;
            }else{
                //:::指向下一个直接后继节点
                entryNode = getSuccessor(entryNode);
            }
        }

        //:::遍历整颗树之后，还未匹配，返回false
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
        return new Itr();
    }

    @Override
    public String toString(){
        Iterator<Map.EntryNode<K,V>> iterator = this.iterator();

        //:::空容器
        if(!iterator.hasNext()){
            return "[]";
        }

        //:::容器起始使用"["
        StringBuilder s = new StringBuilder("[");

        //:::反复迭代
        while(true){
            //:::获得迭代的当前元素
            Map.EntryNode<K,V> data = iterator.next();

            //:::判断当前元素是否是最后一个元素
            if(!iterator.hasNext()){
                //:::是最后一个元素，用"]"收尾
                s.append(data).append("]");
                //:::返回 拼接完毕的字符串
                return s.toString();
            }else{
                //:::不是最后一个元素
                //:::使用", "分割，拼接到后面
                s.append(data).append(", ");
            }
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
                return new TargetEntryNode<>(currentNode, parent, RelativePosition.CURRENT);
            }
        }

        //:::没有找到目标节点
        if(compareResult > 0){
            //:::返回 右孩子 哨兵节点
            return new TargetEntryNode<>(null, parent, RelativePosition.RIGHT);
        }else if(compareResult < 0){
            //:::返回 左孩子 哨兵节点
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
        //:::迭代器不存在
        if(this.comparator == null){
            //:::依赖对象本身的 Comparable，可能会转型失败
            return ((Comparable) k1).compareTo(k2);
        }else{
            //:::通过迭代器逻辑进行比较
            return this.comparator.compare(k1,k2);
        }
    }

    /**
     * 将目标节点从二叉搜索树中删除
     * @param target 需要被删除的节点
     * */
    private void deleteEntryNode(EntryNode<K,V> target){
        /*
         * 删除二叉搜索树节点
         * 	1.无左右孩子
         * 		直接删除
         * 	2.只有左孩子或者右孩子
         * 		将唯一的孩子和parent节点直接相连
         * 	3.既有左孩子，又有右孩子
         * 		找到自己的直接前驱/后继（左侧的最右节点/右侧的最左节点）
         * 		将自己和直接后继进行交换，转换为第1或第2种情况，并将自己删除
         * */

        //:::size自减1
        size--;

        //:::既有左孩子，又有右孩子
        if(target.left != null && target.right != null){
            //:::找到直接后继(右侧的最左节点)
            EntryNode<K,V> targetSuccessor = getSuccessor(target);

            //:::target的key/value和自己的后继交换
            target.key = targetSuccessor.key;
            target.value = targetSuccessor.value;
            //:::target指向自己的后继，转换为第一/第二种情况
            target = targetSuccessor;
        }

        EntryNode<K,V> parent = target.parent;
        RelativePosition relativePosition = getRelativeByParent(parent,target);
        //:::获得代替被删除节点原先位置的节点(从左右孩子中选择一个)
        EntryNode<K,V> replacement = (target.left != null ? target.left : target.right);
        if(replacement == null){
            //:::无左右孩子

            //:::直接删除,断开和双亲节点的联系
            if(relativePosition == RelativePosition.LEFT){
                parent.left = null;
            }else{
                parent.right = null;
            }

            target.parent = null;
        }else{
            //:::只有左孩子或者右孩子
            replacement.parent = target.parent;

            //:::被删除节点的双亲节点指向被代替的节点
            if(relativePosition == RelativePosition.LEFT){
                parent.left = replacement;
            }else{
                parent.right = replacement;
            }
        }
    }

    /**
     * 判断双亲节点和目标节点 相对位置
     * @param parent    双亲节点
     * @param target    目标节点
     * @return          相对位置(左孩子/右孩子)
     */
    private RelativePosition getRelativeByParent(EntryNode<K,V> parent,EntryNode<K,V> target){
        if(parent.left == target){
            return RelativePosition.LEFT;
        }else if(parent.right == target){
            return RelativePosition.RIGHT;
        }else{
            throw new RuntimeException("不是父子节点关系");
        }
    }

    /**
     * 获得当前节点的直接后继
     * @param targetEntryNode     当前节点
     * @return              当前节点的直接后继
     */
    private EntryNode<K,V> getSuccessor(EntryNode<K,V> targetEntryNode){
        if(targetEntryNode == null){
            //:::当前节点为null，则后继也为null
            return null;
        }

        //:::判断当前节点是否存在右孩子
        if(targetEntryNode.right != null){
            //:::存在右孩子，右子树的最左节点为直接后继
            EntryNode<K,V> rightChildSuccessor = targetEntryNode.right;

            //:::循环往复，直至直接右孩子的最左节点
            while(rightChildSuccessor.left != null){
                rightChildSuccessor = rightChildSuccessor.left;
            }

            return rightChildSuccessor;
        }else{
            //:::不存在右孩子，寻找第一个靠右的双亲节点
            EntryNode<K,V> parent = targetEntryNode.parent;
            EntryNode<K,V> child = targetEntryNode;

            //:::判断当前孩子节点是否是双亲节点的左孩子
            while(parent != null && parent.right == child){
                //:::不是左孩子，是右孩子，继续向上寻找
                child = parent;
                parent = parent.parent;
            }

            return parent;
        }
    }

    /**
     * 获得二叉搜索树的第一个节点
     * */
    private EntryNode<K,V> getFirstNode(){
        if(this.root == null){
            //:::空树，返回null
            return null;
        }

        EntryNode<K,V> entryNode = this.root;

        //:::循环往复，寻找整棵树的最左节点(第一个节点)
        while(entryNode.left != null){
            entryNode = entryNode.left;
        }

        return entryNode;
    }
}
