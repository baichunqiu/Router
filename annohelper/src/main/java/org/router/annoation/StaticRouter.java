package org.router.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: BaiCQ
 * @ClassName: StaticRouter
 * @date: 2018/8/16
 * @Description: StaticRouter 编译静态注解注册
 */
/**
 * @author: BaiCQ
 * @ClassName: AutoRouter
 * @date: 2018/8/16
 * @Description: StaticRouter 静态注解注册 注册时pattern 为该类的全路径类名。
 * 如代码片段：
 *      @StaticRouter(ActivityRule.ACTIVITY_SCHEME + "shop.main")
 *      public class ShopActivity extends Activity {
 *      ......
 *      }
 *      路由跳转调用如下代码：pattern --> ActivityRule.ACTIVITY_SCHEME + "shop.main"
 *
 *      if (Router.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "shop.main") {//判断添加路由
 *          Intent it = Router.invoke(MainActivity.this,
 *              ActivityRule.ACTIVITY_SCHEME + "shop.main");
 *          startActivity(it);
 *       }
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface StaticRouter {
    String value();
}
