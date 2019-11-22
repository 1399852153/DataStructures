package DataStructures.queue;

import com.xiongyx.datastructures.queue.ArrayDeque;
import com.xiongyx.datastructures.queue.Deque;
import java.util.ArrayList;
import java.util.List;

public class QueueTest {

    public static void main(String[] args){
        List<Integer> list = new ArrayList();
        list.stream().count();

        testQueue();
    }

    private static void testQueue(){
        Deque<Integer> deque = new ArrayDeque<>(3);
        deque.addHead(1);
        deque.addHead(2);
        deque.addHead(3);
        deque.addHead(4);
        deque.addHead(5);
        deque.addHead(6);
        deque.addHead(7);
        deque.addTail(101);
        deque.addTail(102);
        deque.addTail(103);
        deque.addTail(104);
        deque.addTail(105);
        deque.addTail(106);

        System.out.println(deque.isEmpty());
        System.out.println(deque.peekHead());
        System.out.println(deque.peekTail());
        System.out.println(deque.removeHead());
        System.out.println(deque.removeHead());
        System.out.println(deque.removeHead());
        System.out.println(deque.removeTail());

        System.out.println(deque.peekHead());
        System.out.println(deque.peekTail());

        deque.clear();
        System.out.println(deque.isEmpty());
    }
}
