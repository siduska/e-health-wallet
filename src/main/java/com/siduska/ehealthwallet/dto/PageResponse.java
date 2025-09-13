package com.siduska.ehealthwallet.dto;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {

    // Constants for pagination headers
    public static final String HEADER_TOTAL_COUNT = "X-Total-Count";
    public static final String HEADER_TOTAL_PAGES = "X-Total-Pages";
    public static final String HEADER_CURRENT_PAGE = "X-Current-Page";
    public static final String HEADER_PAGE_SIZE = "X-Page-Size";

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    public HttpHeaders toHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_TOTAL_COUNT, String.valueOf(totalElements));
        headers.add(HEADER_TOTAL_PAGES, String.valueOf(totalPages));
        headers.add(HEADER_CURRENT_PAGE, String.valueOf(page));
        headers.add(HEADER_PAGE_SIZE, String.valueOf(size));
        return headers;
    }
}
