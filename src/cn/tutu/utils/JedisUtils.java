package cn.tutu.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * redis工具类
 *
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
public class JedisUtils {

    private static JedisPool pool = null;

    static {
        // 加载配置文件
        InputStream inputStream = JedisUtils.class.getClassLoader().getResourceAsStream("redis.properties");
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 获得池子对象
        JedisPoolConfig JedisPoolConfig = new JedisPoolConfig();
        JedisPoolConfig.setMaxIdle(Integer.parseInt(prop.getProperty("redis.maxIdle")));   // 最大闲置数
        JedisPoolConfig.setMinIdle(Integer.parseInt(prop.getProperty("redis.minIdle")));   // 最小闲置数
        JedisPoolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("redis.maxTotal")));   // 最大连接数

        pool = new JedisPool(JedisPoolConfig, prop.getProperty("redis.url"), Integer.parseInt(prop.getProperty("redis.port")));
    }

    // 获得jedis的方法
    public static Jedis getJedis() {
        return pool.getResource();   // 获得一个Jedis资源
    }
}
