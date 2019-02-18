package DataStructures.queue;

import DataStructures.util.CommonUtil;
import com.xiongyx.datastructures.queue.CompleteBinaryHeap;
import com.xiongyx.datastructures.queue.PriorityQueue;

/**
 * @Author xiongyx
 * @Date 2019/2/18
 */
public class PriorityQueueTest {
    public static void main(String[] args){
//        Integer[] array = {1,2,3,4,5,6,7,8,9,10};
//
//        PriorityQueue<Integer> priorityQueue = new CompleteBinaryHeap<>(array);
//
//        CommonUtil.show(priorityQueue);

        PriorityQueue<Integer> priorityQueue = new CompleteBinaryHeap<>();
        for(int i=0; i<10; i++){
            priorityQueue.insert(i);
        }

        CommonUtil.show(priorityQueue);

    }
}
