package com.gorent.api.util.search;

public class SearchCriteria {

    private String key;
    private Object value;
    private SearchType operation;

    public SearchCriteria(String key, Object value, SearchType operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchType getOperation() {
        return operation;
    }

    public void setOperation(SearchType operation) {
        this.operation = operation;
    }


}
