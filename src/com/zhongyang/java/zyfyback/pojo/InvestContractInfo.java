package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;
import java.util.Date;

public class InvestContractInfo implements Serializable{

    private String id;

    private String contractno;

    private String path;

    private Date createDate;

    private Date expiredDate;

    private Boolean isdel;

    private String investid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getIsdel() {
        return isdel;
    }

    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }

    public String getInvestid() {
        return investid;
    }

    public void setInvestid(String investid) {
        this.investid = investid == null ? null : investid.trim();
    }
}