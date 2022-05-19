package com.tencent.qidian.test;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qidian.sdk.ThirdPartyQiDianUtil;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

/**
 * 第三方企点应用测试
 *
 * @author Jun
 */
public class ThirdPartyQiDianTest {

	String encodingAesKey = "s2I6wx2vDpAxHNmhTkYaDK3J4kCMg7t0pjkl4gbTTHD";
	String token = "token1";
	String appId = "202187955";
	String componentAccessToken;
	String ticket;


	String timestamp = "163137000";
	String nonce = "24221883462";
	String signature = "d9239aae902ffdd41903e0cfacfffd3cefb86e61";
	String xmlText = "<xml><AppId><![CDATA[202187955]]></AppId><Encrypt><![CDATA[ZAEAqZ4bWeacR0i7Q4h/fubRA7rk3bQawv3GbSy1yy0FZKf8bcd+OdG8aeqjvpiXFlosDzmdgiX7GP4qdWqHeJGCCDC26S3zUnPZZNRFIgaSPxCzwcrBFdzpBbutgWgkeApNI1d5wLW9Cz0nGZEwCCQdb/lS81S38NKc/GmZWQIGaSQsSW7bfIkyCn3ZaPC/xDwy49H7fZTud7vgJIvjo6owrKDMtYYtu+zWFnq9/vCjyMcxgJbvJlz967GTfZowIbwu1vMMBg/xDgA6D3RRT1qS1ndpEpHuEXB766R020MxFv4WfoJCg66CpbFoU/J0j9l5xiAhhTFQ8eseaND0pA==]]></Encrypt></xml>";


	/**
	 * 获取应用开发商token
	 *
	 * @throws Exception 异常抛出
	 */
	@Before
	public void getComponentAccessToken() throws Exception {
		Map<String, Object> tickets = new ThirdPartyQiDianUtil().getVerifyTicket(encodingAesKey, token, signature.toString(),
				timestamp, nonce, xmlText.toString());
		ticket = tickets.get("ComponentVerifyTicket").toString();
		assert (ticket != null) : "ticket为null";
		String componentAppSecret = "rc9SdaLtEMOGvPn0";
		JSONObject jsonObject = new ThirdPartyQiDianUtil().getComponentAccessToken(appId, componentAppSecret, ticket);
		assert (jsonObject.get("component_access_token") != null) : "应用开发商token为null " + jsonObject.get("errmsg");
		componentAccessToken = jsonObject.get("component_access_token").toString();
	}

	/**
	 * 使用应用授权code换取应用授权token
	 *
	 * @throws Exception 异常抛出
	 */
	@Test
	public void getAuthorizerAccessToken() throws Exception {
		JSONObject objectAu = new ThirdPartyQiDianUtil().getAuthorizerAccessToken(appId, "8797601b037562528458a0dfc6996766",
				componentAccessToken.toString());
		assert (null != objectAu.get("authorization_info")) : "获取token失败: " + objectAu.get("errmsg");
		JSONObject resJson = JSONObject.parseObject(objectAu.get("authorization_info").toString());
		String authorizerRefreshToken = resJson.get("authorizer_refresh_token").toString();
		assert (null != authorizerRefreshToken) : "刷新token为null";
	}


	/**
	 * 应用授权刷新token
	 *
	 * @throws Exception 异常抛出
	 */
	@Test
	public void getNewAuthorizerAccessToken() throws Exception {
		String authorizer_appId = "202187955";
		String applicationId = "1300000891";
		String authorizer_refresh_token = "167f2612ddb20fe47604b8619161afb0";
		JSONObject jsonObject = new ThirdPartyQiDianUtil().getNewAuthorizerAccessToken(appId, authorizer_appId,
				authorizer_refresh_token, applicationId, componentAccessToken);
		assert (null != jsonObject) : "接口返回值为null";
		assert (null != jsonObject.get("authorizer_access_token")) : "token为null: " + jsonObject.get("errmsg");
	}


}
