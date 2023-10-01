package com.rest;

import com.rest.domain.model.Category;
import com.rest.domain.model.Post;
import com.rest.domain.model.PostCategory;
import com.rest.domain.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class BlogDataSource {
    public static Set<User> users = new HashSet<>();
    public static Set<Post> posts= new HashSet<>();

    public static Set<Category> categories= new HashSet<>();

    public static Set<PostCategory> postCategories= new HashSet<>();

    public static Integer idIndex = 1;

    @PostConstruct
    public void init(){
        initBlogDataFromCsv();
    }

    private void initBlogDataFromCsv() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new ClassPathResource("blogData.csv").getFile()))) {
            String headerLine = bufferedReader.readLine();
            var line = "";

            while ((line=bufferedReader.readLine())!=null){
                var data = line.split(",");

                //set user
                var user = new User(Integer.valueOf(data[3]),data[4], data[5], data[6]);
                users.add(user);

                //set category
                var category = new Category(Integer.valueOf(data[7]),data[8]);
                categories.add(category);

                //set post
                var post = new Post(Integer.valueOf(data[0]), data[1], data[2], user);
                posts.add(post);

                //set postCategory
                var postCategory = new PostCategory(post,category);
                postCategories.add(postCategory);

                idIndex++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Set<PostCategory> getPostCategories() {
        return postCategories;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Integer getIdIndex() {
        return idIndex;
    }

}