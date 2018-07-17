package net.intelink.zmframework.model;

public class RequestHeader {
	
	private String appid;  //	String	App识别代码	
	private String device_id; //	String	//设备号	
	private String command;//	String	//请求命令(远程方法名称)	Login(登录)
	private String version; //	String	//请求命令的版本，决定Body实体的成员	1.0
	private String token; //	String	//登录令牌(类似于SessionID)	
	private String sign; //	String	//数字签名MD5_16(appid+body+command+device_id+ encrypt_type +token+vesion appkey)	
	private Integer encrypt_type; //	int	//请求的参数实体加密方式	0
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getEncrypt_type() {
		return encrypt_type;
	}
	public void setEncrypt_type(Integer encrypt_type) {
		this.encrypt_type = encrypt_type;
	}
	@Override
	public String toString() {
		return "RequestHeader [appid=" + appid + ", device_id=" + device_id + ", command=" + command + ", version="
				+ version + ", token=" + token + ", sign=" + sign + ", encrypt_type=" + encrypt_type + "]";
	}

	
	
	
}
