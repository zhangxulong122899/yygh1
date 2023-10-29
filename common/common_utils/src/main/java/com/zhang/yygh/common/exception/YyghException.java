/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.common.exception
 * @Author: 张栩垄
 * @CreateTime: 2023-08-10  09:38
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YyghException extends RuntimeException{

    private Integer code;
    private String message;



}
