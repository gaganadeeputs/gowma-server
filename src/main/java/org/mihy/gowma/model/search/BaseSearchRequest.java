/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model.search;


import java.util.List;
import java.util.Optional;


public class BaseSearchRequest {

    private Optional<Integer> pageSize;
    private Optional<Integer> pageNumber;
    private Optional<List<SortField>> sorts;

    public Optional<Integer> getPageSize() {
        return pageSize;
    }

    public void setPageSize(Optional<Integer> pageSize) {
        this.pageSize = pageSize;
    }

    public Optional<Integer> getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Optional<Integer> pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Optional<List<SortField>> getSorts() {
        return sorts;
    }

    public void setSorts(Optional<List<SortField>> sorts) {
        this.sorts = sorts;
    }

    public static class SortField {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public SortOrder getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(SortOrder sortOrder) {
            this.sortOrder = sortOrder;
        }

        private SortOrder sortOrder;

        public static enum SortOrder {
            ASC,DESC
        }
    }

}
