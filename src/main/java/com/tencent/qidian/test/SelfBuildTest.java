package com.tencent.qidian.test;


import com.alibaba.fastjson.JSONObject;
import com.tencent.qidian.sdk.SelfBuildUtil;
import org.junit.Test;


/**
 * 自建应用测试
 *
 * @author Jun
 */
public class SelfBuildTest {


    /**
     * 获取自建应用token
     *
     * @throws Exception 异常抛出
     */
    @Test
    public void getSelfBuildToken() throws Exception {
        //开发者appId
        String appId = "202144643";
        //应用secret
        String secret = "Sdc70b9fe76";
        //应用ID
        String sid = "1300000845";
        JSONObject jsonObject = new SelfBuildUtil().getSelfBuildToken(sid, appId, secret);
        assert (jsonObject.get("access_token") != null) : "access_token获取失败: " + jsonObject.get("errmsg");
    }

}
