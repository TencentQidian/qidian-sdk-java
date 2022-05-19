package com.tencent.qidian.test;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import com.tencent.qidian.sdk.ThirdPartyPersonUtil;

/**
 * 第三方个人应用测试
 *
 * @author Jun
 */
public class ThirdPartyPersonTest {


    String encodingAesKey = "s2I6wx2vDpAxHNmhTkYaDK3J4kCMg7t0pjkl4gbTTHD";
    String token = "token1";
    String appId = "202187955";

    String timestamp = "1631497803";
    String nonce = "45685418431";
    String signature = "0dea83a7b6dc1f4b58da16df10479638f5de3bae";
    String xmlText = "<xml><AppId><![CDATA[202187955]]></AppId><Encrypt><![CDATA[BFyzIcV/mmoOTqKIoDGDBh9u58fx072e83W7ZfnBGvqH7DWXMs8Fz/IbokkniyoAtfiNb4UuDo6eV+QQJtqMu2ZlGmQ1Ka+VuG6A6JVJNwTPXVjOBNlD9NmPheaFP3BxdOPPMrp9A8ZM9Q8h59sAe3SXC6g4WsYljwYTotl4KVj1iWMx2z8WCQ2THTNyoOULoBVPABckE9H58+4/tFyaKNT4Rw+p8BNXd4XzC5l9ORRolu3LJAxkHEeRU7XNGL3fULk+AJ0pa/9THbCGCTIo8J/YUHxwj1Krokkpv6l/DwvtjbFSmRXh/H8LtMtsBZmuHzmKW7fuhxTewrgPv4vEVA==]]></Encrypt></xml>";
    String componentAccessToken;
    String ticket;
    JSONObject jsonobject;


    /**
     * 获取应用供应商token
     *
     * @throws Exception 异常抛出
     */
    @Before
    public void getComponentAccessToken() throws Exception {
        Map<String, Object> tickets = new ThirdPartyPersonUtil().getVerifyTicket(encodingAesKey, token, signature.toString(),
                timestamp, nonce, xmlText.toString());
        ticket = tickets.get("ComponentVerifyTicket").toString();
        assert (ticket != null) : "ticket为null";
        String componentAppSecret = "rc9SdaLtEMOGvPn0";// 开发者密钥
        JSONObject jsonObject = new ThirdPartyPersonUtil().getComponentAccessToken(appId, componentAppSecret, ticket);
        assert (jsonObject.get("component_access_token") != null) : "应用供应商token为null: " + jsonObject.get("errmsg");
        componentAccessToken = jsonObject.get("component_access_token").toString();
    }

    /**
     * 使用应用授权code换取应用授权token
     *
     * @throws Exception 异常抛出
     */
    @Test
    public void getAuthorizerPersonalAccessToken() throws Exception {
        jsonobject = new ThirdPartyPersonUtil().getAuthorizerPersonalAccessToken(appId,
                "bf81dbffb2b7d18248131141f0b5375b", componentAccessToken.toString());
        assert (null != jsonobject.get("authorization_info")) : "获取token失败: " + jsonobject.get("errmsg");
        JSONObject resJson = JSONObject.parseObject(jsonobject.get("authorization_info").toString());
        String authorizerRefreshToken = resJson.get("authorizer_refresh_token").toString();
        assert (null != authorizerRefreshToken) : "刷新token为null";
    }

    /**
     * 刷新token
     *
     * @throws Exception 异常抛出
     */
    @Test
    public void getNewAuthorizerPersonalAccessToken() throws Exception {
        String userId = "223H3SCo9rcrJLVSAxSvTsZcnixfh1AzTc0BTw2DAEQ=";
        String authorizerUin = "119983";
        String sid = "1300000865";
        String authorizerRefreshToken = "6b9f7a5672d4ab55cce608f9099701d0";
        JSONObject jsonObject = new ThirdPartyPersonUtil().getNewAuthorizerPersonalAccessToken(userId,
                authorizerUin, authorizerRefreshToken, sid, componentAccessToken);
        assert (null != jsonObject) : "接口返回值为null";
        assert (null != jsonObject.get("authorizer_access_token")) : "token获取失败: " + jsonObject.get("errmsg");
    }

}
