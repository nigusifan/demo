package net.intelink.zmframework.model;

import java.util.Date;
/**
 *
 * @description 监控日志模型
 * @author suzhongqiang
 * @date 17/7/14 下午8:27
 * @version 1.0.0
 */
public class MonitorLogModel {

    //日志id
    private Long id;

    //公司代码
    private String companyCode;

    //项目代码
    private String projectCode;

    //日志类型 1:操作日志，2:普通业务日志，3:任务日志，4:接口监控日志
    private Short logType;

    //日志级别  1:info,2:warn,3:error,4:debug
    private Short logLevel;

    //模块  对应菜单下的事件元素，如：提交按钮
    private String module;

    //执行事件 只有在接口监控的时候才是必填项
    private String executeEvent;

    //执行类名
    private String className;

    //日志内容：可存放异常信息，参数信息，响应信心等
    private String logContent;

    //调用开始时间：只有在监控类别是接口的情况下是必填项
    private Date startTime;

    ////调用结束时间：只有在监控类别是接口的情况下是必填项
    private Date endTime;

    //接口响应时间 :单位：毫秒  只有在监控类别是接口的情况下是必填项
    private Integer respTime;

    //服务ip:只有在监控类别是接口的情况下是必填项
    private String serverIp;

    //客户端ip:只有在监控类别是接口的情况下是必填项
    private String clientIp;

    //操作人
    private String operUser;

    //创建时间
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Short getLogType() {
        return logType;
    }

    public void setLogType(Short logType) {
        this.logType = logType;
    }

    public Short getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Short logLevel) {
        this.logLevel = logLevel;
    }

    public Integer getRespTime() {
        return respTime;
    }

    public void setRespTime(Integer respTime) {
        this.respTime = respTime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getExecuteEvent() {
        return executeEvent;
    }

    public void setExecuteEvent(String executeEvent) {
        this.executeEvent = executeEvent;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
