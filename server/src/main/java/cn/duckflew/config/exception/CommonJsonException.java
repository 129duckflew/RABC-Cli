package cn.duckflew.config.exception;

import cn.duckflew.util.CommonUtils;
import cn.duckflew.util.constants.ErrorEnum;
import com.alibaba.fastjson.JSONObject;

public class CommonJsonException extends RuntimeException
{

    private final JSONObject resJson;

    public CommonJsonException(ErrorEnum errorEnum) {
        this.resJson = CommonUtils.errorJson(errorEnum);
    }

    public CommonJsonException(JSONObject resultJson) {
        this.resJson = resultJson;
    }

    public JSONObject getResJson()
    {
        return resJson;
    }
}
