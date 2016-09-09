package ua.kiev.allexb.carrental.controller.validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author allexb
 * @version 1.0 01.09.2016
 */
public abstract class Validator<T> {
    protected HttpServletRequest request;
    protected String message = "";

    public Validator(HttpServletRequest request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void addMessage(String massage) {
        this.message += massage;
    }

    public abstract boolean validate();
    public abstract T getValue();
}
