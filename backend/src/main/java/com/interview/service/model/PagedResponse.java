package com.interview.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResponse<T> {
    private int page;
    private int size;
    private int total;

    private List<T> values;
}
