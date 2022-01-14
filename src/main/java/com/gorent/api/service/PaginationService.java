package com.gorent.api.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    private final int DEFAULT_PAGE_SIZE = 20;

    public Pageable getPageable(Integer page, Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of(page, pageSize);
    }

}
