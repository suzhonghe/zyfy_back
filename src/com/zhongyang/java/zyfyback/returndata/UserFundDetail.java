package com.zhongyang.java.zyfyback.returndata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@package com.zhongyang.java.zyfyback.returndata
 *@filename UserFundDetail.java
 *@date 2017年7月21日上午9:00:42
 *@author suzh
 */
public class UserFundDetail implements Serializable{

	private int investCount;
	
	private int loanCount;
	
	private BigDecimal availableAmount;
	
	private BigDecimal dueInAmount;//待收

    private BigDecimal dueOutAmount;//待还

    private BigDecimal frozenAmount;//冻结
    
    private BigDecimal totalAmount;//资产总额：可用余额+冻结+待收

	public int getInvestCount() {
		return investCount;
	}

	public void setInvestCount(int investCount) {
		this.investCount = investCount;
	}

	public int getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(int loanCount) {
		this.loanCount = loanCount;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getDueInAmount() {
		return dueInAmount;
	}

	public void setDueInAmount(BigDecimal dueInAmount) {
		this.dueInAmount = dueInAmount;
	}

	public BigDecimal getDueOutAmount() {
		return dueOutAmount;
	}

	public void setDueOutAmount(BigDecimal dueOutAmount) {
		this.dueOutAmount = dueOutAmount;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
    
}
