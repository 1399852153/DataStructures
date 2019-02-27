package DataStructures.queue;

import DataStructures.util.CommonUtil;
import com.xiongyx.datastructures.queue.CompleteBinaryHeap;
import com.xiongyx.datastructures.queue.PriorityQueue;
import java.util.Arrays;

/**
 * @Author xiongyx
 * @Date 2019/2/18
 */
public class PriorityQueueTest {
    public static void main(String[] args){
        Integer[] array = {1001,2,3,2324,5,6,7,8,9,10,11,155};

//        testJDKCompleteBinaryHeap(array);
//
//        CommonUtil.printLine(2);
//
//        testCompleteBinaryHeap(array);

        CompleteBinaryHeap.heapSort(array);
        CommonUtil.show(array);
    }

    private static void testJDKCompleteBinaryHeap(Integer[] array){
        java.util.PriorityQueue<Integer> priorityQueue = new java.util.PriorityQueue<>((o1, o2) -> -(o1.compareTo(o2)));
        priorityQueue.addAll(Arrays.asList(array));
        CommonUtil.show("jdk:" + priorityQueue);

//        for(int i=0; i<array.length; i++){
//            CommonUtil.show(priorityQueue.poll());
//        }
    }

    private static void testCompleteBinaryHeap(Integer[] array){
        PriorityQueue<Integer> priorityQueue = new CompleteBinaryHeap<>(array);
        CommonUtil.show("my:" + priorityQueue);
    }
}
