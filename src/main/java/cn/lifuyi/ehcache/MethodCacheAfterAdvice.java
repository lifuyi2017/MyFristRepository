package cn.lifuyi.ehcache;

import net.sf.ehcache.Cache;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 网站：http://blog.csdn.net/u010955843/article/details/51692639
 * Created by lifuyi on 2018/1/12 0012.
 * 建立一个拦截器MethodCacheAfterAdvice，作用是在用户进行create/update/delete操作时来刷新、remove相关cache内容，
 * 这个拦截器需要实现AfterRetruningAdvice接口，将会在所拦截的方法执行后执行在afterReturning（object arg0，Method arg1，Object[] arg2,object arg3)
 * 方法中所预定的操作
 * <p>
 * 一般缓存在执行create/update/delete之后都需要刷新
 */
public class MethodCacheAfterAdvice implements AfterReturningAdvice, InitializingBean {
    private static final Log logger = LogFactory.getLog(MethodCacheInterceptor.class);
    private Cache cache;

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public MethodCacheAfterAdvice() {
        super();
    }

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        //获取类名,并查找cache中这个类的键，并移除
        String className = o1.getClass().getName();
        List list = cache.getKeys();
        for (int i = 0; i < list.size(); i++) {
            String cacheKey = String.valueOf(list.get(i));
            if (cacheKey.startsWith(className)) {
                cache.remove(cacheKey);
                logger.debug("remove cache " + cacheKey);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");
    }
}
