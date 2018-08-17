package org.basis.network;

import java.io.Serializable;

/**
 * @author: BaiCQ
 * @ClassName: ParseBean
 * @date: 2018/6/27
 * @Description: ParseBean Json 解析实体
 */
public class ParseBean implements Serializable{
    private int status;
    private String sysMsg;
    private String body;
    private int pageIndex;
    private int pageTotal;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(String sysMsg) {
        this.sysMsg = sysMsg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }
}
