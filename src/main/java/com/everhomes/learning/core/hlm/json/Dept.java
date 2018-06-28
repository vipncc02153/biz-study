package com.everhomes.learning.core.hlm.json;

import java.math.BigDecimal;

public class Dept {

	private String deptName ;
    private Integer deptNo;
    private Object deptManager;
    private byte num;
    private BigDecimal bigDecimal ;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}
	public Object getDeptManager() {
		return deptManager;
	}
	public void setDeptManager(Object deptManager) {
		this.deptManager = deptManager;
	}
	public byte getNum() {
		return num;
	}
	public void setNum(byte num) {
		this.num = num;
	}
	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}
	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}
	
    
}
