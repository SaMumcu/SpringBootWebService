package com.sabihamumcu.spring_mongo_firebase_android;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.nio.charset.StandardCharsets.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final String TOPIC = "news";
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    AndroidPushNotificationService androidPushNotificationsService;

    @Scheduled(fixedRate = 1800000)
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");
        JSONObject notification = new JSONObject();
        String notificationTitle = "\u00a9 2003-2008 My Company. \u00a9All rights reserved.";
        byte[] ptext = notificationTitle.getBytes(ISO_8859_1);
        String value = new String(ptext, UTF_8);
        notification.put("title", "En Yeniler");
        notification.put("body", "Kendini yenilemek ister misin?");
        notification.put("click_action", "NewProductsActivity");
        List<SubCategory> productList = new ArrayList<SubCategory>();
        List<Category> categoryList = this.categoryRepository.findByCategory("yeniGelenler");

        /*JSONObject data = new JSONObject();
        data.put("title", category.getAltUrun().get(0).getTitle());
        data.put("dataBody", categoryList.get(categoryList.size()-1));*/

        body.put("notification", notification);
        //body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/first")
    public Category getFirst() {
        List<Category> categories = this.categoryRepository.findAll();
        Category category = categories.get(0);
        return category;
    }

    @GetMapping("/allCategories")
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        List<Category> allList = this.categoryRepository.findAll();
        for (int i = 0; i < allList.size(); i++) {
            categories.add(allList.get(i).getBaslik());
        }
        return categories;
    }

    @RequestMapping(value = "/productsInCategory", method = RequestMethod.POST)
    public Category getProductsInCategory(@RequestBody SearchCriteria productTitle) {
        List<SubCategory> productList = new ArrayList<SubCategory>();
        String categoryTitle = productTitle.getSearchCriteria();
        List<Category> categoryList = this.categoryRepository.findByCategory(categoryTitle);
        return categoryList.get(categoryList.size() - 1);
    }

    @RequestMapping(value = "/subCategoriesInCategory", method = RequestMethod.POST)
    public List<String> getSubCategories(@RequestBody SearchCriteria productTitle) {
        String category = productTitle.getSearchCriteria();
        List<String> subCategories = new ArrayList<>();
        List<Category> allList = this.categoryRepository.findAll();
        for (int i = 0; i < allList.size(); i++) {
            if (!allList.get(i).getBaslik().equals("yeniGelenler")) {
                String[] which = allList.get(i).getBaslik().split("/");
                if (which[4].equals(category)) {
                    subCategories.add(allList.get(i).getBaslik());
                }
            }
        }
        return subCategories;
    }

    @GetMapping("/last")
    public Category getLast() {
        List<Category> categories = this.categoryRepository.findAll();
        Category category = categories.get(categories.size() - 1);
        return category;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public List<SubCategory> get(@RequestBody SearchCriteria productTitle) {
        List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
        String arananBaslik = productTitle.getSearchCriteria();
        List<Category> categoryList = this.categoryRepository.findByBaslik(arananBaslik);
        for (Category category : categoryList) {
            for (SubCategory subCategory : category.getAltUrun()) {
                if (subCategory.getTitle().contains(arananBaslik) || subCategory.getTitle().equals(arananBaslik)) {
                    subCategoryList.add(subCategory);
                }
            }
        }
        return subCategoryList;
    }

    @GetMapping("/{_id}")
    public Category getById(@PathVariable("_id") String _id) {
        Category category = this.categoryRepository.findById(_id);
        return category;
    }

}
