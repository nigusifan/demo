package net.intelink.zmframework.model;

import java.io.Serializable;

public class UserMenuStagg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//菜单id
	private Long menuId;
	//角色id
	private Long staggId;
	//授权网点过滤[B_AUTH_STAT_FILTER:1-无网点授权;2-所有网点;3-总部;4-区域中心;5-总部、区域中心]
	private String authStatFilter; 
	
	
	
	public String getAuthStatFilter() {
		return authStatFilter;
	}
	public void setAuthStatFilter(String authStatFilter) {
		this.authStatFilter = authStatFilter;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getStaggId() {
		return staggId;
	}
	public void setStaggId(Long staggId) {
		this.staggId = staggId;
	}
	
	
	
	
}
