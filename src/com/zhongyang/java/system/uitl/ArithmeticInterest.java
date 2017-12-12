package com.zhongyang.java.system.uitl;

import java.math.BigDecimal;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月25日 下午2:28:35 类说明：还款利息算法
 */
public class ArithmeticInterest {
	// 一次性付本息算法：还款利息和投资利息都是一次性还，公式：利率*本金*期限/12；除12是为了把年华利率转为为月利率
	public static BigDecimal getArithmeticInterest(Double rate, BigDecimal loanAmount, int longtime) {
		String mont = Integer.toString(longtime);
		BigDecimal years = new BigDecimal(12);
		BigDecimal months = new BigDecimal(mont);
		// 1，先把利率换算为小数类型：比如把5% 换算为0.05
		BigDecimal retaToP = new BigDecimal(rate).divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_EVEN);
		// 2,一定金额年华利息：利息*标的金额
		BigDecimal tretaAmount = retaToP.multiply(loanAmount).setScale(4, BigDecimal.ROUND_HALF_EVEN);
		// 3，月化利息:总利息/12
		BigDecimal monthsRate = new BigDecimal(0.00);
		monthsRate = (tretaAmount.multiply(months)).divide(years, 2, BigDecimal.ROUND_HALF_EVEN);

		return monthsRate;
	}

	// 按月付息到期还本算法：还款利息和投资利息都是一次性还，公式：利率*本金/12；除12是为了把年华利率转为为月利率
	public static BigDecimal getArithmeticMonthInterest(Double rate, BigDecimal loanAmount) {
		BigDecimal years = new BigDecimal(12);
		// 1，先把利率换算为小数类型：比如把5% 换算为0.05
		BigDecimal retaToP = new BigDecimal(rate).divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_EVEN);
		// 2,一定金额年华利息：利息*标的金额
		BigDecimal tretaAmount = retaToP.multiply(loanAmount).setScale(4, BigDecimal.ROUND_HALF_EVEN);
		// 3，月化利息:总利息/12
		BigDecimal monthsRate = tretaAmount.divide(years, 2, BigDecimal.ROUND_HALF_EVEN);
		return monthsRate;
	}
}
