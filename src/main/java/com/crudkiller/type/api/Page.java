package com.crudkiller.type.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private int start;
    private int limit;
    private int page;
    private String sort;
    @Builder.Default
    private SortOrder sortOrder = SortOrder.ASC;
    @Builder.Default
    private boolean emptyPage = true;

    public static Page of() {
        return Page.builder().build();
    }

    public static Page of(int start, int limit, int page, String sort) {
        return Page.builder()
                .emptyPage(false)
                .start(start)
                .limit(limit)
                .page(page)
                .sort(sort)
                .build();
    }

    public static Page of(int start, int limit, String sort) {
        return Page.builder().start(start).limit(limit).sort(sort).emptyPage(false).build();
    }
}