import com.zhang.yygh.cmn.ServiceCmnApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: PACKAGE_NAME
 * @Author: 张栩垄
 * @CreateTime: 2023-08-27  21:48
 * @Description: 描述
 * @Version: 1.0
 */
@SpringBootTest(classes = ServiceCmnApplication.class)
@RunWith(SpringRunner.class)
public class MyTest {

    @Test
    public void  test1(){
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("zhangsa");
        objects.add("lisi");
        String s = objects.toString();
        System.out.println(s);
    }
}
