package net.intelink.zmframework.exception;

import net.intelink.zmframework.enums.IEnumCode;

/**
 * 业务逻辑层异常
 *
 * @author suzhongqiang
 * @date 2017.05.31
 */
public class ServiceException extends BaseException {

    public ServiceException() {
        super();
    }

    public ServiceException(Throwable e) {
        super(e);
    }

    public ServiceException(IEnumCode code) {
        super(code);
    }

    public ServiceException(IEnumCode code, String message) {
        super(code,message);
    }

    public ServiceException(IEnumCode code, String message, String solution) {
        super(code, message, solution);
    }

    public ServiceException(String msg) {
        super(msg);
    }


}
