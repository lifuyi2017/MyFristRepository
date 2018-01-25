package cn.lifuyi.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 网站：https://www.cnblogs.com/zzj0410/p/7196505.html
 * Created by lifuyi on 2018/1/12 0012.
 * 主要分为三层，最上层是CacheManager，它是操作Ehcache的入口。可以通过CacheManager.getInstance()获得一个单子的CacheManager，
 * 或者通过CacheManager的构造函数创建一个新的CacheManager。每个CacheManger都管理多个Cache。每个Cache都以一种类Hash的方式，
 * 关联多个Element。Element就是我们用于存放缓存内容的地方。
 * <p>
 * 此拦截器用于查询时查看cache中是否有相关的值
 */
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {
    //用于日志打印的logger
    private static final Log logger = LogFactory.getLog(MethodCacheInterceptor.class);
    private Cache cache;

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public MethodCacheInterceptor() {
        super();
    }

    /**
     * @Author:lifuyi
     * @Descripution:拦截Service/DAO 的方法，并查找该结果是否存在，如果存在就返回cache 中的值
     * 否则，返回数据库查询结果，并将查询结果放入cache
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        Object result;
        logger.debug("Find object from cache is " + cache.getName());
        String cacheKey = getCacheKey(targetName, methodName, arguments);
        //查询cache
        Element element = cache.get(cacheKey);
        if (element == null) {
            logger.debug("Hold up method , Get method result and create cache........!");
            //执行Dao层方法方法
            result = invocation.proceed();
            //设置cache
            element = new Element(cacheKey, (Serializable) result);
            cache.put(element);
        }
        return element.getObjectValue();
    }

    /**
     * @Author:lifuyi
     * @Descripution:获得cache key 的方法，cache key 是Cache 中一个Element 的唯一标识,
     * cache key 包括包名+类名+方法名，如com.co.cache.service.UserServiceImpl.getAllUser
     */
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sb.append(".").append(arguments[i]);
            }
        }
        return sb.toString();
    }

    /**
     * @Author:lifuyi
     * @Descripution:implement InitializingBean，检查cache 是否为空
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");
    }
}
