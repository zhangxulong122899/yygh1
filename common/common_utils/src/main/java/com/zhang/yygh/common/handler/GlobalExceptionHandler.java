/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.common.handler
 * @Author: 张栩垄
 * @CreateTime: 2023-08-09  17:34
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.common.handler;

import com.zhang.yygh.common.exception.YyghException;
import com.zhang.yygh.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice //凡是由@ControllerAdvice 标记的类表示全局异常处理
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    // @ResponseBody
    public R handlerException(Exception ex){
        ex.printStackTrace();//输出异常 日志文件
        log.error(ex.getMessage());
        return R.error().message(ex.getMessage());//失败
    }

    @ExceptionHandler(value = SQLException.class) //细粒度的异常处理
    public R handlerSqlException(SQLException ex){
        ex.printStackTrace();//输出异常 日志文件
        log.error(ex.getMessage());
        return R.error().message("SQL处理异常");//失败
    }

    @ExceptionHandler(value = ArithmeticException.class) //细粒度的异常处理
    public R handlerSqlException(ArithmeticException ex){
        ex.printStackTrace();//输出异常 日志文件
        log.error(ex.getMessage());
        return R.error().message("数学异常");//失败
    }

    @ExceptionHandler(value = RuntimeException.class) //细粒度的异常处理
    public R handlerSqlException(RuntimeException ex){
        ex.printStackTrace();//输出异常 日志文件
        log.error(ex.getMessage());
        return R.error().message("编译异常");//失败
    }

    @ExceptionHandler(value = YyghException.class) //细粒度的异常处理
    public R handlerSqlException(YyghException ex){
        ex.printStackTrace();//输出异常 日志文件
        log.error(ex.getMessage());
        return R.error().message(ex.getMessage()).code(ex.getCode());//失败
    }

}
