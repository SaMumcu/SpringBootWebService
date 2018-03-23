package com.sabihamumcu.spring_mongo_firebase_android;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBSeeder implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    public DBSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Category> categories=categoryRepository.findAll();
    }
}
