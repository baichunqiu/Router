package org.basis;

import org.basis.network.DefaultParser;
import org.basis.network.Parser;

public class CommonHelper {
    private static Parser mParser;

    /**
     * 设置默认Json解析器
     * @param defaultParser
     */
    public static void setDefaultParser(Parser defaultParser) {
        CommonHelper.mParser = defaultParser;
    }

    public static Parser getParser() {
        if (null == mParser) {//使用默认解析器
            mParser = new DefaultParser();
        }
        return mParser;
    }
}
