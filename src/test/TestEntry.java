import com.aliyuncs.exceptions.ClientException;
import com.personal.base.service.sms.aliyun.model.SendSmsModel;
import com.personal.base.service.sms.aliyun.service.SmsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestEntry {
    public static void main(String[] args) throws ClientException {
        SendSmsModel model = new SendSmsModel();
        model.setCode("1234");
        model.setTelephone("15311440358");
        model.setTemplateCode("SMS_168826077");
        model.setExtendCode(null);
        model.setOutId("outid");
        model.setSignName("jaye");
        Map<String,String> parameter = new HashMap<>();
        parameter.put("code","1234");
        model.setParameters(parameter);

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-base-service-sms.xml");
        SmsService service = (SmsService) ctx.getBean("smsService");
        service.sendSms(model);
        while(true){

        }
    }
}
