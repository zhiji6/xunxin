/*
 * Copyright (c) 2016-8-31
 * created by alex
 */

package com.xunxin.web.api.bean;

import java.util.List;

/**
 * 分页结果对象
 *
 * @author huangyong
 * @since 1.0.0
 */
public class PagingResult<T> {

    public PagingResult(List<T> data,Long total) {
        setList(data);
        setTotal(total);
    }

    public PagingResult() {
		// TODO Auto-generated constructor stub
	}

	private List<T> list;
    private Long last;
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getLast() {
        return last;
    }

    public void setLast(Long last) {
        this.last = last;
    }

}
