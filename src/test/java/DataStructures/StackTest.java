package DataStructures;

import com.xiongyx.datastructures.stack.Stack;
import com.xiongyx.datastructures.stack.VectorStack;

public class StackTest {

    public static void main(String[] args){
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

    private static void show(Object obj){
        System.out.println(obj);
    }
}
