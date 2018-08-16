package org.router.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: BaiCQ
 * @ClassName: Components
 * @date: 2018/8/16
 * @Description: Components 一般注解在主module的appliacation 上面
 *      注意和Component的对应关系 需包含所有的Component
 *      示例代码：
 *
 *      @Component("shoplib")
 *      @Component("bbslib")
 *           ....
 *          --->  包含 shoplib,bbslib
 *       @Components({"shoplib", "bbslib"})
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Components {
    String[] value();
}
