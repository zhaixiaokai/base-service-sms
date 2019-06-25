package com.personal.base.service.sms.aliyun.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.personal.base.service.sms.aliyun.model.SendSmsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private static Logger logger = LoggerFactory.getLogger(SmsService.class);
    DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI9skWZR7BGXOQ", "EWKAh316Wp7paLo1mDpEJQopa1XS5E");


    public Boolean sendSms(SendSmsModel smsModel) throws ClientException {

        IAcsClient client = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        request.setEndpoint("ecs-cn-hangzhou.aliyuncs.com");
        //必填:待发送手机号
        request.setPhoneNumbers(smsModel.getTelephone());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(smsModel.getSignName());    // TODO 修改成自己的
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsModel.getTemplateCode());    // TODO 修改成自己的
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        request.setTemplateParam(JSONObject.toJSONString(smsModel.getParameters()));
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        request.setVersion("2019-06-05");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(smsModel.getOutId());
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = client.getAcsResponse(request);
        if(sendSmsResponse.getCode()!= null && sendSmsResponse.getCode().equals("OK")){
            logger.info("短信发送成功:{}",smsModel);
            return true;
        }else {
            logger.error("短信发送失败:{}",smsModel);
        }
        return false;
    }

}
