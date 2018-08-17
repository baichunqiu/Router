package org.basis.network;

import org.json.JSONObject;

/**
 * @author: BaiCQ
 * @ClassName: ParseBean
 * @date: 2018/6/27
 * @Description: Parser 公共信息解析器
 */
public interface Parser {
    ParseBean parse(JSONObject resulObj);
}
