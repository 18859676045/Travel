package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalCount;
    private int currentPage;
    private int intpageSize;
    private List<T> list;
    private int totalPage;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public PageBean() {
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", intpageSize=" + intpageSize +
                ", list=" + list +
                ", totalPage=" + totalPage +
                '}';
    }

    public PageBean(int totalCount, int currentPage, int intpageSize, List<T> list, int totalPage) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.intpageSize = intpageSize;
        this.list = list;
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }



    public int getIntpageSize() {
        return intpageSize;
    }

    public void setIntpageSize(int intpageSize) {
        this.intpageSize = intpageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
