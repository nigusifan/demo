package net.intelink.zmframework.model;

import java.io.Serializable;

public class UserStaggStat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long staggId;
	
	private Long statId;
	
	private Integer statGrade;

	public Integer getStatGrade() {
		return statGrade;
	}

	public void setStatGrade(Integer statGrade) {
		this.statGrade = statGrade;
	}

	public Long getStaggId() {
		return staggId;
	}

	public void setStaggId(Long staggId) {
		this.staggId = staggId;
	}

	public Long getStatId() {
		return statId;
	}

	public void setStatId(Long statId) {
		this.statId = statId;
	}
	
	

}
