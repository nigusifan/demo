package net.intelink.zmframework.exception;


import net.intelink.zmframework.enums.IEnumCode;

/**
 * 异常基础类
 *
 * @author suzhongqiang
 * @date 2017.05.31
 */
public class BaseException extends RuntimeException {

    IEnumCode code;
    String message;
    String solution;	//出错时的解决方案提示信息


    public BaseException() {

    }

    public BaseException(String message) {
        super(message);
        this.message = message;

    }


    public BaseException(IEnumCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;

    }

    public BaseException(Throwable message) {
        super(message);
    }

    public BaseException(IEnumCode code) {
        super(code.getMessage());
        this.message = code.getMessage();
        this.code = code;
        this.solution = code.getSolution();
    }

    public BaseException(IEnumCode code, String message, String solution) {
        super(code.getMessage());
        this.message = message;
        this.code = code;
        this.solution = solution;
    }

    public IEnumCode getCode() {
        return code;
    }

    public void setCode(IEnumCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
