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
    private static final int EXPAND_BASE = 2;

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

    /**
     * 默认构造方法
     * */
    public ArrayDeque(int initCapacity) {
        assert initCapacity > 0;

        //:::设置数组大小为默认
        this.elements = new Object[initCapacity];

        //:::初始化队列 头部,尾部下标
        this.head = 0;
        this.tail = 0;
    }

    //===============================================内部辅助方法===================================================
    /**
     * 快速取模
     * */
    private int getMod(int logicIndex){
        int innerArrayLength = this.elements.length;

        while(logicIndex < 0){
            logicIndex += innerArrayLength;
        }

        while(logicIndex >= innerArrayLength){
            logicIndex -= innerArrayLength;
        }

        //:::获得真实下标
        return logicIndex;
    }

    /**
     * 内部数组扩容
     * */
    private void expand(){
        //:::内部数组 扩容两倍
        int elementsLength = this.elements.length;
        Object[] newElements = new Object[elementsLength * EXPAND_BASE];

        //:::将"head -> 数组尾部"的元素 复制在新数组的前面
        for(int i=this.head, j=0; i<elementsLength; i++,j++){
            newElements[j] = this.elements[i];
        }

        //:::将"0 -> head"的元素 复制在新数组的后面
        for(int i=0, j=elementsLength-this.head; i<this.head; i++,j++){
            newElements[j] = this.elements[i];
        }

        //:::初始化head,tail下标
        this.head = 0;
        this.tail = this.elements.length;

        //:::内部数组指向 新扩容的数组
        this.elements = newElements;
    }

    //=================================================外部接口=======================================================

    @Override
    public void addHead(E e) {
        //:::头部插入元素 head下标前移一位
        this.head = getMod(this.head - 1);
        //:::存放新插入的元素
        this.elements[this.head] = e;

        //:::判断当前队列大小 是否到达临界点
        if(head == tail){
            //:::内部数组扩容
            expand();
        }
    }

    @Override
    public void addTail(E e) {
        //:::存放新插入的元素
        this.elements[this.tail] = e;
        //:::尾部插入元素 tail下标后移一位
        this.tail = getMod(this.tail + 1);

        //:::判断当前队列大小 是否到达临界点
        if(head == tail){
            //:::内部数组扩容
            expand();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E removeHead() {
        //:::暂存需要被删除的数据
        E dataNeedRemove = (E)this.elements[this.head];
        //:::将当前头部元素引用释放
        this.elements[this.head] = null;

        //:::头部下标 后移一位
        this.head = getMod(this.head + 1);

        return dataNeedRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E removeTail() {
        //:::获得尾部元素下标(前移一位)
        int lastIndex = getMod(this.tail - 1);
        //:::暂存需要被删除的数据
        E dataNeedRemove = (E)this.elements[lastIndex];

        //:::设置尾部下标
        this.tail = lastIndex;

        return dataNeedRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peekHead() {
        return (E)this.elements[this.head];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peekTail() {
        //:::获得尾部元素下标(前移一位)
        int lastIndex = getMod(this.tail - 1);

        return (E)this.elements[lastIndex];
    }

    @Override
    public int size() {
        return getMod(tail - head);
    }

    @Override
    public boolean isEmpty() {
        //:::当且仅当 头尾下标相等时 队列为空
        return (head == tail);
    }

    @Override
    public void clear() {
        int h = this.head;
        int t = this.tail;

        while(h != t){
            int modHead = getMod(h);
            this.elements[modHead] = null;
            h = getMod(h + 1);
        }

        this.head = 0;
        this.tail = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
