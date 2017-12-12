package com.zhongyang.java.bankmanager.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.FundRecordStatus;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2016年1月14日 下午5:01:03 类说明
 */
public class UntredtedApplication implements Serializable{

	private String id;

	private String account;

	private String operationEmplyee;

	private String telephone;

	private BigDecimal tradeAmount;

	private FundRecordStatus status;

	private Date createTime;
	
	private String strCreateTime;

	private String userName;

	private String userId;

	private String orderId;

	private String description;

	private Boolean flag;
	
	private String cause_type;
	
	private String charge_type;
		
	public String getStrCreateTime() {
		return strCreateTime;
	}

	public void setStrCreateTime(String strCreateTime) {
		this.strCreateTime = strCreateTime;
	}

	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}

	public String getCause_type() {
		return cause_type;
	}

	public void setCause_type(String cause_type) {
		this.cause_type = cause_type;
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

	public String getOperationEmplyee() {
		return operationEmplyee;
	}

	public void setOperationEmplyee(String operationEmplyee) {
		this.operationEmplyee = operationEmplyee;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
