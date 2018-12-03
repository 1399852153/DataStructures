package com.xiongyx.datastructures.stack;

import com.xiongyx.datastructures.list.LinkedList;

/**
 * 链表为基础实现的 栈结构
 * */
public class LinkedListStack<E> implements Stack<E>{

    /**
     * 内部链表
     * */
    private LinkedList<E> innerLinkedList;

    /**
     * 默认构造方法
     * */
    public LinkedListStack() {
        this.innerLinkedList = new LinkedList<>();
    }

    @Override
    public boolean push(E e) {
        //:::将新元素插入内部链表末尾(入栈)
        innerLinkedList.add(e);

        return true;
    }

    @Override
    public E pop() {
        //:::内部链表末尾下标
        int lastIndex = innerLinkedList.size() - 1;

        //:::将链表末尾处元素删除并返回(出栈)
        return innerLinkedList.remove(lastIndex);
    }

    @Override
    public E peek() {
        //:::内部链表末尾下标
        int lastIndex = innerLinkedList.size() - 1;

        //:::返回链表末尾处元素(窥视)
        return innerLinkedList.get(lastIndex);
    }

    @Override
    public int size() {
        return innerLinkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return innerLinkedList.isEmpty();
    }

    @Override
    public void clear() {
        innerLinkedList.clear();
    }
}
