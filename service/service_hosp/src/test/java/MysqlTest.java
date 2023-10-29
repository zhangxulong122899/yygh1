import com.mongodb.client.result.DeleteResult;
import com.zhang.yygh.hosp.ServiceHospApplication;
import com.zhang.yygh.hosp.bean.Actor;
import com.zhang.yygh.hosp.mapper.HospitalSetMapper;
import com.zhang.yygh.hosp.repository.ActorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;


/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: PACKAGE_NAME
 * @Author: 张栩垄
 * @CreateTime: 2023-08-06  12:31
 * @Description: 描述
 * @Version: 1.0
 */


@SpringBootTest(classes = ServiceHospApplication.class)
@RunWith(SpringRunner.class)
public class MysqlTest {

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ActorRepository actorRepository;

    @Test
    public void  test1(){
        System.out.println("hospitalSetMapper.selectById(1) = " + hospitalSetMapper.selectById(1));
    }

    @Test
    public void test2(){
        // mongoTemplate.insert(new Actor("2","张学友",true,new Date()));
        Actor actor = new Actor();
        actor.setId("2");
        actor.setActorName("郭富城");
        mongoTemplate.save(actor);
    }

    @Test
    public void test3(){
        Actor actor = mongoTemplate.findById("111", Actor.class);
        actor.setActorName("朱丽倩");
        mongoTemplate.save(actor);
    }

    @Test
    public void test4(){
        ArrayList<Actor> actors = new ArrayList<>();
        actors.add(new Actor("11","关之琳",false,new Date()));
        actors.add(new Actor("12","关晓彤",false,new Date()));
        actors.add(new Actor("13","张柏芝",false,new Date()));
        mongoTemplate.insert(actors,Actor.class);
    }

    @Test
    public void test5(){
        Query query = new Query(Criteria.where("actorName").is("郭富城"));
        DeleteResult remove = mongoTemplate.remove(query, Actor.class);
        System.out.println(remove.getDeletedCount());
    }

    @Test
    public void  test6(){
        // List<Actor> actors = actorRepository.findByActorName("张柏芝");
        // System.out.println(actors.toString());
        float money =  0.01f;
        // double a = 0;
        for (int i = 0; i < 30; i++) {
            money += money * 2;
        }
        System.out.println(String.format("%.2f", money));

    }


}

