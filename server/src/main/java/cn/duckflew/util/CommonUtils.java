package cn.duckflew.util;

import cn.duckflew.config.exception.CommonJsonException;
import cn.duckflew.util.constants.Constants;
import cn.duckflew.util.constants.ErrorEnum;
import com.alibaba.fastjson.JSONObject;

public class CommonUtils
{

    public static void hasAllRequired(JSONObject reqJson, String requiredColumns)
    {
        if (!StringTools.isNullOrEmpty(requiredColumns))
        {
            String[] columns = requiredColumns.split(",");
            String missColumn="";
            for (String column : columns)
            {
                Object object = reqJson.get(column.trim());
                if (StringTools.isNullOrEmpty(object))
                {
                    missColumn+=column+" ";
                }
            }
            if (!StringTools.isNullOrEmpty(missColumn))
            {
                reqJson.clear();
                reqJson.put("code", ErrorEnum.E_90003.getErrorCode());
                reqJson.put("msg",ErrorEnum.E_90003.getErrorMsg()+missColumn.trim());
                reqJson.put("info",new JSONObject());
                throw new CommonJsonException(reqJson);
            }
        }
    }

    public static JSONObject errorJson(ErrorEnum errorEnum)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",errorEnum.getErrorCode());
        jsonObject.put("msg",errorEnum.getErrorMsg());
        jsonObject.put("info",new JSONObject());
        return jsonObject;
    }

    public static JSONObject successJson()
    {
      return   successJson(new JSONObject());
    }
    public static JSONObject successJson(Object info)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", Constants.SUCCESS_CODE);
        jsonObject.put("msg",Constants.SUCCESS_MSG);
        jsonObject.put("info",info);
        return  jsonObject;
    }
}
