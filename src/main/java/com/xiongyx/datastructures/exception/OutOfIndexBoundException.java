package com.xiongyx.datastructures.exception;

/**
 * 下标越界 异常
 * */
public class OutOfIndexBoundException extends RuntimeException{

    public OutOfIndexBoundException() {
    }

    public OutOfIndexBoundException(String message) {
        super(message);
    }
}
