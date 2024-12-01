package com.mockmate.auth_service.dto.user;


import java.util.List;

public class PastInterviewsPageDto {
    private int page;
    private int limit;
    private long totalListings;
    private int totalPages;
    private List<PastInterviewDto> results;

    // Getters and Setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalListings() {
        return totalListings;
    }

    public void setTotalListings(long totalListings) {
        this.totalListings = totalListings;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<PastInterviewDto> getResults() {
        return results;
    }

    public void setResults(List<PastInterviewDto> results) {
        this.results = results;
    }
}
