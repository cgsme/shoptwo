package cn.tutu.utils;

import java.util.UUID;

/**
 * 通用工具类
 *
 * Created by 曹贵生 on 2017/5/21.
 * Email: 1595143088@qq.com
 */
public class CommonsUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
