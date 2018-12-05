package com.xiongyx.datastructures.stack;

/**
 * 栈ADT 接口定义
 * */
public interface Stack<E>{

    /**
     * 将一个元素 加入栈顶
     * @param  e 需要插入的元素
     * @return 是否插入成功
     * */
    boolean push(E e);

    /**
     * 返回栈顶元素，并且将其从栈中移除(弹出)
     * @return 当前栈顶元素
     * */
    E pop();

    /**
     * 返回栈顶元素，不将其从栈中移除(窥视)
     * @return 当前栈顶元素
     * */
    E peek();

    /**
     * @return 返回当前列表中元素的个数
     */
    int size();

    /**
     * 判断当前列表是否为空
     * @return 如果当前列表中元素个数为0，返回true；否则，返回false
     */
    boolean isEmpty();

    /**
     * 清除栈中所有元素
     * */
    void clear();
}
