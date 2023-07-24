package com.yarosh.checks.controller.view;

import java.util.List;

public record ContentPageView<V extends View>(List<V> content, Integer size, Integer nextPage) {

    @Override
    public String toString() {
        return "ContentPageView{" +
                "content=" + content +
                ", size=" + size +
                ", nextPage=" + nextPage +
                '}';
    }
}
