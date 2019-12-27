package com.laonsys.springmvc.extensions.model;


/**
 * 페이징 처리를 위한 유틸리티 클래스
 * @author  kaldosa
 */
public final class Paginate {
    public static int DEFAULT_LIMIT = 10;

    private int itemPerPage = DEFAULT_LIMIT;

    private int pagePerBlock = DEFAULT_LIMIT;

    private int totalItem;

    private int totalPage;

    private int page = 1;

    private int startPage;

    private int endPage;

    public Paginate() {
    }

    public Paginate(int itemPerPage, int pagePerBlock) {
        this.itemPerPage = (itemPerPage == 0) ? DEFAULT_LIMIT : itemPerPage;
        this.pagePerBlock = (pagePerBlock == 0) ? DEFAULT_LIMIT : pagePerBlock;
    }
    
    public Paginate(int page, int itemPerPage, int pagePerBlock) {
        this(itemPerPage, pagePerBlock);
        this.page = (page == 0) ? 1 : page;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = (itemPerPage == 0) ? DEFAULT_LIMIT : itemPerPage;
    }

    public int getPagePerBlock() {
        return pagePerBlock;
    }

    public void setPagePerBlock(int pagePerBlock) {
        this.pagePerBlock = (pagePerBlock == 0) ? DEFAULT_LIMIT : pagePerBlock;
    }

    public int getStartItem() {
        return (this.page - 1) * itemPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = (page == 0) ? 1 : page;
    }

    public Paginate paging(int totalItem) {
        return paging(totalItem, page, itemPerPage, pagePerBlock);
    }

    public Paginate paging(int totalItem, int page) {
        return paging(totalItem, page, itemPerPage, pagePerBlock);
    }
    
    public Paginate paging(int totalItem, int page, int itemPerPage, int pagePerBlock) {
        this.page = (page == 0) ? 1 : page;
        this.itemPerPage = (itemPerPage == 0) ? DEFAULT_LIMIT : itemPerPage;
        this.pagePerBlock = (pagePerBlock == 0) ? DEFAULT_LIMIT : pagePerBlock;
        this.totalItem = totalItem;
        
        totalPage = (totalItem / itemPerPage) + ((totalItem % itemPerPage == 0) ? 0 : 1);
        totalPage = (totalPage == 0) ? 1 : totalPage;

        /*
         * 현재 페이지 블럭을 구한다. 페이지블럭당 페이지수가 10 개, 즉 한번에 보여줄 페이지 수가 10개라고 가정하고 
         * 현재 페이지가 44 페이지면, 현재 페이지 블럭은 44/10 하여 소수점을 올림한 결과 5 이다. 
         * 따라서 현재 페이지 44 페이지는 다섯번째 페이지 블럭에 있는 페이지이다.
         *  
         * 1 ~ 10 페이지 블럭 (첫번째 페이지 블럭) 
         * 11 ~ 20 페이지 블럭 (두번째 페이지 블럭) 
         * 21 ~ 30 페이지 블럭 (세번째 페이지 블럭) 
         * 31 ~ 40 페이지 블럭 (네번째 페이지 블럭) 
         * 41 ~ 50 페이지 블럭 (다섯번째 페이지 블럭) <-- 44 페이지는 여기에 속함
         */
        int pageBlock = (int) Math.ceil((double) page / pagePerBlock);

        // 현재 페이지 블럭의 시작 페이지와 마지막 페이지를 구한다.
        startPage = (pageBlock - 1) * pagePerBlock + 1;
        endPage = startPage + pagePerBlock - 1;

        // 마지막 페이지가 만약 총 페이지 보다 크면 총 페이지를 마지막 페이지로 설정한다.
        if (endPage > totalPage) {
            endPage = totalPage;
        }
        
        return this;
    }
    
    @Override
    public String toString() {
        return "page: " + page + ", total: " + totalPage 
                + "[" + startPage + ", " + endPage + "], [" 
                + totalItem + ", " + itemPerPage + ", " + pagePerBlock + "]";
    }
}
