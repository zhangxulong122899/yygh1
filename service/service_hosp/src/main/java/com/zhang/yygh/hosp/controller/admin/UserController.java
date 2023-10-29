/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.hosp.controller
 * @Author: 张栩垄
 * @CreateTime: 2023-08-15  18:02
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.hosp.controller.admin;

import com.zhang.yygh.common.result.R;
import com.zhang.yygh.model.acl.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
//解决跨域问题@CrossOrigin //解决跨域问题
public class UserController {

    @PostMapping(value = "/login")
    public R login(@RequestBody User user){
        //暂时不去数据库中查询：用户系统再去数据库查询
        return  R.ok().data("token","admin-token");
    }

    @GetMapping(value = "/info")
    public R info( String token){
        //暂时不去数据库中查询：用户系统再去数据库查询
        return  R.ok().data("roles","[admin]").data("introduction","I am a super administrator")
                .data("name","Super Admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
