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
        Stack<Character> stack = new VectorStack<>();

        // 将字符串转换为字符数组，进行遍历
        char[] expressionChars = expression.toCharArray();
        for(char targetChar : expressionChars){
            // 如果当前字符是 左括号
            if(isParenthesisOpen(targetChar)){
                // 左括号 压入栈中
                stack.push(targetChar);

                // 如果是右括号
            }else if(isParenthesisClose(targetChar)){
                // 如果当前栈为空
                if(stack.isEmpty()){
                    // 左括号少于右括号 (校验失败)
                    return false;
                }

                // 查看栈顶左括号
                char leftParenthesis = stack.peek();
                // "栈顶左括号" 和 "当前右括号" 匹配
                if(isMatch(leftParenthesis,targetChar)){
                    // 栈顶左括号出栈
                    stack.pop();
                }else{
                    // 左右括号类型不匹配 (校验失败)
                    return false;
                }
            }else{
                // 其它字符不进行处理
            }
        }

        // 遍历结束
        if(stack.isEmpty()){
            // 如果栈为空,说明括号完全匹配 (校验成功)
            return true;
        }else{
            // 如果栈不为空，左括号多于右括号 (校验失败)
            return false;
        }
    }

    public static void main(String[] args){
        String expression1 = "{3 * [2 + 10 /(1 + 1)]}";
        bracketsMatch(expression1);

        String expression2 = "(]{})";
        bracketsMatch(expression2);
    }
}
