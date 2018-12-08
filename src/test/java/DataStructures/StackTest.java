package DataStructures;

import com.xiongyx.datastructures.queue.ArrayDeque;
import com.xiongyx.datastructures.queue.Deque;
import com.xiongyx.datastructures.stack.ParenthesisMatchingUtil;
import com.xiongyx.datastructures.stack.Stack;
import com.xiongyx.datastructures.stack.VectorStack;

public class StackTest {

    public static void main(String[] args){
//        testParenthesisMatching();

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

    private static void testStack(){
        Stack<String> stack = new VectorStack<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        stack.push("5");
        show(stack.isEmpty());

        show(stack.size());
        show(stack);

        show(stack.peek());
        show(stack);

        show(stack.pop());
        show(stack.pop());
        show(stack);

        stack.clear();
        show(stack);
        show(stack.isEmpty());
    }

    private static void testParenthesisMatching(){
        String expressions = "{10 / [3 * (1+2)]}";
        boolean isMatch = ParenthesisMatchingUtil.bracketsMatch(expressions);
        show("isMatch=" + isMatch);
    }

    private static void show(Object obj){
        System.out.println(obj);
    }
}
