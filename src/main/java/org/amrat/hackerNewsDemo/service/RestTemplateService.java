package org.amrat.hackerNewsDemo.service;

import org.amrat.hackerNewsDemo.entity.Comment;
import org.amrat.hackerNewsDemo.entity.Story;
import org.amrat.hackerNewsDemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Calling News Backend Services.
 */
@Service
public class RestTemplateService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${hackers.base.url}")
    private String baseUrl;

    private String BEST_STORIES_URL = "v0/beststories.json";
    private String GET_STORY_URL = "v0/item/";
    private String GET_USER_URL = "/v0/user/";

    public List<String> getBestStories() {
        ResponseEntity<List<String>> responseEntity;

        try {
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(baseUrl + BEST_STORIES_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseEntity.getBody();
    }

    public Story getLatestStoryById(int storyId) {
        ResponseEntity<Story> responseEntity;
        try {
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(baseUrl + GET_STORY_URL+storyId+".json", HttpMethod.GET, null, Story.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseEntity.getBody();
    }

    public Comment getCommentById(int commentId) {
        ResponseEntity<Comment> responseEntity;
        try {
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(baseUrl + GET_STORY_URL+commentId+".json", HttpMethod.GET, null, Comment.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseEntity.getBody();
    }

    public User getUserByUserId(String userId) {
        ResponseEntity<User> responseEntity;
        try {
            HttpEntity<User> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(baseUrl + GET_USER_URL + userId + ".json", HttpMethod.GET, null, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return responseEntity.getBody();
    }
}
