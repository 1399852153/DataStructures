package com.xiongyx.datastructures.queue;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * 基于数组的 双端队列
 * */
public class ArrayDeque<E> implements Deque<E>{

    //===============================================成员变量======================================================
    /**
     * 内部封装的数组
     * */
    private Object[] elements;

    /**
     * 队列默认的容量大小
     * */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * 扩容翻倍的基数
     * */
    private static final double EXPAND_BASE = 2;

    /**
     * 队列头部下标
     * */
    private int head;

    /**
     * 队列尾部下标
     * */
    private int tail;

    //=================================================构造方法======================================================
    /**
     * 默认构造方法
     * */
    public ArrayDeque() {
        //:::设置数组大小为默认
        this.elements = new Object[DEFAULT_CAPACITY];

        //:::初始化队列 头部,尾部下标
        this.head = 0;
        this.tail = 0;
    }
    //===============================================内部辅助方法===================================================


    //=================================================外部接口=======================================================

    @Override
    public void addHead(E e) {

    }

    @Override
    public void addTail(E e) {

    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeTail() {
        return null;
    }

    @Override
    public E peekHead() {
        return null;
    }

    @Override
    public E peekTail() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
