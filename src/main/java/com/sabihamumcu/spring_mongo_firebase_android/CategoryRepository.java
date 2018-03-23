package com.sabihamumcu.spring_mongo_firebase_android;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {

    Category findById(String _id);

    @Query("{'altUrun.title':{'$regex':'?0'}}")
    List<Category> findByBaslik(String baslik);

    @Query("{'baslik': ?0}")
    List<Category> findByCategory(String categoryTitle);

}
