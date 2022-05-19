package com.tencent.qidian.sdk;

import com.alibaba.fastjson.JSONObject;
import com.tencent.qidian.constant.QiDianConstant;
import com.tencent.qidian.util.HttpClientUtil;

/**
 * <p>
 * 第三方个人应用
 * <p>
 * 应用授权token,应用授权刷新token
 * <p>
 * 文档地址: https://api.qidian.qq.com/wiki/doc/open/eaoymkpc4mxk5021dmg1
 *
 * @author Jun
 */
public class ThirdPartyPersonUtil extends CommonUtil {

    /**
     * 应用授权code换取应用授权token
     *
     * @param componentAppId       应用开发商的appId
     * @param authorizationCode    应用授权code,会在授权成功时返回给应用开发者
     * @param componentAccessToken 应用开发商token
     * @return <p style='font-weight: bold'>
     * authorizer_appId 应用授权者的appId <BR>
     * <p style='font-weight: bold'>
     * authorizer_corPuIn 应用授权者的corPuIn <BR>
     * <p style='font-weight: bold'>
     * authorizer_uin 被授权个q对应的主号 <BR>
     * <p style='font-weight: bold'>
     * authorizer_access_token
     * 应用授权token，应用开发商可以使用应用授权token作为access_token访问应用授权方的API能力，范围限定于应用所包含的能力内。即使用应用授权token就可以调用创建应用时申请的各项API。
     * <BR>
     * <p style='font-weight: bold'>
     * expires_in 过期时间，单位秒（s）<BR>
     * <p style='font-weight: bold'>
     * authorizer_refresh_token
     * 应用授权刷新token，应用授权刷新token主要用于应用开发商刷新授权者的应用授权token，只会在授权时提供，请妥善保存。一旦丢失，只能让用户重新授权，才能重新获得。开发者使用应用授权刷新token定时刷新应用授权token，从而使用API获取安装者的相关信息。<BR>
     * <p style='font-weight: bold'>
     * applicationId 应用的id<BR>
     * <p style='font-weight: bold'>
     * userId 被授权个q的openid<BR>
     * <p style='font-weight: bold'>
     * errCode 错误码<BR>
     * <p style='font-weight: bold'>
     * errMsg 错误信息<BR>
     * @throws Exception 异常抛出外层处理
     */
    public JSONObject getAuthorizerPersonalAccessToken(String componentAppId, String authorizationCode,
                                                       String componentAccessToken) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("component_appid", componentAppId);
        jsonParam.put("authorization_code", authorizationCode);
        String result = new HttpClientUtil().sendHttpsRequest(
                QiDianConstant.AUTHORIZER_PERSONAL_ACCESS_TOKEN_URL + componentAccessToken, jsonParam.toJSONString(),
                "post");
        return JSONObject.parseObject(result);
    }

    /**
     * 应用授权刷新token
     *
     * @param userId                 被授权的个人openid
     * @param authorizerUin          被授权个q对应的主号
     * @param authorizerRefreshToken 应用授权刷新token（长期有效）
     * @param sid                    应用的id
     * @param componentAccessToken   应用开发商token
     * @return <p style='font-weight: bold'>
     * authorizer_access_token 应用授权token<BR>
     * <p style='font-weight: bold'>
     * expires_in 过期时间，单位秒（s）<BR>
     * <p style='font-weight: bold'>
     * authorizer_refresh_token 应用授权刷新token（长期有效）<BR>
     * <p style='font-weight: bold'>
     * errCode 错误码<BR>
     * <p style='font-weight: bold'>
     * errMsg 错误信息<BR>
     * @throws Exception 异常抛出外层处理
     */
    public JSONObject getNewAuthorizerPersonalAccessToken(String userId, String authorizerUin,
                                                          String authorizerRefreshToken, String sid, String componentAccessToken) throws Exception {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("user_id", userId);
        jsonParam.put("authorizer_uin", authorizerUin);
        jsonParam.put("authorizer_refresh_token", authorizerRefreshToken);
        jsonParam.put("sid", sid);
        String result = new HttpClientUtil().sendHttpsRequest(
                QiDianConstant.REFRESH_AUTHORIZER_PERSONAL_ACCESS_TOKEN_URL + componentAccessToken,
                jsonParam.toJSONString(), "post");
        return JSONObject.parseObject(result);
    }
}
