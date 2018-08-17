package org.basis.network;

import org.json.JSONObject;

/**
 * @author: BaiCQ
 * @ClassName: DefaultParser
 * @date: 2018/8/17
 * @Description: 默认解析器
 */
public class DefaultParser implements Parser {
    @Override
    public ParseBean parse(JSONObject resulObj) {
        // TODO: 2018/8/17 根据自己的json格式 修改
        ParseBean parseBean = new ParseBean();
        parseBean.setStatus(resulObj.optInt("code"));
        parseBean.setSysMsg(resulObj.optString("codemsg"));
        parseBean.setBody(resulObj.optJSONObject("result").optString("results"));
        return parseBean;
    }
}
