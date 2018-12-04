package com.xiongyx.datastructures.exception;

/**
 * @Author xiongyx
 * on 2018/12/3.
 */
public class CollectionEmptyException extends RuntimeException{

    public CollectionEmptyException() {
    }

    public CollectionEmptyException(String message) {
        super(message);
    }
}
