package com.xiongyx.datastructures.list;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * @Author xiongyx
 * on 2018/11/25.
 *
 * 列表---链表实现
 */
public class LinkedList <E> implements List <E>{
    /**
     * 链表 头部哨兵节点
     * */
    private Node<E> first;
    /**
     * 链表 尾部哨兵节点
     * */
    private Node<E> last;
    /**
     * 链表 逻辑大小
     * */
    private int size;

    public LinkedList() {
        first = new Node<>();
        last = new Node<>();
        size = 0;
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
    public int indexOf(E e) {
        //:::当前节点 = 列表头部哨兵
        Node currentNode = this.first;

        if(e != null){
            //:::如果不是查询空元素

            //:::遍历列表
            for(int i=0; i<this.size; i++){
                //:::不断切换当前节点 ==> 当前节点 = 当前节点的右节点
                currentNode = currentNode.right;
                //:::如果满足要求（注意: equals顺序不能反，否则可能出现空指针异常）
                if(e.equals(currentNode.data)){
                    //:::返回下标
                    return i;
                }
            }
        }else{
            //:::如果是查询null元素

            //:::遍历列表
            for(int i=0; i<this.size; i++){
                //:::不断切换当前节点 ==> 当前节点 = 当前节点的右节点
                currentNode = currentNode.right;
                //:::如果满足要求
                if(currentNode.data == null){
                    //:::返回下标
                    return i;
                }
            }
        }

        //:::遍历列表未找到相等的元素，返回特殊值"-1"代表未找到
        return -1;
    }

    @Override
    public boolean contains(E e) {
        //:::复用indexOf方法,如果返回-1代表不存在;反之，则代表存在
        return (indexOf(e) != -1);
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public void add(int index, E e) {

    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public E set(int index, E e) {
        return null;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    /**
     * 链表内部节点
     */
    private static class Node <E>{
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
        E data;

        //===================================内部节点 构造函数==================================

        public Node() {}

        public Node(Node left, Node right, E data) {
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
