package com.minnow.ex;

public class BusinessException extends RuntimeException {
    public BusinessException(String massage) {
        super(massage);
    }
}
