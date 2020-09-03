package org.amrat.hackerNewsDemo.service;

import org.amrat.hackerNewsDemo.entity.Comment;
import org.amrat.hackerNewsDemo.entity.CommentResponse;
import org.amrat.hackerNewsDemo.entity.Story;

import java.util.List;
import java.util.Set;


public interface NewsService {

    Set<Story> getBestStories();

    Set<Story> getPastStories();

    List<CommentResponse> getCommentsById(int storyId);
}
