package DataStructures.stack;

import com.xiongyx.datastructures.stack.ParenthesisMatchingUtil;
import com.xiongyx.datastructures.stack.Stack;
import com.xiongyx.datastructures.stack.VectorStack;

public class StackTest {

    public static void main(String[] args){
        testParenthesisMatching();
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
