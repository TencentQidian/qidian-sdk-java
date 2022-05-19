package com.tencent.qidian.test;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.tencent.qidian.sdk.CommonUtil;

/**
 * 基类测试
 *
 * @author Jun
 */
public class CommonTest {

    String encodingAesKey = "mQugtnS8RKTrcucB0fcGep7jwVWfHA1BBTqdE7Ti9Re";
    String token = "meeting123";
    String timestamp = "1629093004";
    String nonce = "83617831335";
    String appId = "202144643";
    Map<String, Object> ency = new HashMap<String, Object>();


    /**
     * 加密解密
     *
     * @throws Exception 异常抛出
     */
    @Test
    public void decryptXml() throws Exception {
        // 加密
        CommonUtil commonUtil = new CommonUtil();
        ency = commonUtil.encryptionXml(encodingAesKey, token, timestamp, nonce, appId, "测试的加密");
        Object signature = ency.get("signature");
        Object xmlText = ency.get("xmlText");
        // 解密
        String result = commonUtil.decryptXml(encodingAesKey, token, signature.toString(), timestamp, nonce, xmlText.toString());
        assert (result != null) : "解密为null";
    }


    /**
     * 解析events
     * @throws Exception 异常抛出
     */
    @Test
    public void getEvents() throws Exception {
        CommonUtil commonUtil = new CommonUtil();
        String timestamp = "1629100995";
        String nonce = "fprxshrb";
        String signature = "e3da63f50a337c7b78897cacb422f143e5aedaf6";
        String xmlText = "<xml><AppId><![CDATA[202144643]]></AppId><Encrypt><![CDATA[MvNSkH6kHq8osU+mh9A23RWRJZQ57T+V289FQmKkjppXJagkNTQvOdY0TQuPy6aouCepDbMxH2BdtFoWMgu2E2+85TqxDw0TXKhXn1Sum9ZUQvxBu7g4ocV78fWY5eCc0EDoBVADJugnlymGV6rOfNIsV2SlOhpMXt87tkG3aet2d9bPDW4fmyKm4QDOj72eUIc/Tmc3e8zhY0dJO5NeBqzLO8c63+Q7vblzZ0Y8PzLBiqjeozuBzzTneybnthOgYPfIw9p7EwfVTlJB1wllyVaYzZDDJ0GCWDELVJXkqtgf292dQibH2iaUZ5JXT66bpEMBF1vm8S46mbsaOf/WvdPB9y07NpEIakXzTff9QRdIuo4+qNfo5MaJRLo8PmN2isvjpO8PiEV/+dLj6kabRl2C+wB0MljTLUaO/0ScSVav2t0noR/MvYqp3BLfawMVlWKK4lDXiuFUIpN93WolpSNIb4wA/Rs+maQsyxpZOz2QONHWZWlSTqphKatP7MO2CXMsaL+UApYEwiSqtSZnYduioNKyENAZHpoFtIdepAbDzuwkvgEV2Q4rHZIw1HPcxHs0A0kfFd1KFb4CggpvuBAqwl/69cxi7WYASrJexxqq9h4+uhsO6+LGCp6c8qqn]]></Encrypt></xml>";
        Map<String, Object> map = commonUtil.getEvents(encodingAesKey, token, signature.toString(), timestamp, nonce,
                xmlText);
        assert (map.get("Appid") != null) : "解密为null";
    }

    /**
     * 验证url
     *
     * @throws Exception 异常抛出
     */
    @Test
    public void verifyUrl() throws Exception {
        String echoStr = new CommonUtil().verifyUrl("token1", "4e2f3116e9abb2fc4e77eefc90ba55f4a8d02ef3", "1629257567",
                "ixagoqda", "qonkxaqp");
        assert (echoStr == "qonkxaqp") : "验证url失败";
    }

}
