package com.personal.base.service.sms.aliyun.model;

import java.util.List;
import java.util.Map;

public class SendSmsModel<T> {

    private String telephone;
    private String code;
    private String templateCode;
    private Long extendCode;
    private String outId;
    private Map parameters;
    private String signName;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Long getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(Long extendCode) {
        this.extendCode = extendCode;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
