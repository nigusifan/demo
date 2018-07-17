package net.intelink.zmframework.model;

import java.io.Serializable;

public class StatInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long statId;
	private String stano;
	private String station;
	private Long parentStatId;
	private String statType;
	private String statManageType;
	private Long accountId;
	private Long grade2StatId;
	private String grade2Station;
	private Long statGrade;
	private Long defaultReceivingNode;
	private String shStation;
	private Integer isparentStart;
	private Integer isDispatch;
	
	public Long getStatId() {
		return statId;
	}
	public void setStatId(Long statId) {
		this.statId = statId;
	}
	public String getStano() {
		return stano;
	}
	public void setStano(String stano) {
		this.stano = stano;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public Long getParentStatId() {
		return parentStatId;
	}
	public void setParentStatId(Long parentStatId) {
		this.parentStatId = parentStatId;
	}
	public String getStatType() {
		return statType;
	}
	public void setStatType(String statType) {
		this.statType = statType;
	}
	public String getStatManageType() {
		return statManageType;
	}
	public void setStatManageType(String statManageType) {
		this.statManageType = statManageType;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getGrade2StatId() {
		return grade2StatId;
	}
	public void setGrade2StatId(Long grade2StatId) {
		this.grade2StatId = grade2StatId;
	}
	public String getGrade2Station() {
		return grade2Station;
	}
	public void setGrade2Station(String grade2Station) {
		this.grade2Station = grade2Station;
	}
	public Long getStatGrade() {
		return statGrade;
	}
	public void setStatGrade(Long statGrade) {
		this.statGrade = statGrade;
	}
	public Long getDefaultReceivingNode() {
		return defaultReceivingNode;
	}
	public void setDefaultReceivingNode(Long defaultReceivingNode) {
		this.defaultReceivingNode = defaultReceivingNode;
	}
	public String getShStation() {
		return shStation;
	}
	public void setShStation(String shStation) {
		this.shStation = shStation;
	}
	public Integer getIsparentStart() {
		return isparentStart;
	}
	public void setIsparentStart(Integer isparentStart) {
		this.isparentStart = isparentStart;
	}
	public Integer getIsDispatch() {
		return isDispatch;
	}
	public void setIsDispatch(Integer isDispatch) {
		this.isDispatch = isDispatch;
	}
	

	
}
