package com.member.system.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 统一分页结果
 *
 * @param <T> 列表元素类型
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页码，从 1 开始 */
    private long pageNo;

    /** 每页大小 */
    private long pageSize;

    /** 总记录数 */
    private long total;

    /** 总页数 */
    private long pages;

    /** 当前页数据 */
    private List<T> records;

    public PageResult() {
        this.records = Collections.emptyList();
    }

    public static <T> PageResult<T> of(long pageNo, long pageSize, long total, List<T> records) {
        PageResult<T> page = new PageResult<T>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(total);
        page.setPages(pageSize <= 0 ? 0 : (total + pageSize - 1) / pageSize);
        page.setRecords(records == null ? Collections.<T>emptyList() : records);
        return page;
    }

    public static <T> PageResult<T> empty(long pageNo, long pageSize) {
        return of(pageNo, pageSize, 0L, Collections.<T>emptyList());
    }
}
