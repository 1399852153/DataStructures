package com.xiongyx.datastructures.exception;

/**
 * 下标负数 异常
 * */
public class NegativeOfIndexException extends RuntimeException{

    public NegativeOfIndexException() {
    }

    public NegativeOfIndexException(String message) {
        super(message);
    }
}
