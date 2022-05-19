# 企点开放平台 SDK

## 使用方法

```
//1.完善服务器配置
String echoStr = new CommonUtil().verifyUrl(token, signature, timeStamp, nonce, echoStr);

//2.解析xml推送内容
CommonUtil commonUtil = new CommonUtil();
String result = commonUtil.decryptXml(encodingAesKey, token, msgSignature, timeStamp, nonce, xmlTexts);

//3.获取自建应用的token
JSONObject jsonObject = new SelfBuildUtil().getSelfBuildToken(sid, appId, secret);
String accessToken = jsonObject.get("access_token").toString();

//4.获取ticket票据
CommonUtil commonUtil = new CommonUtil();
Map<String, Object> map = commonUtil.getVerifyTicket(encodingAesKey, token, msgSignature, timeStamp, nonce, xmlTexts);
String ticket = map.get("ComponentVerifyTicket").toString();

//5.根据ticket换取应用开发商token
JSONObject jsonObject = new ThirdPartyQiDianUtil().getComponentAccessToken(appId, componentAppSecret, ticket);
String componentAccessToken = jsonObject.get("component_access_token").toString();

//6.获取第三方应用的企业授权code，并换取企业授权token
JSONObject objectAu = new ThirdPartyQiDianUtil().getAuthorizerAccessToken(componentAppId, authorizationCode, componentAccessToken);

//7.刷新企业授权token
JSONObject jsonObject = new ThirdPartyQiDianUtil().getNewAuthorizerAccessToken(componentAppId, authorizerAppId, authorizerRefreshToken, applicationId, componentAccessToken);
```

## 开发文档

https://api.qidian.qq.com/wiki