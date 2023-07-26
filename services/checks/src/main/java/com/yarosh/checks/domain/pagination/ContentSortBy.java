package com.yarosh.checks.domain.pagination;

public class ContentSortBy {

    private static final String DEFAULT_SORTING_COLUMN = "id";

    private final String column;
    private final boolean isDesc;

    public ContentSortBy() {
        this(DEFAULT_SORTING_COLUMN, true);
    }

    public ContentSortBy(String column) {
        this(column, true);
    }

    public ContentSortBy(boolean isDesc) {
        this(DEFAULT_SORTING_COLUMN, isDesc);
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
