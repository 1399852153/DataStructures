package com.xiongyx.datastructures.list;

import com.xiongyx.datastructures.exception.IteratorStateErrorException;
import com.xiongyx.datastructures.exception.NegativeOfIndexException;
import com.xiongyx.datastructures.exception.OutOfIndexBoundException;
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

    //=================================================构造方法======================================================

    public LinkedList() {
        this.first = new Node<>();
        this.last = new Node<>();

        //:::将首尾哨兵节点 进行连接
        this.first.right = this.last;
        this.last.left = this.first;

        //:::初始化size
        this.size = 0;
    }

    //===============================================内部辅助方法===================================================
    /**
     * 插入时,下标越界检查
     * @param index 下标值
     */
    private void rangeCheckForAdd(int index){
        //:::如果大于size的值,抛出异常
        //:::注意：插入时,允许插入线性表的末尾,因此(index == size)是合法的
        if(index > this.size){
            throw new OutOfIndexBoundException("index error  index=" + index + " size=" + this.size) ;
        }

        if(index < 0){
            throw new NegativeOfIndexException("index error  index=" + index + " size=" + this.size) ;
        }
    }

    /**
     * 下标越界检查
     * @param index 下标值
     */
    private void rangeCheck(int index){
        //:::如果大于等于size的值,抛出异常
        if(index >= this.size){
            throw new OutOfIndexBoundException("index error  index=" + index + " size=" + this.size) ;
        }

        if(index < 0){
            throw new NegativeOfIndexException("index error  index=" + index + " size=" + this.size) ;
        }
    }

    /**
     * 返回下标对应的 内部节点
     * */
    private Node<E> find(int index){
        //:::要求调用该方法前,已经进行了下标越界校验
        //:::出于效率的考虑,不进行重复校验

        //:::判断下标在当前列表的大概位置（前半段 or 后半段）
        if(index < this.size/2){
            //:::下标位于前半段

            //:::从头部结点出发,进行遍历
            Node<E> currentNode = this.first.right;
            //:::遍历(index)次
            for(int i=0; i<index; i++){
                currentNode = currentNode.right;
            }

            return currentNode;
        }else{
            //:::下标位于后半段

            //:::从尾部结点出发,进行遍历
            Node<E> currentNode = this.last.left;
            //:::遍历(size-index)次
            for(int i=index; i<size; i++){
                currentNode = currentNode.left;
            }
            return currentNode;
        }
    }

    //=================================================外部接口=======================================================

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
        Node<E> currentNode = this.first;

        if(e != null){
            //:::如果不是查询空元素

            //:::遍历列表
            for(int i=0; i<this.size; i++){
                //:::不断切换当前节点 ==> "当前节点 = 当前节点的右节点"
                currentNode = currentNode.right;
                //:::如果满足要求（注意: equals顺序不能反,否则可能出现currentNode.data为空,出现空指针异常）
                if(e.equals(currentNode.data)){
                    //:::返回下标
                    return i;
                }
            }
        }else{
            //:::如果是查询null元素

            //:::遍历列表
            for(int i=0; i<this.size; i++){
                //:::不断切换当前节点 ==> "当前节点 = 当前节点的右节点"
                currentNode = currentNode.right;
                //:::如果满足要求
                if(currentNode.data == null){
                    //:::返回下标
                    return i;
                }
            }
        }

        //:::遍历列表未找到相等的元素,返回特殊值"-1"代表未找到
        return -1;
    }

    @Override
    public boolean contains(E e) {
        //:::复用indexOf方法,如果返回-1代表不存在;反之,则代表存在
        return (indexOf(e) != -1);
    }

    @Override
    public boolean add(E e) {
        //:::将新的数据 插入到列表末尾 ==> 作为last节点的前一个节点插入
        this.last.linkAsLeft(new Node<>(e));
        //:::size自增
        this.size++;

        return true;
    }

    @Override
    public void add(int index, E e) {
        //:::插入时,下标越界检查
        rangeCheckForAdd(index);

        //:::查询出下标对应的 目标节点
        Node<E> targetNode = find(index);

        //:::将新的数据节点 作为目标节点的前一个节点插入
        targetNode.linkAsLeft(new Node<>(e));

        //:::size自增
        this.size++;
    }

    @Override
    public boolean remove(E e) {
        //:::当前节点 = 列表头部哨兵
        Node<E> currentNode = this.first;

        if(e != null){
            //:::如果不是查询空元素

            //:::遍历列表
            for(int i=0; i<this.size; i++){
                //:::不断切换当前节点 ==> "当前节点 = 当前节点的右节点"
                currentNode = currentNode.right;
                //:::如果满足要求（注意: equals顺序不能反,否则可能出现currentNode.data为空,出现空指针异常）
                if(e.equals(currentNode.data)){
                    //:::匹配成功,将当前节点从链表中删除
                    currentNode.unlinkSelf();

                    //:::删除成功,返回true
                    return true;
                }
            }
        }else{
            //:::如果是查询null元素

            //:::遍历列表
            for(int i=0; i<this.size; i++){
                //:::不断切换当前节点 ==> "当前节点 = 当前节点的右节点"
                currentNode = currentNode.right;
                //:::如果满足要求
                if(currentNode.data == null){
                    //:::匹配成功,将当前节点从链表中删除
                    currentNode.unlinkSelf();

                    //:::删除成功,返回true
                    return true;
                }
            }
        }

        //:::遍历列表未找到相等的元素,未进行删除 返回false
        return false;
    }

    @Override
    public E remove(int index) {
        //:::下标越界检查
        rangeCheck(index);

        //:::获得下标对应的 Node
        Node<E> targetNode = find(index);

        //:::将目标节点从链表中移除
        targetNode.unlinkSelf();
        //:::size自减
        this.size--;

        //:::返回被移除的节点data值
        return targetNode.data;
    }

    @Override
    public E set(int index, E e) {
        //:::下标越界检查
        rangeCheck(index);

        //:::获得下标对应的 Node
        Node<E> targetNode = find(index);

        //:::先暂存要被替换的"data"
        E oldValue = targetNode.data;
        //:::将当前下标对应的"data"替换为"e"
        targetNode.data = e;

        //:::返回被替换的data
        return oldValue;
    }

    @Override
    public E get(int index) {
        //:::下标越界检查
        rangeCheck(index);

        //:::获得下标对应的 Node
        Node<E> targetNode = find(index);

        return targetNode.data;
    }

    @Override
    public void clear() {
        //:::当头部哨兵节点不和尾部哨兵节点直接相连时
        while(this.first.right != this.last){
            //:::删除掉头部哨兵节点后的节点
            remove(0);
        }

        //:::执行完毕后,代表链表被初始化,重置size
        this.size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        Iterator<E> iterator = this.iterator();

        //:::空列表
        if(!iterator.hasNext()){
            return "[]";
        }

        //:::列表起始使用"["
        StringBuilder s = new StringBuilder("[");

        //:::反复迭代
        while(true){
            //:::获得迭代的当前元素
            E data = iterator.next();

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

    //===============================================================内部类==========================================================
    /**
     * 链表内部节点类
     */
    private static class Node <T>{
        /**
         * 左边关联的节点引用
         * */
        Node<T> left;

        /**
         * 右边关联的节点引用
         * */
        Node<T> right;

        /**
         * 节点存储的数据
         * */
        T data;

        //===================================内部节点 构造函数==================================

        private Node() {}

        private Node(T data) {
            this.data = data;
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

        /**
         * 将"当前节点"移出链表
         * */
        private void unlinkSelf(){
            //:::令当前链表的 左节点和右节点建立关联
            this.left.right = this.right;
            //:::令当前链表的 右节点和左节点建立关联
            this.right.left = this.left;

            //:::这样,当前节点就从链表中被移除了(同时,作为私有的内部类,当前节点不会被其它对象引用,很快会被GC回收)
        }
    }

    /**
     * 链表迭代器实现
     * */
    private class Itr implements Iterator<E>{
        /**
         * 当前迭代器光标位置
         * 初始化指向 头部哨兵节点
         * */
        private Node<E> currentNode = LinkedList.this.first;
        /**
         * 最近一次迭代返回的数据
         * */
        private Node<E> lastReturned;

        @Override
        public boolean hasNext() {
            //:::判断当前节点的下一个节点 是否是 尾部哨兵节点
            return (this.currentNode.right != LinkedList.this.last);
        }

        @Override
        public E next() {
            //:::指向当前节点的下一个节点
            this.currentNode = this.currentNode.right;

            //:::设置最近一次返回的节点
            this.lastReturned = currentNode;

            //:::返回当前节点的data
            return this.currentNode.data;
        }

        @Override
        public void remove() {
            if(this.lastReturned == null){
                throw new IteratorStateErrorException("迭代器状态异常: 可能在一次迭代中进行了多次remove操作");
            }

            //:::当前光标指向的节点要被删除,先暂存引用
            Node<E> nodeWillRemove = this.currentNode;

            //:::由于当前节点需要被删除,因此光标前移,指向当前节点的上一个节点
            this.currentNode = this.currentNode.left;

            //:::移除操作需要将最近一次返回设置为null，防止多次remove
            this.lastReturned = null;

            //:::将节点从链表中移除
            nodeWillRemove.unlinkSelf();
        }
    }
}
