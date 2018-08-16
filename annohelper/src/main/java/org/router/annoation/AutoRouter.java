package org.router.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: BaiCQ
 * @ClassName: AutoRouter
 * @date: 2018/8/16
 * @Description: AutoRouter 自动注解注册路由 注册时pattern 为该类的全路径类名。
 * 如代码片段：
 *      @AutoRouter
 *      public class ShopActivity extends Activity {//package org.shoplib
 *      ......
 *      }
 *      路由跳转调用如下代码：pattern --> ActivityRule.ACTIVITY_SCHEME + "org.shoplib.ShopActivity"
 *
 *      if (Router.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "org.shoplib.ShopActivity")) {//判断添加路由
 *          Intent it = Router.invoke(MainActivity.this,
 *              ActivityRule.ACTIVITY_SCHEME + "org.shoplib.ShopActivity");
 *          startActivity(it);
 *       }
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface AutoRouter {
}
