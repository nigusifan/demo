package net.intelink.zmframework.exception;


import net.intelink.zmframework.enums.IEnumCode;

/**
 * 控制层异常
 *
 * @author suzhongqiang
 * @date 2017.05.31
 */
public class ControllerException extends BaseException {

    public ControllerException() {
        super();
    }

    public ControllerException(IEnumCode code) {
        super(code);
    }

    public ControllerException(IEnumCode code, String message) {
        super(code,message);
    }

    public ControllerException(IEnumCode code, String message, String solution) {
        super(code, message, solution);
    }

    public ControllerException(String msg) {
        super(msg);
    }
}
