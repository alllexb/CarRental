package ua.kiev.allexb.carrental.controller.validator;

/**
 * @author allexb
 * @version 1.0 01.09.2016
 */
public abstract class Validator<T> {
    protected T model;
    protected StringBuffer errorMessage;

    public Validator(T model) {
        this.model = model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public StringBuffer getErrorMessage() {
        return errorMessage;
    }

    public abstract boolean isValid();

}
