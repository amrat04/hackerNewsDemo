package org.amrat.hackerNewsDemo.service;

import org.amrat.hackerNewsDemo.entity.Comment;
import org.amrat.hackerNewsDemo.entity.Story;
import org.amrat.hackerNewsDemo.entity.User;

import java.util.Set;

public class TestUtils {

    public static Story createStory(String by, int descendants, String story_id, Set<Integer> kids, int score, String time,
                              String title, String type, String url) {
        Story story = new Story();
        story.setBy(by);
        story.setDescendants(descendants);
        story.setId(Long.parseLong(story_id));

        story.setKids(kids);
        story.setScore(score);
        story.setTime(time);
        story.setTitle(title);
        story.setType(type);
        story.setUrl(url);
        return story;
    }

    public static Comment createComment(String by, int id, Set<Integer> kids, int parent, String text, String time){
        Comment comment = new Comment();
        comment.setBy(by);
        comment.setId(id);
        comment.setKids(kids);
        comment.setParent(parent);
        comment.setText(text);
        comment.setTime(time);
        comment.setType("comment");
        return  comment;
    }

    public static User createUser(String about, long created, String id, String delay, int karma, Set<Integer> submitted){
        User user = new User();
        user.setAbout(about);
        user.setCreated(created);
        user.setId(id);
        user.setDelay(delay);
        user.setKarma(karma);
        user.setSubmitted(submitted);
        return user;
    }
}
