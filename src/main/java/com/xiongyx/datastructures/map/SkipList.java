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
    private static final double PROMOTE_RATE = 1/2;

    public SkipList(Comparator<E> comparator) {
        // 设置比较器
        this.comparator = comparator;
        this.size = 0;
        this.maxLevel = 0;

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

    private boolean put(E data){
        // 从最底层中，找到其直接最接近的前驱节点
        Node<E> predecessorNode = findPredecessorNode(data);

        if(predecessorNode.data.equals(data)){
            // data匹配，已经存在，直接返回
            return true;
        }

        // 之前不存在，需要新插入节点
        Node<E> newNode = new Node<>(data);
        // 当前跳表元素个数+1
        this.size++;
        // 将新节点挂载至前驱节点之后
        predecessorNode.linkAsRight(newNode);
        int currentLevel = 0;

        Random random = new Random();

        Node<E> hasUpNodePredecessorNode = predecessorNode;
        Node<E> newNodeUpperNode = newNode;
        while (random.nextDouble() < PROMOTE_RATE) {
            // 当前插入的节点需要提升等级，在更高层插入索引节点
            if(currentLevel == this.maxLevel){
                promoteMaxLevel();
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

        return false;
    }


    private Node<E> findPredecessorNode(E data){
        // 从跳表头结点开始，从上层到下层逐步逼近
        Node<E> currentNode = head;

        while(true){
            Node<E> rightNode = currentNode.right;
            // 当前遍历节点的右节点不是右哨兵，且data >= 右节点data
            while (rightNode.nodeType != NodeType.RIGHT_SENTINEL && comparator.compare(data,rightNode.data) >= 0){
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
    private void promoteMaxLevel(){
        // 最大层数+1
        this.maxLevel++;

        // 当前最高曾左、右哨兵节点
        Node<E> upperLeftSentinel = new Node<>();
        Node<E> upperRightSentinel = new Node<>();

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
        }

        private Node() {
        }

        /**
         * 将一个节点作为"当前节点"的"左节点" 插入链表
         * @param node  需要插入的节点
         * */
        private void linkAsLeft(Node<T> node){
            //:::先设置新增节点的 左右节点
            node.left = this.left;
            node.right = this;

            //:::将新增节点插入 当前节点和当前节点的左节点之间
            this.left.right = node;
            this.left = node;
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
            node.right.left = node;
            node.right = node;
        }
    }
}
