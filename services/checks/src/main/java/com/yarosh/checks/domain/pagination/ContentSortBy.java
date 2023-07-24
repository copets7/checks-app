package com.yarosh.checks.domain.pagination;

public class ContentSortBy {

    private final String column;
    private final boolean isDesc;

    public ContentSortBy() {
        this("id", true);
    }

    public ContentSortBy(String column, boolean isDesc) {
        this.column = column;
        this.isDesc = isDesc;
    }

    public String getColumn() {
        return column;
    }

    public boolean isDesc() {
        return isDesc;
    }

    @Override
    public String toString() {
        return "ContentSortBy{" +
                "column='" + column + '\'' +
                ", isDesc=" + isDesc +
                '}';
    }
}
