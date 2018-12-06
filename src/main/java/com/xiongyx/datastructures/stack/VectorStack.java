package com.xiongyx.datastructures.stack;

import com.xiongyx.datastructures.exception.CollectionEmptyException;
import com.xiongyx.datastructures.iterator.Iterator;
import com.xiongyx.datastructures.list.ArrayList;

/**
 * 向量为基础实现的 栈结构
 * */
public class VectorStack <E> implements Stack<E>{

    /**
     * 内部向量
     * */
    private ArrayList<E> innerArrayList;

    /**
     * 默认构造方法
     * */
    public VectorStack() {
        this.innerArrayList = new ArrayList<>();
    }

    /**
     * 构造方法,确定初始化时的内部向量大小
     * */
    public VectorStack(int initSize) {
        this.innerArrayList = new ArrayList<>(initSize);
    }

    @Override
    public boolean push(E e) {
        //:::将新元素插入内部向量末尾(入栈)
        innerArrayList.add(e);

        return true;
    }

    @Override
    public E pop() {
        if(this.isEmpty()){
            throw new CollectionEmptyException("Stack already empty");
        }

        //:::内部向量末尾下标
        int lastIndex = innerArrayList.size() - 1;

        //:::将向量末尾处元素删除并返回(出栈)
        return innerArrayList.remove(lastIndex);
    }

    @Override
    public E peek() {
        if(this.isEmpty()){
            throw new CollectionEmptyException("Stack already empty");
        }

        //:::内部向量末尾下标
        int lastIndex = innerArrayList.size() - 1;

        //:::返回向量末尾处元素(窥视)
        return innerArrayList.get(lastIndex);
    }

    @Override
    public int size() {
        return innerArrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return innerArrayList.isEmpty();
    }

    @Override
    public void clear() {
        innerArrayList.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return innerArrayList.iterator();
    }

    @Override
    public String toString() {
        return innerArrayList.toString();
    }
}
