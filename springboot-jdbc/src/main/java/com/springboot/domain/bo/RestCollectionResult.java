package com.springboot.domain.bo;

import java.util.Collection;

/**
 * 集合对象返回结果
 */
public class RestCollectionResult<T> {

    private String result;
    private String message;
    private Collection<T> items;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }
}
