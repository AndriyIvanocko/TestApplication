package com.example.testapplication.presentation.base.list;

import java.util.List;

public class PaginationListModel<T> {
    private List<T> data;
    private int currentPage;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
