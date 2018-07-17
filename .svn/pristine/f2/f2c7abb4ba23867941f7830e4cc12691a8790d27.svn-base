/**
 *
 */
package net.intelink.zmframework.model;

import java.io.Serializable;

/**
 * 分页工具类
 *
 * @author suzhongqiang
 * @date 2017.05.31
 */
public class Limit implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2864524359649764964L;
    public static final Limit LIMIT_ONE = buildLimit(1, 1);
    private int size;
    private int pageId;
    private int start;
    private int end;

    /**
     * 用于 页面&DB分页
     *
     * @param pageId
     * @param pageSize
     * @return
     */
    public static Limit buildLimit(int pageId, int pageSize) {
        if (pageId <= 0)
            pageId = 1;
        if (pageSize <= 0)
            pageSize = 50;// 默认50
        Limit limit = new Limit();
        limit.pageId = pageId;
        limit.size = pageSize;
        limit.start = (pageId - 1) * pageSize;
        limit.end = pageId * pageSize;
        return limit;
    }

    private Limit(int pageId, int pageSize) {
        this.pageId = pageId;
        this.size = pageSize;
    }

    private Limit() {
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the size
     */
    public int getStart() {
        return start;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the pageId
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * @param pageId the pageId to set
     */
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getEnd() {
        return end;
    }
}