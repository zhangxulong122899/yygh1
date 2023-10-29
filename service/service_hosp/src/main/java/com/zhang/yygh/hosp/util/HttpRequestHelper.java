/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.util
 * @Author: 张栩垄
 * @CreateTime: 2023-09-13  20:18
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequestHelper {

    public static Map<String, Object> switchMap(Map<String, String[]> parameterMap) {
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue()[0];
            resultMap.put(key,value);
        }
        return resultMap;
    }
}
