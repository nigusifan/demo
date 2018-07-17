package net.intelink.zmframework.enums;

public enum EnumResCode implements IEnumCode {

    OK(0, "请求成功"),
    ERROR(1,"系统繁忙"),
    PARAMETER_ERROR(400,"提交数据不合法或协议错误！"),
    DEVICE_NO_AUTHZ(401,"设备未授权"),
    LOGIN_FAIL(403,"登录失败：用户密码错误或用户被禁用"),
    NO_LOGIN(404,"用户未登录，请重新登录"),
    INVALID_REQ_ENTITY(421,"无效的请求实体类型"),
    DECRYPT_REQ_FAIL(422,"解密请求实体失败"),
    RESOLVE_REQ_FAIL(423,"解析请求实体失败"),
    INVALID_TOKEN(432,"无效TOKEN"),

    ;
	
    int code;
    String msg;
    String solution;

    EnumResCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    EnumResCode(int code, String msg,String solution) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the msg
     */
    public String getMessage() {
        return msg;
    }

    public String getSolution(){
        return solution;
    }
}
