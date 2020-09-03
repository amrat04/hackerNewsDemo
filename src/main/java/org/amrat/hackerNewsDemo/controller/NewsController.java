package org.amrat.hackerNewsDemo.controller;

import org.amrat.hackerNewsDemo.entity.CommentResponse;
import org.amrat.hackerNewsDemo.entity.Story;
import org.amrat.hackerNewsDemo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Set;

/**
 *  NewsController : End points for services best-stories, past-stories, comments
 */
@RestController
public class NewsController {

    @Autowired
    NewsService newsService;

    //  Should return the top 10 best stories ranked by score in the last 15 minutes (Read Instructions),
    //  Each story should have title, URL, score, time of submission and the user who submitted it.
    @GetMapping("/best-stories")
    public Set<Story> getBestStories() {
        return newsService.getBestStories();

    }

    //  Should return all the past top stories that were served previously.
    @GetMapping("/past-stories")
    public Set<Story> getPastStories() {

        return newsService.getPastStories();
    }

    //  Should return the top 10 comments on a given story sorted by total number of child comments.
    //  Each comment should contain comment text, user’s hacker news handle and how old the users hacker news profile is in years
    @GetMapping("/comments/{storyid}")
    public List<CommentResponse> getComments(@PathVariable("storyid") int storyid) throws InvalidParameterException {

        return newsService.getCommentsById(storyid);
    }
}
