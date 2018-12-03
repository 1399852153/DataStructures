package com.xiongyx.datastructures.stack;

/**
 * 括号匹配 工具类
 * */
public final class ParenthesisMatchingUtil {

    /**
     * 左 小括号
     * */
    private static final char PARENTHESES_OPEN = '(';
    /**
     * 右 小括号
     * */
    private static final char PARENTHESES_CLOSE = ')';

    /**
     * 左 中括号
     * */
    private static final char BRACKET_OPEN = '[';
    /**
     * 右 中括号
     * */
    private static final char BRACKET_CLOSE = ']';

    /**
     * 左 大括号
     * */
    private static final char BRACES_OPEN = '{';
    /**
     * 右 右括号
     * */
    private static final char BRACES_CLOSE = '}';

    /**
     * 判断当前字符是否是 左括号
     * */
    private static boolean isParenthesisOpen(char target){
        return (target == PARENTHESES_OPEN) || (target == BRACKET_OPEN) || (target == BRACES_OPEN);
    }

    /**
     * 判断当前字符是否是 右括号
     * */
    private static boolean isParenthesisClose(char target){
        return (target == PARENTHESES_CLOSE) || (target == BRACKET_CLOSE) || (target == BRACES_CLOSE);
    }

    /**
     * 判断左右括号是否匹配
     * */
    private static boolean isMatch(char left, char right){
        return (left == PARENTHESES_OPEN && right == PARENTHESES_CLOSE) ||
                (left == BRACKET_OPEN && right == BRACKET_CLOSE) ||
                (left == BRACES_OPEN && right == BRACES_CLOSE);
    }

    /**
     * 判断表达式中的括号是否匹配
     * @param expression 表达式
     * */
    public static boolean bracketsMatch(String expression){
        Stack<Character> stack = new LinkedListStack<>();

        char[] expressionChars = expression.toCharArray();
        for(char targetChar : expressionChars){
            if(isParenthesisOpen(targetChar)){
                stack.push(targetChar);
            }else if(isParenthesisClose(targetChar)){
                if(stack.isEmpty()){
                    return false;
                }

                char leftParenthesis = stack.peek();
                if(isMatch(leftParenthesis,targetChar)){
                    stack.pop();
                }else{
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
