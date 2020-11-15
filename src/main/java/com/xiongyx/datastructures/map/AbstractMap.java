package com.xiongyx.datastructures.map;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * @author xiongyx
 * on 2020/11/15.
 */
public abstract class AbstractMap<K,V> implements Map<K,V>{

    @Override
    public String toString() {
        Iterator<EntryNode<K,V>> iterator = this.iterator();

        // 空容器
        if(!iterator.hasNext()){
            return "[]";
        }

        // 容器起始使用"["
        StringBuilder s = new StringBuilder("[");

        // 反复迭代
        while(true){
            // 获得迭代的当前元素
            Map.EntryNode<K,V> data = iterator.next();

            // 判断当前元素是否是最后一个元素
            if(!iterator.hasNext()){
                // 是最后一个元素，用"]"收尾
                s.append(data).append("]");
                // 返回 拼接完毕的字符串
                return s.toString();
            }else{
                // 不是最后一个元素
                // 使用", "分割，拼接到后面
                s.append(data).append(", ");
            }
        }
    }
}
