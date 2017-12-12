package com.zhongyang.java.zyfyback.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.FundRecordOperation;
import com.zhongyang.java.system.enumtype.FundRecordStatus;
import com.zhongyang.java.system.enumtype.FundRecordType;
import com.zhongyang.java.system.enumtype.OrderType;

public class PlatFundRecord implements Serializable{
	
    private String id;

    private String account;

    private BigDecimal amount;

    private String description;

    private FundRecordOperation operation;
    
    private String strOpreation;

    private String orderId;

    private String remark;

    private FundRecordStatus status;
    
    private String strStatus;

    private Date timeRecorded;
    
    private String strTimeRecorded;

    private FundRecordType type;
    
    private String strType;

    private String userId;
    
    private String userName;
    
    private String mobile;
    
    
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStrOpreation() {
		return strOpreation;
	}

	public void setStrOpreation(String strOpreation) {
		this.strOpreation = strOpreation;
	}

	public String getStrTimeRecorded() {
		return strTimeRecorded;
	}

	public void setStrTimeRecorded(String strTimeRecorded) {
		this.strTimeRecorded = strTimeRecorded;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FundRecordOperation getOperation() {
		return operation;
	}

	public void setOperation(FundRecordOperation operation) {
		this.operation = operation;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

	public Date getTimeRecorded() {
		return timeRecorded;
	}

	public void setTimeRecorded(Date timeRecorded) {
		this.timeRecorded = timeRecorded;
	}

	public FundRecordType getType() {
		return type;
	}

	public void setType(FundRecordType type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}