package com.personal.base.service.sms.aliyun.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.personal.base.service.sms.aliyun.conf.AccessKey;
import com.personal.base.service.sms.aliyun.model.SendSmsModel;
import com.personal.base.service.sms.aliyun.model.SmsSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsService {
    private static Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    private AccessKey access;

    public SmsSendResult sendSms(SendSmsModel smsModel) throws ClientException {
        logger.info("[base-sms-service] 发送短信,参数{}", JSONObject.toJSONString(smsModel));
        SmsSendResult sendResult = new SmsSendResult(0, "系统错误,发送失败");
        if (smsModel == null) {
            sendResult.setMessage("传入参数为空");
            return sendResult;
        }
        if (StringUtils.isEmpty(smsModel.getTelephone())) {
            sendResult.setMessage("手机号不能为空!");
            return sendResult;
        }
        if (StringUtils.isEmpty(smsModel.getSignName())) {
            sendResult.setMessage("签名不能为空!");
            return sendResult;
        }
        if (StringUtils.isEmpty(smsModel.getTemplateCode())) {
            sendResult.setMessage("短信模板标识不能为空!");
            return sendResult;
        }
        if (access == null || StringUtils.isEmpty(access.getAccessKeyId()) || StringUtils.isEmpty(access.getAccessKeySecret())) {
            sendResult.setMessage("accesskey不能为空,请注意配置文件是否正确设置");
            return sendResult;
        }
        DefaultProfile profile = DefaultProfile.getProfile("default", access.getAccessKeyId(), access.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", smsModel.getTelephone());
        request.putQueryParameter("SignName", smsModel.getSignName());
        request.putQueryParameter("TemplateCode", smsModel.getTemplateCode());
        if (smsModel.getParameters() != null) {
            request.putQueryParameter("TemplateParam", JSONObject.toJSONString(smsModel.getParameters()));
        }
        if (smsModel.getExtendCode() != null) {
            request.putQueryParameter("SmsUpExtendCode", JSONObject.toJSONString(smsModel.getExtendCode()));
        }
        CommonResponse response = client.getCommonResponse(request);
        logger.debug("获取到应答报文:{}", JSONObject.toJSONString(response));
        Map data = (Map) JSONObject.parse(response.getData());
        if (response.getHttpStatus() == 200 && "ok".equalsIgnoreCase(String.valueOf(data.get("Code")))) {
            sendResult.setCode(1);
            sendResult.setMessage("发送成功");
            logger.info("[base-service-sms] 发送短信成功");
            return sendResult;
        }
        logger.info("[base-service-sms] 发送短信失败,aliyun反馈信息{},全部信息为:{}", data.get("Message"), JSONObject.toJSONString(response));
        return sendResult;
    }

}
