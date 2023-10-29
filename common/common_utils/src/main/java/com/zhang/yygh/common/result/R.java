/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.common.config.result
 * @Author: 张栩垄
 * @CreateTime: 2023-08-07  19:43
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class R {
    private Integer code;
    private String message;
    private boolean success;
    private Map<String,Object> data = new HashMap<String, Object>();

    private R(){}

    public static R ok(){
        R r = new R();
        r.setCode(20000);
        r.setMessage("成功");
        r.setSuccess(true);
        return r;
    }

    public static R error(){
        R r = new R();
        r.setCode(200001);
        r.setMessage("失败");
        r.setSuccess(false);
        return r;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R success(boolean success){
        this.setSuccess(success);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

}
