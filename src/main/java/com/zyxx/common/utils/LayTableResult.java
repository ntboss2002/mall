package com.zyxx.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台返回给LayUI表格的数据格式
 *
 * @author zxy
 */
@Getter
@Setter
public class LayTableResult<T> {

    /**
     * 接口状态
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 接口数据长度
     */
    private Long count;

    /**
     * 接口数据
     */
    private List<T> data;

    /**
     * 无参构造函数
     */
    public LayTableResult() {
        super();
    }

    /**
     * 返回数据给表格
     */
    public LayTableResult(Long count, List<T> data) {
        super();
        this.count = count;
        this.data = data;
        this.code = 0;
    }

    /**
     * 返回数据给表格
     */
    public LayTableResult(Integer count, List<T> data) {
        super();
        this.count = count.longValue();
        this.data = data;
        this.code = 0;
    }

    /**
     * 返回数据给表格
     * <p>
     * 配合mybatisplus分页查询使用
     */
    public LayTableResult(IPage<T> iPage) {
        super();
        this.count = iPage.getTotal();
        this.data = iPage.getRecords();
        this.code = 0;
    }
}
