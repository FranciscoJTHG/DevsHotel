package com.dev.DevsHotel.domain.utils;

import java.util.List;

import org.springframework.data.domain.Page;

// Se creaa este record ya que persiste el warning sobre la serialización de PageImpl. Para resolver esto y mejorar la implementación

public record PageResponse<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
}
