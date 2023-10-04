package com.wooin.hahahaback.common.exception;

public class NoAuthorizedException extends RuntimeException{
    public NoAuthorizedException(String message) {
        super(message);
    }
}
