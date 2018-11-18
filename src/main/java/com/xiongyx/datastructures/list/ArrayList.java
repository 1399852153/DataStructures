package com.xiongyx.datastructures.list;

/**
 * @Author xiongyx
 * on 2018/11/17.
 *
 * 列表接口 线性表实现
 */
public class ArrayList <E> implements List <E>{

    //===============================================成员变量======================================================
    /**
     * 内部封装的数组
     */
    private Object[] elements;

    /**
     * 线性表默认的容量大小
     * */
    protected static final int DEFAULT_CAPACITY = 16;

    /**
     * 内部数组的实际大小
     * */
    private int capacity;

    /**
     * 当前线性表的实际大小
     * */
    private int size;

    //=================================================构造方法======================================================
    /**
     * 默认的无参构造方法
     * */
    public ArrayList() {
        this.capacity = DEFAULT_CAPACITY;
        size = 0;
        //:::设置数组大小为默认
        elements = new Object[capacity];
    }

    /**
     * 设置内部数组初始大小的构造方法
     * @param capacity 内部数组初始大小
     * */
    public ArrayList(int capacity) {
        this.capacity = capacity;
        size = 0;
        //:::设置数组大小
        elements = new Object[capacity];
    }

    //===============================================内部辅助方法===================================================

    //=================================================外部接口=======================================================

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public void add(int index, Object o) {

    }

    @Override
    public boolean remove(Object obj) {
        return false;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public E set(int index, Object o) {
        return null;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public int indexOf(Object obj) {
        return 0;
    }
}
