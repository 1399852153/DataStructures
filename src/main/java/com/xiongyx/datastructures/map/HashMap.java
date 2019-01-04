package com.xiongyx.datastructures.map;

import com.xiongyx.datastructures.exception.IteratorStateErrorException;
import com.xiongyx.datastructures.iterator.Iterator;
import com.xiongyx.datastructures.map.Map.EntryNode;

/**
 * @Author xiongyx
 * @Date 2018/12/26
 *
 * 哈希表实现
 */
public class HashMap<K,V> implements Map<K,V>{

    //===========================================成员属性================================================
    /**
     * 内部数组
     * */
    private EntryNode<K,V>[] elements;

    /**
     * 当前哈希表的大小
     * */
    private int size;

    /**
     * 负载因子
     * */
    private float loadFactor;

    /**
     * 默认的哈希表容量
     * */
    private final static int DEFAULT_CAPACITY = 16;

    /**
     * 扩容翻倍的基数 两倍
     * */
    private final static int REHASH_BASE = 2;

    /**
     * 默认的负载因子
     * */
    private final static float DEFAULT_LOAD_FACTOR = 0.75f;

    //========================================构造方法===================================================
    /**
     * 默认构造方法
     * */
    @SuppressWarnings("unchecked")
    public HashMap() {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        elements = new EntryNode[DEFAULT_CAPACITY];
    }

    /**
     * 指定初始容量的构造方法
     * @param capacity 指定的初始容量
     * */
    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        elements = new EntryNode[capacity];
    }

    /**
     * 指定初始容量和负载因子的构造方法
     * @param capacity 指定的初始容量
     * @param loadFactor 指定的负载因子
     * */
    @SuppressWarnings("unchecked")
    public HashMap(int capacity,int loadFactor) {
        this.size = 0;
        this.loadFactor = loadFactor;
        elements = new EntryNode[capacity];
    }

    //==========================================内部辅助方法=============================================
    /**
     * 通过key的hashCode获得对应的内部数组下标
     * @param key 传入的键值key
     * @return 对应的内部数组下标
     * */
    private int getIndex(K key){
        return getIndex(key,this.elements);
    }

    /**
     * 通过key的hashCode获得对应的内部数组插槽slot下标
     * @param key 传入的键值key
     * @param elements 内部数组
     * @return 对应的内部数组下标
     * */
    private int getIndex(K key,EntryNode<K,V>[] elements){
        if(key == null){
            //::: null 默认存储在第0个桶内
            return 0;
        }else{
            int hashCode = key.hashCode();

            //:::通过 高位和低位的异或运算，获得最终的hash映射，减少碰撞的几率
            int finalHashCode = hashCode ^ (hashCode >>> 16);
            return (elements.length-1) & finalHashCode;
        }
    }

    /**
     * 获得目标节点的前一个节点
     * @param currentNode 当前桶链表节点
     * @param key         对应的key
     * @return 返回当前桶链表中"匹配key的目标节点"的"前一个节点"
     *          注意：当桶链表中不存在匹配节点时，返回桶链表的最后一个节点
     * */
    private EntryNode<K,V> getTargetPreviousEntryNode(EntryNode<K,V> currentNode,K key){
        //:::不匹配
        EntryNode<K,V> nextNode = currentNode.next;
        //:::遍历当前桶后面的所有节点
        while(nextNode != null){
            //:::如果下一个节点的key匹配
            if(nextNode.keyIsEquals(key)){
                return currentNode;
            }else{
                //:::不断指向下一个节点
                currentNode = nextNode;
                nextNode = nextNode.next;
            }
        }

        //:::到达了桶链表的末尾，返回最后一个节点
        return currentNode;
    }

    /**
     * 哈希表扩容
     * */
    @SuppressWarnings("unchecked")
    private void reHash(){
        //:::扩容两倍
        EntryNode<K,V>[] newElements = new EntryNode[this.elements.length * REHASH_BASE];

        //:::遍历所有的插槽
        for (int i=0; i<this.elements.length; i++) {
            //:::为单个插槽内的元素 rehash
            reHashSlot(i,newElements);
        }

        //:::内部数组 ---> 扩容之后的新数组
        this.elements = newElements;
    }

    /**
     * 单个插槽内的数据进行rehash
     * */
    private void reHashSlot(int index,EntryNode<K, V>[] newElements){
        //:::获得当前插槽第一个元素
        EntryNode<K, V> currentEntryNode = this.elements[index];
        if(currentEntryNode == null){
            //:::当前插槽为空，直接返回
            return;
        }

        //:::低位桶链表 头部节点、尾部节点
        EntryNode<K, V> lowListHead = null;
        EntryNode<K, V> lowListTail = null;
        //:::高位桶链表 头部节点、尾部节点
        EntryNode<K, V> highListHead = null;
        EntryNode<K, V> highListTail = null;

        while(currentEntryNode != null){
            //:::获得当前节点 在新数组中映射的插槽下标
            int entryNodeIndex = getIndex(currentEntryNode.key,newElements);
            //:::是否和当前插槽下标相等
            if(entryNodeIndex == index){
                //:::和当前插槽下标相等
                if(lowListHead == null){
                    //:::初始化低位链表
                    lowListHead = currentEntryNode;
                    lowListTail = currentEntryNode;
                }else{
                    //:::在低位链表尾部拓展新的节点
                    lowListTail.next = currentEntryNode;
                    lowListTail = lowListTail.next;
                }
            }else{
                //:::和当前插槽下标不相等
                if(highListHead == null){
                    //:::初始化高位链表
                    highListHead = currentEntryNode;
                    highListTail = currentEntryNode;
                }else{
                    //:::在高位链表尾部拓展新的节点
                    highListTail.next = currentEntryNode;
                    highListTail = highListTail.next;
                }
            }
            //:::指向当前插槽的下一个节点
            currentEntryNode = currentEntryNode.next;
        }

        //:::新扩容elements(index)插槽 存放lowList
        newElements[index] = lowListHead;
        //:::lowList末尾截断
        if(lowListTail != null){
            lowListTail.next = null;
        }

        //:::新扩容elements(index + this.elements.length)插槽 存放highList
        newElements[index + this.elements.length] = highListHead;
        //:::highList末尾截断
        if(highListTail != null){
            highListTail.next = null;
        }
    }

    /**
     * 判断是否需要 扩容
     * */
    private boolean needReHash(){
        return ((this.size / this.elements.length) > this.loadFactor);
    }

    //============================================外部接口================================================

    @Override
    public V put(K key, V value) {
        if(needReHash()){
            reHash();
        }

        //:::获得对应的内部数组下标
        int index = getIndex(key);
        //:::获得对应桶内的第一个节点
        EntryNode<K,V> firstEntryNode = this.elements[index];

        //:::如果当前桶内不存在任何节点
        if(firstEntryNode == null){
            //:::创建一个新的节点
            this.elements[index] = new EntryNode<>(key,value);
            //:::创建了新节点，size加1
            this.size++;
            return null;
        }

        if(firstEntryNode.keyIsEquals(key)){
            //:::当前第一个节点的key与之匹配
            V oldValue = firstEntryNode.value;
            firstEntryNode.value = value;
            return oldValue;
        }else{
            //:::不匹配

            //:::获得匹配的目标节点的前一个节点
            EntryNode<K,V> targetPreviousNode = getTargetPreviousEntryNode(firstEntryNode,key);
            //:::获得匹配的目标节点
            EntryNode<K,V> targetNode = targetPreviousNode.next;
            if(targetNode != null){
                //:::更新value的值
                V oldValue = targetNode.value;
                targetNode.value = value;
                return oldValue;
            }else{
                //:::在桶链表的末尾 新增一个节点
                targetPreviousNode.next = new EntryNode<>(key,value);
                //:::创建了新节点，size加1
                this.size++;
                return null;
            }
        }
    }

    @Override
    public V remove(K key) {
        //:::获得对应的内部数组下标
        int index = getIndex(key);
        //:::获得对应桶内的第一个节点
        EntryNode<K,V> firstEntryNode = this.elements[index];

        //:::如果当前桶内不存在任何节点
        if(firstEntryNode == null){
            return null;
        }

        if(firstEntryNode.keyIsEquals(key)){
            //:::当前第一个节点的key与之匹配

            //:::将桶链表的第一个节点指向后一个节点(兼容next为null的情况)
            this.elements[index] = firstEntryNode.next;
            //:::移除了一个节点 size减一
            this.size--;
            //:::返回之前的value值
            return firstEntryNode.value;
        }else{
            //:::不匹配

            //:::获得匹配的目标节点的前一个节点
            EntryNode<K,V> targetPreviousNode = getTargetPreviousEntryNode(firstEntryNode,key);
            //:::获得匹配的目标节点
            EntryNode<K,V> targetNode = targetPreviousNode.next;

            if(targetNode != null){
                //:::将"前一个节点的next" 指向 "目标节点的next" ---> 相当于将目标节点从桶链表移除
                targetPreviousNode.next = targetNode.next;
                //:::移除了一个节点 size减一
                this.size--;
                return targetNode.value;
            }else{
                //:::如果目标节点为空，说明key并不存在于哈希表中
                return null;
            }
        }
    }

    @Override
    public V get(K key) {
        //:::获得对应的内部数组下标
        int index = getIndex(key);
        //:::获得对应桶内的第一个节点
        EntryNode<K,V> firstEntryNode = this.elements[index];

        //:::如果当前桶内不存在任何节点
        if(firstEntryNode == null){
            return null;
        }

        if(firstEntryNode.keyIsEquals(key)){
            //:::当前第一个节点的key与之匹配
            return firstEntryNode.value;
        }else{
            //:::获得匹配的目标节点的前一个节点
            EntryNode<K,V> targetPreviousNode = getTargetPreviousEntryNode(firstEntryNode,key);
            //:::获得匹配的目标节点
            EntryNode<K,V> targetNode = targetPreviousNode.next;

            if(targetNode != null){
                return targetNode.value;
            }else{
                //:::如果目标节点为空，说明key并不存在于哈希表中
                return null;
            }
        }
    }

    @Override
    public boolean containsKey(K key) {
        V value = get(key);
        return (value != null);
    }

    @Override
    public boolean containsValue(V value) {
        //:::遍历全部桶链表
        for (EntryNode<K, V> element : this.elements) {
            //:::获得当前桶链表第一个节点
            EntryNode<K, V> entryNode = element;

            //:::遍历当前桶链表
            while (entryNode != null) {
                //:::如果value匹配
                if (entryNode.value.equals(value)) {
                    //:::返回true
                    return true;
                } else {
                    //:::不匹配，指向下一个节点
                    entryNode = entryNode.next;
                }
            }
        }

        //:::所有的节点都遍历了，没有匹配的value
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
        //:::遍历内部数组，将所有桶链表全部清空
        for(int i=0; i<this.elements.length; i++){
            this.elements[i] = null;
        }

        //:::size设置为0
        this.size = 0;
    }

    @Override
    public Iterator<Map.EntryNode<K,V>> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
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

    private static class EntryNode<K,V> implements Map.EntryNode<K,V>{
        final K key;
        V value;
        EntryNode<K,V> next;

        EntryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        boolean keyIsEquals(K key){
            if(this.key == key){
                return true;
            }

            if(key == null){
                //:::如果走到这步，this.key不等于null，不匹配
                return false;
            }else{
                return key.equals(this.key);
            }
        }

        Map.EntryNode<K, V> getNext() {
            return next;
        }

        void setNext(HashMap.EntryNode<K, V> next) {
            this.next = next;
        }

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
     * 哈希表 迭代器实现
     */
    private class Itr implements Iterator<Map.EntryNode<K,V>> {
        /**
         * 迭代器 当前节点
         * */
        private HashMap.EntryNode<K,V> currentNode;

        /**
         * 迭代器 下一个节点
         * */
        private HashMap.EntryNode<K,V> nextNode;

        /**
         * 迭代器 当前内部数组的下标
         * */
        private int currentIndex;

        /**
         * 默认构造方法
         * */
        private Itr(){
            //:::如果当前哈希表为空，直接返回
            if(HashMap.this.isEmpty()){
                return;
            }
            //:::在构造方法中，将迭代器下标移动到第一个有效的节点上

            //:::遍历内部数组，找到第一个不为空的数组插槽slot
            for(int i=0; i<HashMap.this.elements.length; i++){
                //:::设置当前index
                this.currentIndex = i;

                HashMap.EntryNode<K,V> firstEntryNode = HashMap.this.elements[i];
                //:::找到了第一个不为空的插槽slot
                if(firstEntryNode != null){
                    //:::nextNode = 当前插槽第一个节点
                    this.nextNode = firstEntryNode;

                    //:::构造方法立即结束
                    return;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return (this.nextNode != null);
        }

        @Override
        public Map.EntryNode<K,V> next() {
            this.currentNode = this.nextNode;
            //:::暂存需要返回的节点
            HashMap.EntryNode<K,V> needReturn = this.nextNode;

            //:::nextNode指向自己的next
            this.nextNode = this.nextNode.next;
            //:::判断当前nextNode是否为null
            if(this.nextNode == null){
                //:::说明当前所在的桶链表已经遍历完毕

                //:::寻找下一个非空的插槽
                for(int i=this.currentIndex+1; i<HashMap.this.elements.length; i++){
                    //:::设置当前index
                    this.currentIndex = i;

                    HashMap.EntryNode<K,V> firstEntryNode = HashMap.this.elements[i];
                    //:::找到了后续不为空的插槽slot
                    if(firstEntryNode != null){
                        //:::nextNode = 当前插槽第一个节点
                        this.nextNode = firstEntryNode;
                        //:::跳出循环
                        break;
                    }
                }
            }
            return needReturn;
        }

        @Override
        public void remove() {
            if(this.currentNode == null){
                throw new IteratorStateErrorException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }

            //:::获得需要被移除的节点的key
            K currentKey = this.currentNode.key;
            //:::将其从哈希表中移除
            HashMap.this.remove(currentKey);

            //:::currentNode设置为null，防止反复调用remove方法
            this.currentNode = null;
        }
    }
}
