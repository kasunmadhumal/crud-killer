package com.crudkiller.type.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchFilter {
    private Page page;

    public static SearchFilter empty() {
        return new SearchFilter(Page.of());
    }
}