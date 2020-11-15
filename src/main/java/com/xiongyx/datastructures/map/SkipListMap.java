package com.xiongyx.datastructures.map;

import com.xiongyx.datastructures.exception.IteratorStateErrorException;
import com.xiongyx.datastructures.iterator.Iterator;

import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

/**
 * @author xiongyx
 * on 2020/11/14.
 */
public class SkipListMap<K,V> extends AbstractMap<K,V>{

    private Node<K,V> head;
    private Node<K,V> tail;
    private Comparator<K> comparator;
    private int maxLevel;
    private int size;

    /**
     * 插入新节点时，提升的概率为0.5，期望保证上一层和下一层元素的个数之比为 1:2
     * 以达到查询节点时，log(n)的对数时间复杂度
     * */
    private static final double PROMOTE_RATE = 1.0/2.0;
    private static final int INIT_MAX_LEVEL = 1;

    public SkipListMap() {
        // 初始化整个跳表结构
        initialize();
    }

    public SkipListMap(Comparator<K> comparator) {
        this();
        // 设置比较器
        this.comparator = comparator;
    }

    private void initialize(){
        this.size = 0;
        this.maxLevel = INIT_MAX_LEVEL;

        // 构造左右哨兵节点
        Node<K,V> headNode = new Node<>();
        headNode.nodeType = NodeType.LEFT_SENTINEL;

        Node<K,V> tailNode = new Node<>();
        tailNode.nodeType = NodeType.RIGHT_SENTINEL;

        // 跳表初始化时只有一层，包含左右哨兵两个节点
        this.head = headNode;
        this.tail = tailNode;
        // 左右哨兵牵手
        this.head.right = this.tail;
        this.tail.left = this.head;
    }

    // ==============================api================================

    @Override
    public V put(K key, V value) {
        Node<K,V> oldNode = putNode(key,value);
        if(oldNode == null){
            return null;
        }else{
            V oldValue = oldNode.value;
            oldNode.value = value;
            return oldValue;
        }
    }

    @Override
    public V remove(K key) {
        Node<K,V> node = removeNode(key);
        if(node == null){
            return null;
        }else{
            return node.value;
        }
    }

    @Override
    public V get(K key) {
        Node<K,V> node = searchNode(key);
        if(node == null){
            return null;
        }else{
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        Node<K,V> node = searchNode(key);

        return node != null;
    }

    @Override
    public boolean containsValue(V value) {
        // 找到最底层的头结点，遍历最底层链表
        Node<K,V> lowestHeadNode = getLowestLevelHeadNode();

        Node<K,V> rightNode = lowestHeadNode.right;
        while(rightNode.nodeType != NodeType.RIGHT_SENTINEL){
            if(Objects.equals(rightNode.value,value)){
                // 匹配，返回true
                return true;
            }
        }

        // 未匹配到任何一个，返回false
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        // 初始化整个跳表结构
        initialize();
    }

    @Override
    public Iterator<EntryNode<K,V>> iterator() {
        return new KVItr();
    }

    // ================================私有方法=================================

    /**
     * 链表迭代器实现
     * */
    private class KVItr implements Iterator<Map.EntryNode<K,V>>{
        /**
         * 当前迭代器光标位置
         * 初始化指向 头部哨兵节点
         * */
        private Node<K,V> currentNode = getLowestLevelHeadNode();
        /**
         * 最近一次迭代返回的数据
         * */
        private Node<K,V> lastReturned;

        @Override
        public boolean hasNext() {
            // 判断当前节点的下一个节点 是否是 尾部哨兵节点
            return (this.currentNode.right.nodeType != NodeType.RIGHT_SENTINEL);
        }

        @Override
        public EntryNode<K,V> next() {
            // 指向当前节点的下一个节点
            this.currentNode = this.currentNode.right;

            // 设置最近一次返回的节点
            this.lastReturned = currentNode;

            // 返回当前节点
            return this.currentNode;
        }

        @Override
        public void remove() {
            if(this.lastReturned == null){
                throw new IteratorStateErrorException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }

            // 当前光标指向的节点要被删除,先暂存引用
            Node<K,V> nodeWillRemove = this.currentNode;

            // 由于当前节点需要被删除,因此光标前移,指向当前节点的上一个节点
            this.currentNode = this.currentNode.left;

            // 移除操作需要将最近一次返回设置为null，防止多次remove
            this.lastReturned = null;

            // 将节点从水平链表中移除
            removeNode(nodeWillRemove.getKey());
        }
    }


    private Node<K,V> searchNode(K key){
        Node<K,V> preNode = findPredecessorNode(key);
        if(preNode.key != null && Objects.equals(preNode.key,key)){
            return preNode;
        }else{
            return null;
        }
    }

    private Node<K,V> putNode(K key,V value){
        if(key == null){
            throw new RuntimeException("key required");
        }

        // 从最底层中，找到其直接最接近的前驱节点
        Node<K,V> predecessorNode = findPredecessorNode(key);

        if(Objects.equals(key,predecessorNode.key)){
            // data匹配，已经存在，直接返回false代表未插入成功
            return predecessorNode;
        }

        // 当前跳表元素个数+1
        this.size++;

        // 之前不存在，需要新插入节点
        Node<K,V> newNode = new Node<>(key,value);
        // 将新节点挂载至前驱节点之后
        predecessorNode.linkAsRight(newNode);
        int currentLevel = INIT_MAX_LEVEL;

        Random random = new Random();

        Node<K,V> hasUpNodePredecessorNode = predecessorNode;
        Node<K,V> newNodeUpperNode = newNode;

        boolean doPromoteLevel = false;
        while (random.nextDouble() < PROMOTE_RATE && !doPromoteLevel) {
            // 当前插入的节点需要提升等级，在更高层插入索引节点
            if(currentLevel == this.maxLevel){
                promoteLevel();
                // 保证一次插入节点，做多只会提升一层（否则将会有小概率出现高位的许多层中只有极少数(甚至只有1个)元素的情况）
                doPromoteLevel = true;
            }

            // 找到上一层的前置节点
            while (hasUpNodePredecessorNode.up == null) {
                // 向左查询，直到找到最近的一个有上层节点的前驱节点
                hasUpNodePredecessorNode = hasUpNodePredecessorNode.left;
            }
            // 指向上一层的node
            hasUpNodePredecessorNode = hasUpNodePredecessorNode.up;

            Node<K,V> upperNode = new Node<>(key,value);
            // 将当前data的up节点和上一层最接近的左上的node建立连接
            hasUpNodePredecessorNode.linkAsRight(upperNode);

            // 当前data这一列的上下节点建立关联
            upperNode.down = newNodeUpperNode;
            newNodeUpperNode.up = upperNode;

            // 由于当前data节点可能需要在更上一层建立索引节点，所以令newNodeUpperNode指向更上层的up节点
            newNodeUpperNode = newNodeUpperNode.up;
            // 当前迭代层次++
            currentLevel++;
        }

        return null;
    }

    private Node<K,V> removeNode(K key){
        Node<K,V> needRemoveNode = searchNode(key);
        return removeNode(needRemoveNode);
    }

    private Node<K,V> removeNode(Node<K,V>needRemoveNode){
        if (needRemoveNode == null){
            // 如果没有找到对应的节点，不需要删除，直接返回
            return null;
        }
        // 当前跳表元素个数-1
        this.size--;

        // 保留需要返回的最底层节点Node
        Node<K,V> returnCache = needRemoveNode;

        // 找到了对应节点，则当前节点以及其所有层的up节点都需要被删除
        int currentLevel = INIT_MAX_LEVEL;
        while (needRemoveNode != null){
            // 将当前节点从该水平层的链表中移除(令其左右节点直接牵手)
            needRemoveNode.unlinkSelfHorizontal();

            // 当该节点的左右都是哨兵节点时，说明当前层只剩一个普通节点
            boolean onlyOneNormalData =
                    needRemoveNode.left.nodeType == NodeType.LEFT_SENTINEL &&
                    needRemoveNode.right.nodeType == NodeType.RIGHT_SENTINEL;
            boolean isLowestLevel = currentLevel == INIT_MAX_LEVEL;

            if(!isLowestLevel && onlyOneNormalData){
                // 不是最底层，且只剩当前一个普通节点了，需要删掉这一层(将该层的左哨兵节点传入)
                destroyLevel(needRemoveNode.left);
            }else{
                // 不需要删除该节点
                currentLevel++;
            }
            // 指向该节点的上一点
            needRemoveNode = needRemoveNode.up;
        }

        return returnCache;
    }

    private Node<K,V> findPredecessorNode(K key){
        // 从跳表头结点开始，从上层到下层逐步逼近
        Node<K,V> currentNode = head;

        while(true){
            // 当前遍历节点的右节点不是右哨兵，且data >= 右节点data
            while (currentNode.right.nodeType != NodeType.RIGHT_SENTINEL && doCompare(key,currentNode.right.key) >= 0){
                // 指向同一层的右节点
                currentNode = currentNode.right;
            }

            // 跳出了上面循环，说明找到了同层最接近的一个节点
            if(currentNode.down != null){
                // currentNode.down != null,未到最底层，进入下一层中继续查找、逼近
                currentNode = currentNode.down;
            }else{
                // currentNode.down == null，说明到了最下层保留实际节点的，直接返回
                // （currentNode.data并不一定等于参数data，可能是最逼近的前缀节点）
                return currentNode;
            }
        }
    }

    /**
     * 提升当前跳表的层次(在当前最高层上建立一个只包含左右哨兵的一层，并令跳表的head指向左哨兵)
     * */
    private void promoteLevel(){
        // 最大层数+1
        this.maxLevel++;

        // 当前最高曾左、右哨兵节点
        Node<K,V> upperLeftSentinel = new Node<>(NodeType.LEFT_SENTINEL);
        Node<K,V> upperRightSentinel = new Node<>(NodeType.RIGHT_SENTINEL);

        // 最高层左右哨兵牵手
        upperLeftSentinel.right = upperRightSentinel;
        upperRightSentinel.left = upperLeftSentinel;

        // 最高层的左右哨兵，和当前第一层的head/right建立上下连接
        upperLeftSentinel.down = this.head;
        upperRightSentinel.down = this.tail;

        this.head.up = upperLeftSentinel;
        this.tail.up = upperRightSentinel;

        // 令跳表的head/tail指向最高层的左右哨兵
        this.head = upperLeftSentinel;
        this.tail = upperRightSentinel;
    }

    private Node<K,V> getLowestLevelHeadNode(){
        // 找到最底层的head节点
        Node<K,V> headNode = this.head;
        while(headNode.down != null){
            headNode = headNode.down;
        }

        return headNode;
    }

    private void destroyLevel(Node<K,V> levelLeftSentinelNode){
        // 最大层数减1
        this.maxLevel--;

        // 当前层的右哨兵节点
        Node<K,V> levelRightSentinelNode = levelLeftSentinelNode.right;

        if(levelLeftSentinelNode == this.head){
            // 需要删除的是当前最高层(levelLeftSentinelNode是跳表的头结点)

            // 令下一层的左右哨兵节点的up节点清空
            levelLeftSentinelNode.down.up = null;
            levelRightSentinelNode.down.up = null;

            // 令跳表的head/tail指向最高层的左右哨兵
            this.head = levelLeftSentinelNode.down;
            this.tail = levelRightSentinelNode.down;
        }else{
            // 需要删除的是中间层

            // 移除当前水平层左哨兵，令其上下节点建立连接
            levelLeftSentinelNode.unlinkSelfVertical();
            // 移除当前水平层右哨兵，令其上下节点建立连接
            levelRightSentinelNode.unlinkSelfHorizontal();
        }
    }

    private String getLevelListToString(Node<K,V> head){
        // 列表起始使用"["
        StringBuilder s = new StringBuilder("[");

        while(true){
            if(head.nodeType != NodeType.RIGHT_SENTINEL){
                // 不是最后一个元素
                // 使用", "分割，拼接到后面
                s.append(head).append(", ");
            }else{
                s.append(head).append("]");
                return s.toString();
            }
            head = head.right;
        }
    }

    private String getAllListToString(){
        if(this.isEmpty()){
            return "[]";
        }

        StringBuilder s = new StringBuilder();
        int currentLevel = this.maxLevel;

        // 遍历每一层
        Node<K,V> headNode = this.head;
        while(headNode != null){
            String levelListString = getLevelListToString(headNode);
            s.append("level").append(currentLevel).append(":").append(levelListString).append(System.lineSeparator());
            headNode = headNode.down;
            currentLevel--;
        }

        return s.toString();
    }

    @SuppressWarnings("unchecked")
    private int doCompare(K key1,K key2){
        if(this.comparator != null){
            // 如果跳表被设置了比较器，则使用比较器进行比较
            return this.comparator.compare(key1,key2);
        }else{
            // 否则强制转换为Comparable比较(对于没有实现Comparable的key会抛出强制类型转换异常)
            return ((Comparable)key1).compareTo(key2);
        }
    }

    private String getLowestListToString() {
        if(this.isEmpty()){
            return "[]";
        }

        // 找到最底层的head节点
        Node<K,V> headNode = getLowestLevelHeadNode();
        return getLevelListToString(headNode);
    }

    private enum NodeType{
        /**
         * 普通节点
         * */
        NORMAL,

        /**
         * 左侧哨兵节点
         * */
        LEFT_SENTINEL,

        /**
         * 右侧哨兵节点
         * */
        RIGHT_SENTINEL,
        ;
    }
    private static class Node<K,V> implements EntryNode<K,V>{
        K key;
        V value;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> up;
        Node<K,V> down;

        NodeType nodeType;

        private Node(K key,V value) {
            this.key = key;
            this.value = value;
            this.nodeType = NodeType.NORMAL;
        }

        private Node() {
        }

        private Node(NodeType nodeType) {
            this.nodeType = nodeType;
        }

        /**
         * 将一个节点作为"当前节点"的"右节点" 插入链表
         * @param node  需要插入的节点
         * */
        private void linkAsRight(Node<K,V> node){
            //:::先设置新增节点的 左右节点
            node.left = this;
            node.right = this.right;

            //:::将新增节点插入 当前节点和当前节点的左节点之间
            this.right.left = node;
            this.right = node;
        }

        /**
         * 将"当前节点"从当前水平链表移除(令其左右节点直接牵手)
         * */
        private void unlinkSelfHorizontal(){
            // 令当前链表的 左节点和右节点建立关联
            this.left.right = this.right;
            // 令当前链表的 右节点和左节点建立关联
            this.right.left = this.left;
        }

        /**
         * 将"当前节点"从当前垂直链表移除(令其上下节点直接牵手)
         * */
        private void unlinkSelfVertical(){
            // 令当前链表的 左节点和右节点建立关联
            this.up.down = this.down;
            // 令当前链表的 右节点和左节点建立关联
            this.down.up = this.up;
        }

        @Override
        public String toString() {
            if(this.key != null){
                return "{" +
                        "key=" + key +
                        ",value=" + value +
                        '}';
            }else{
                return "{" +
                        "nodeType=" + nodeType +
                        '}';
            }
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        SkipListMap<Integer,String> skipListMap = new SkipListMap<>();

        String defaultValue = "sss";
        int max = 1000;
        int mustExist = 37;
        skipListMap.put(mustExist,defaultValue);
        for(int i=1; i<10; i++){
            skipListMap.put((int)(Math.random() * max),defaultValue);
        }
//        System.out.println(list.getLowestListToString());
        System.out.println(skipListMap.getAllListToString());
        System.out.println("maxLevel = " + skipListMap.maxLevel + " size=" + skipListMap.size());
//        System.out.println("maxLevel = " + skipListMap.maxLevel + " size=" + skipListMap.size());
//        System.out.println("mustExist removed=" + skipListMap.remove(mustExist));
//        skipListMap.remove(mustExist);
//        System.out.println("mustExist find after remove:" + skipListMap.searchNode(mustExist));
//
//        int maybeExist = 233;
//        System.out.println("maybeExist find:" + skipListMap.searchNode(maybeExist));
//        System.out.println("maybeExist removed=" + skipListMap.remove(maybeExist));
//        System.out.println("maybeExist find after remove:" + skipListMap.searchNode(maybeExist));

        System.out.println(skipListMap);

        Iterator<EntryNode<Integer,String>> iterator = skipListMap.iterator();
        while(iterator.hasNext()){
            Map.EntryNode<Integer,String> entry = iterator.next();
            if(entry.getKey() % 2 == 0){
                System.out.println("删除key entry="+entry);
                iterator.remove();
            }
        }
        System.out.println(skipListMap);
        System.out.println(skipListMap.getAllListToString());
        System.out.println("maxLevel = " + skipListMap.maxLevel + " size=" + skipListMap.size());
    }
}
