/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.model.search;


import java.util.List;
import java.util.Optional;


public class BaseSearchRequest {

    private Integer pageSize;
    private Integer pageNumber;
    private List<SortField> sorts;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<SortField> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortField> sorts) {
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
