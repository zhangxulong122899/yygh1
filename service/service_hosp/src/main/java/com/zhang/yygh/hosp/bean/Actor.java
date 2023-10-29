/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.bean
 * @Author: 张栩垄
 * @CreateTime: 2023-09-11  08:28
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private String id;
    private String  actorName;
    private boolean gender;
    private Date birth;
}
