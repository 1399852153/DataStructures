package com.xiongyx.datastructures.list;

/**
 * @Author xiongyx
 * on 2018/11/25.
 *
 * 列表---链表实现
 */
public class LinkedList <T> {

    /**
     * 链表内部节点
     */
    private static class Node <T>{
        /**
         * 左边关联的节点引用
         * */
        Node left;

        /**
         * 右边关联的节点引用
         * */
        Node right;

        /**
         * 节点存储的数据
         * */
        T data;

        //===================================内部节点 构造函数==================================

        public Node() {}

        public Node(Node left, Node right, T data) {
            this.left = left;
            this.right = right;
            this.data = data;
        }

        /**
         * 将一个节点作为"当前节点"的"左节点" 插入链表
         * @param node  需要插入的节点
         * */
        public void linkAsLeft(Node node){
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
        public void linkAsRight(Node node){
            //:::先设置新增节点的 左右节点
            node.left = this;
            node.right = this.right;

            //:::将新增节点插入 当前节点和当前节点的左节点之间
            node.right.left = node;
            node.right = node;
        }

        /**
         * 将"当前节点"移出链表
         * @return 返回当前节点
         * */
        public Node unlinkSelf(){
            this.left.right = this.right;
            this.right.left = this.left;
            return this;
        }
    }
}
