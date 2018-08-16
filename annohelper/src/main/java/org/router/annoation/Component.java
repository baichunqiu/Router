package org.router.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: BaiCQ
 * @ClassName: Component
 * @date: 2018/8/16
 * @Description: Component的备注
 *  实例代码：
 *      @Component("shoplib")
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Component {
    String value();
}
