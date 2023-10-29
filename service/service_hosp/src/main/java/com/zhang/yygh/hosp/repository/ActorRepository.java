package com.zhang.yygh.hosp.repository;

import com.zhang.yygh.hosp.bean.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActorRepository extends MongoRepository<Actor,String > {
    public List<Actor> findByActorName(String name);
}
