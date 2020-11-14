package com.xiongyx.datastructures.map;

import com.xiongyx.datastructures.functioninterface.Comparator;

import java.util.Random;

/**
 * @author xiongyx
 * on 2020/11/14.
 */
public class SkipList<E> {

    private Node<E> head;
    private Node<E> tail;
    private Comparator<E> comparator;
    private int maxLevel;
    private int size;

    /**
     * 插入新节点时，提升的概率为0.5，期望保证上一层和下一层元素的个数之比为 1:2
     * 以达到查询节点时，log(n)的对数时间复杂度
     * */
    private static final double PROMOTE_RATE = 1.0/2.0;
    private static final int INIT_MAX_LEVEL = 1;

    public SkipList(Comparator<E> comparator) {
        // 设置比较器
        this.comparator = comparator;
        this.size = 0;
        this.maxLevel = INIT_MAX_LEVEL;

        // 构造左右哨兵节点
        Node<E> headNode = new Node<>();
        headNode.nodeType = NodeType.LEFT_SENTINEL;

        Node<E> tailNode = new Node<>();
        tailNode.nodeType = NodeType.RIGHT_SENTINEL;

        // 跳表初始化时只有一层，包含左右哨兵两个节点
        this.head = headNode;
        this.tail = tailNode;
        // 左右哨兵牵手
        this.head.right = this.tail;
        this.tail.left = this.head;
    }

    // ==============================api================================

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }



    // ================================私有方法=================================

    /**
     * 确切的查找data对应的node，未找到则返回null
     * */
    private Node<E> searchNode(E data){
        Node<E> preNode = findPredecessorNode(data);
        if(preNode.data != null && preNode.data.equals(data)){
            return preNode;
        }else{
            return null;
        }
    }

    private E put(E data){
        System.out.println("put data=" + data);
        if(data == null){
            throw new RuntimeException("data required");
        }

        // 从最底层中，找到其直接最接近的前驱节点
        Node<E> predecessorNode = findPredecessorNode(data);

        if(data.equals(predecessorNode.data)){
            // data匹配，已经存在，直接返回false代表未插入成功
            return data;
        }

        // 当前跳表元素个数+1
        this.size++;

        // 之前不存在，需要新插入节点
        Node<E> newNode = new Node<>(data);
        // 将新节点挂载至前驱节点之后
        predecessorNode.linkAsRight(newNode);
        int currentLevel = INIT_MAX_LEVEL;

        Random random = new Random();

        Node<E> hasUpNodePredecessorNode = predecessorNode;
        Node<E> newNodeUpperNode = newNode;

        while (random.nextDouble() < PROMOTE_RATE) {
            // 当前插入的节点需要提升等级，在更高层插入索引节点
            if(currentLevel == this.maxLevel){
                promoteLevel();
            }

            // 找到上一层的前置节点
            while (hasUpNodePredecessorNode.up == null) {
                // 向左查询，直到找到最近的一个有上层节点的前驱节点
                hasUpNodePredecessorNode = hasUpNodePredecessorNode.left;
            }
            // 指向上一层的node
            hasUpNodePredecessorNode = hasUpNodePredecessorNode.up;

            Node<E> upperNode = new Node<>(data);
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

    private Node<E> remove(E data){
        Node<E> needRemoveNode = searchNode(data);
        if (needRemoveNode == null){
            // 如果没有找到对应的节点，不需要删除，直接返回
            return null;
        }
        // 当前跳表元素个数-1
        this.size--;

        // 保留需要返回的最底层节点Node
        Node<E> returnCache = needRemoveNode;

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

    private Node<E> findPredecessorNode(E data){
        // 从跳表头结点开始，从上层到下层逐步逼近
        Node<E> currentNode = head;

        while(true){
            // 当前遍历节点的右节点不是右哨兵，且data >= 右节点data
            while (currentNode.right.nodeType != NodeType.RIGHT_SENTINEL && comparator.compare(data,currentNode.right.data) >= 0){
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
        Node<E> upperLeftSentinel = new Node<>(NodeType.LEFT_SENTINEL);
        Node<E> upperRightSentinel = new Node<>(NodeType.RIGHT_SENTINEL);

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

    private void destroyLevel(Node<E> levelLeftSentinelNode){
        // 最大层数减1
        this.maxLevel--;

        // 当前层的右哨兵节点
        Node<E> levelRightSentinelNode = levelLeftSentinelNode.right;

        if(levelLeftSentinelNode == this.head){
            // 需要删除的是当前最高层(levelLeftSentinelNode是跳表的头结点)

            // 令下一层的左右哨兵节点的up节点清空
            levelLeftSentinelNode.down.up = null;
            levelRightSentinelNode.down.up = null;
        }else{
            // 需要删除的是中间层

            // 移除当前水平层左哨兵，令其上下节点建立连接
            levelLeftSentinelNode.unlinkSelfVertical();
            // 移除当前水平层右哨兵，令其上下节点建立连接
            levelRightSentinelNode.unlinkSelfHorizontal();
        }
    }

    private String getLevelListToString(Node<E> head){

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

    public String getAllListToString(){
        if(this.isEmpty()){
            return "[]";
        }

        StringBuilder s = new StringBuilder();
        int currentLevel = this.maxLevel;

        // 遍历每一层
        Node<E> headNode = this.head;
        while(headNode != null){
            String levelListString = getLevelListToString(headNode);
            s.append("level").append(currentLevel).append(":").append(levelListString).append(System.lineSeparator());
            headNode = headNode.down;
            currentLevel--;
        }

        return s.toString();
    }

    public String getLowestListToString() {
        if(this.isEmpty()){
            return "[]";
        }

        // 找到最底层的head节点
        Node<E> headNode = this.head;
        while(headNode.down != null){
            headNode = headNode.down;
        }

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
    private static class Node<T>{
        T data;
        Node<T> left;
        Node<T> right;
        Node<T> up;
        Node<T> down;

        NodeType nodeType;

        private Node(T data) {
            this.data = data;
            this.nodeType = NodeType.NORMAL;
        }

        private Node() {
        }

        public Node(NodeType nodeType) {
            this.nodeType = nodeType;
        }

        /**
         * 将一个节点作为"当前节点"的"右节点" 插入链表
         * @param node  需要插入的节点
         * */
        private void linkAsRight(Node<T> node){
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
            if(this.data != null){
                return "Node{" +
                        "data=" + data +
                        '}';
            }else{
                return "Node{" +
                        "nodeType=" + nodeType +
                        '}';
            }
        }
    }

        public static void main(String[] args) {
        SkipList<Integer> list= new SkipList<>(Integer::compareTo);


        int max = 1000;
        int mustExist = 37;
        list.put(mustExist);
        for(int i=0; i<128; i++){
            list.put((int)(Math.random() * max));
        }
//        System.out.println(list.getLowestListToString());
        System.out.println(list.getAllListToString());
        System.out.println("maxLevel=" + list.maxLevel);
        System.out.println(list.searchNode(mustExist));
        list.remove(mustExist);
        System.out.println(list.searchNode(mustExist));

        int maybeExist = 233;
        System.out.println(list.searchNode(maybeExist));
        list.remove(maybeExist);
        System.out.println(list.searchNode(maybeExist));
    }
}
