package com.interview.mapper;

import com.interview.service.model.PagedResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponseMapper {

    public static <T, R> PagedResponse<R> pageToPageResponseMap(Page<T> page, Function<T, R> contentTransformer) {
        return new PagedResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent().stream().map(contentTransformer)
                        .collect(Collectors.toList())
        );
    }
}
