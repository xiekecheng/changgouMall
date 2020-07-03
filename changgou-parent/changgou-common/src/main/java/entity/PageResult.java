package entity;

import java.util.List;

/**
 * @description: 分页结果类
 * @author: Kecheng Xie
 * @since: 2020-04-01 15:55
 **/
public class PageResult<T> {
    private Long total;//总记录数
    private List<T> rows;//记录

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
