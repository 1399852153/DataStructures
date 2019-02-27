package com.xiongyx.datastructures.queue;

import com.xiongyx.datastructures.exception.CollectionEmptyException;
import com.xiongyx.datastructures.functioninterface.Comparator;
import com.xiongyx.datastructures.iterator.Iterator;
import com.xiongyx.datastructures.list.ArrayList;

/**
 * @Author xiongyx
 * @Date 2019/2/18
 *
 * 完全二叉堆 实现优先级队列
 */
public class CompleteBinaryHeap<E> implements PriorityQueue<E>{

    // =========================================成员属性===========================================
    /**
     * 内部向量
     * */
    private ArrayList<E> innerArrayList;

    /**
     * 比较逻辑
     * */
    private final Comparator<E> comparator;

    /**
     * 当前堆的逻辑大小
     * */
    private int size;

    // ===========================================构造函数========================================
    /**
     * 无参构造函数
     * */
    public CompleteBinaryHeap() {
        this.innerArrayList = new ArrayList<>();
        this.comparator = null;
    }

    /**
     * 指定初始容量的构造函数
     * @param defaultCapacity 指定的初始容量
     * */
    public CompleteBinaryHeap(int defaultCapacity){
        this.innerArrayList = new ArrayList<>(defaultCapacity);
        this.comparator = null;
    }

    /**
     * 指定初始容量的构造函数
     * @param comparator 指定的比较器逻辑
     * */
    public CompleteBinaryHeap(Comparator<E> comparator){
        this.innerArrayList = new ArrayList<>();
        this.comparator = comparator;
    }

    /**
     * 指定初始容量和比较器的构造函数
     * @param defaultCapacity 指定的初始容量
     * @param comparator 指定的比较器逻辑
     * */
    public CompleteBinaryHeap(int defaultCapacity, Comparator<E> comparator) {
        this.innerArrayList = new ArrayList<>(defaultCapacity);
        this.comparator = comparator;
    }

    /**
     * 将指定数组 转换为一个完全二叉堆
     * @param array 指定的数组
     * */
    public CompleteBinaryHeap(E[] array){
        this.innerArrayList = new ArrayList<>(array);
        this.comparator = null;

        this.size = array.length;

        // 批量建堆
        heapify();
    }

    /**
     * 将指定数组 转换为一个完全二叉堆
     * @param array 指定的数组
     * @param comparator 指定的比较器逻辑
     * */
    public CompleteBinaryHeap(E[] array, Comparator<E> comparator){
        this.innerArrayList = new ArrayList<>(array);
        this.comparator = comparator;

        // 批量建堆
        heapify();
    }

    // ==========================================外部方法===========================================

    @Override
    public void insert(E newData) {
        // 先插入新数据到 向量末尾
        this.innerArrayList.add(newData);

        // 获得向量末尾元素下标
        int lastIndex = getLastIndex();
        // 对向量末尾元素进行上滤，以恢复堆序性
        siftUp(lastIndex);
    }

    @Override
    public E peekMax() {
        // 内部数组第0位 即为堆顶max
        return this.innerArrayList.get(0);
    }

    @Override
    public E popMax() {
        if(this.innerArrayList.isEmpty()){
            throw new CollectionEmptyException("当前完全二叉堆为空");
        }

        // 将当前向量末尾的元素和堆顶元素交换位置
        int lastIndex = getLastIndex();
        swap(0,lastIndex);

        // 暂存被删除的最大元素（之前的堆顶最大元素被放到了向量末尾）
        E max = this.innerArrayList.get(lastIndex);
        this.size--;

        // 对当前堆顶元素进行下滤，以恢复堆序性
        siftDown(0);

        return max;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public String toString() {
        //:::空列表
        if(this.isEmpty()){
            return "[]";
        }

        //:::列表起始使用"["
        StringBuilder s = new StringBuilder("[");

        //:::从第一个到倒数第二个元素之间
        for(int i=0; i<size-1; i++){
            //:::使用", "进行分割
            s.append(this.innerArrayList.get(i)).append(",").append(" ");
        }

        //:::最后一个元素使用"]"结尾
        s.append(this.innerArrayList.get(size-1)).append("]");
        return s.toString();
    }

    public static <T> void heapSort(T[] array){
        CompleteBinaryHeap<T> completeBinaryHeap = new CompleteBinaryHeap<>(array);

        for(int i=array.length-1; i>=0; i--){
            array[i] = completeBinaryHeap.popMax();
        }
    }

    // =========================================内部辅助函数===========================================
    /**
     * 上滤操作
     * @param index 需要上滤的元素下标
     * */
    private void siftUp(int index){
        while(index >= 0){
            // 获得当前节点
            int parentIndex = getParentIndex(index);

            E currentData = this.innerArrayList.get(index);
            E parentData = this.innerArrayList.get(parentIndex);

            // 如果当前元素 大于 双亲元素
            if(compare(currentData,parentData) > 0){
                // 交换当前元素和双亲元素的位置
                swap(index,parentIndex);

                // 继续向上迭代
                index = parentIndex;
            }else{
                // 当前元素没有违反堆序性，直接返回
                return;
            }
        }
    }

    /**
     * 下滤操作
     * @param index 需要下滤的元素下标
     * */
    private void siftDown(int index){
        int size = this.size();
        // 叶子节点不需要下滤
        int half = size >>> 1;

        while(index < half){
            int leftIndex = getLeftChildIndex(index);
            int rightIndex = getRightChildIndex(index);

            if(rightIndex < size){
                // 右孩子存在 (下标没有越界)

                E leftData = this.innerArrayList.get(leftIndex);
                E rightData = this.innerArrayList.get(rightIndex);
                E currentData = this.innerArrayList.get(index);

                // 比较左右孩子大小
                if(compare(leftData,rightData) >= 0){
                    // 左孩子更大，比较双亲和左孩子
                    if(compare(currentData,leftData) >= 0){
                        // 双亲最大，终止下滤
                        return;
                    }else{
                        // 三者中，左孩子更大，交换双亲和左孩子的位置
                        swap(index,leftIndex);
                        // 继续下滤操作
                        index = leftIndex;
                    }
                }else{
                    // 右孩子更大，比较双亲和右孩子
                    if(compare(currentData,rightData) >= 0){
                        // 双亲最大，终止下滤
                        return;
                    }else{
                        // 三者中，右孩子更大，交换双亲和右孩子的位置
                        swap(index,rightIndex);
                        // 继续下滤操作
                        index = rightIndex;
                    }
                }
            }else{
                // 右孩子不存在 (下标越界)

                E leftData = this.innerArrayList.get(leftIndex);
                E currentData = this.innerArrayList.get(index);

                // 当前节点 大于 左孩子
                if(compare(currentData,leftData) >= 0){
                    // 终止下滤
                    return;
                }else{
                    // 交换 左孩子和双亲的位置
                    swap(index,leftIndex);
                    // 继续下滤操作
                    index = leftIndex;
                }
            }
        }
    }

    /**
     * 批量建堆（将内部数组转换为完全二叉堆）
     * */
    private void heapify(){
        // 获取下标最大的 内部非叶子节点
        int lastInternalIndex = getLastInternal();

        // Floyd建堆算法 时间复杂度"O(n)"
        // 从lastInternalIndex开始向前遍历，对每一个元素进行下滤操作，从小到大依次合并
        for(int i=lastInternalIndex; i>=0; i--){
            siftDown(i);
        }
    }

    /**
     * 获得逻辑上 双亲节点下标
     * @param currentIndex 当前下标
     * */
    private int getParentIndex(int currentIndex){
        return (currentIndex - 1)/2;
    }

    /**
     * 获得逻辑上 左孩子节点下标
     * @param currentIndex 当前下标
     * */
    private int getLeftChildIndex(int currentIndex){
        return (currentIndex * 2) + 1;
    }

    /**
     * 获得逻辑上 右孩子节点下标
     * @param currentIndex 当前下标
     * */
    private int getRightChildIndex(int currentIndex){
        return (currentIndex + 1) * 2;
    }

    /**
     * 获得当前向量末尾下标
     * */
    private int getLastIndex(){
        return this.size - 1;
    }

    /**
     * 获得最后一个非叶子节点下标
     * */
    private int getLastInternal(){
        return (this.size()/2) - 1;
    }

    /**
     * 交换向量中两个元素位置
     * @param a 某一个元素的下标
     * @param b 另一个元素的下标
     * */
    private void swap(int a, int b){
        // 现暂存a、b下标元素的值
        E aData = this.innerArrayList.get(a);
        E bData = this.innerArrayList.get(b);

        // 交换位置
        this.innerArrayList.set(a,bData);
        this.innerArrayList.set(b,aData);
    }

    /**
     * 进行比较
     * */
    @SuppressWarnings("unchecked")
    private int compare(E t1, E t2){
        // 迭代器不存在
        if(this.comparator == null){
            // 依赖对象本身的 Comparable，可能会转型失败
            return ((Comparable) t1).compareTo(t2);
        }else{
            // 通过迭代器逻辑进行比较
            return this.comparator.compare(t1,t2);
        }
    }
}
