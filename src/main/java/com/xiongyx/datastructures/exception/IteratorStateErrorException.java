package com.xiongyx.datastructures.exception;

/**
 * 迭代器状态错误 异常
 * */
public class IteratorStateErrorException extends RuntimeException{

    public IteratorStateErrorException() {
    }

    public IteratorStateErrorException(String message) {
        super(message);
    }
}
