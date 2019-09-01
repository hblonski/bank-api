package com.bank.exception;

public class TransactionException extends Exception {
    static final long serialVersionUID = -3387516993334229948L;

    public TransactionException(String message) {
        super(message);
    }
}
