package net.intelink.zmframework.model;

import java.io.Serializable;
import java.util.List;


/**
 * @description 用户信息
 * @author suzhongqiang
 * @date 17/7/1 下午2:12   
 * @version 1.0.0  
 */
public class UserInfoModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private UserInfo userInfo;

	private StatInfo statInfo;
	
	private List<UserMenuStagg> listMenuStagg;  //用户菜单角色关系数据
	
	private List<UserStaggStat> listStaggStat;  //用户角色网点关系数据
	
	
	private String version;
	
	private String printType;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public StatInfo getStatInfo() {
		return statInfo;
	}

	public void setStatInfo(StatInfo statInfo) {
		this.statInfo = statInfo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<UserMenuStagg> getListMenuStagg() {
		return listMenuStagg;
	}

	public void setListMenuStagg(List<UserMenuStagg> listMenuStagg) {
		this.listMenuStagg = listMenuStagg;
	}

	public List<UserStaggStat> getListStaggStat() {
		return listStaggStat;
	}

	public void setListStaggStat(List<UserStaggStat> listStaggStat) {
		this.listStaggStat = listStaggStat;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	

	


}
