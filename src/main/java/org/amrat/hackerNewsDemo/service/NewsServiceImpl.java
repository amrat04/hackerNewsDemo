package org.amrat.hackerNewsDemo.service;

import org.amrat.hackerNewsDemo.entity.Comment;
import org.amrat.hackerNewsDemo.entity.CommentResponse;
import org.amrat.hackerNewsDemo.entity.Story;
import org.amrat.hackerNewsDemo.entity.User;
import org.amrat.hackerNewsDemo.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    RestTemplateService restTemplateService;

    @Autowired
    ServletContext context;

    @Value("${news.latest.fetch.interval}")
    private int newsFetchInterval;

    @Autowired
    StoryRepository storyRepository;

    @Override
    public Set<Story> getBestStories() {
        Set<Story> bestStorySet = new HashSet<>();
        HashMap<Instant, Set<Story>> getMap = (HashMap<Instant, Set<Story>>) context.getAttribute("bestStories");
        if(Optional.ofNullable(context.getAttribute("bestStories")).isPresent()) {
            for (Map.Entry<Instant, Set<Story>> entry : getMap.entrySet()) {

                Instant currentTime = Instant.now();
                Duration timeElapsed = Duration.between(entry.getKey(), currentTime);
                System.out.println("Minutes" + timeElapsed.toMinutes());
                if (timeElapsed.toMinutes() < newsFetchInterval) {
                    bestStorySet = entry.getValue();
                    return bestStorySet;
                }
            }
        }

        List<String> allStories = restTemplateService.getBestStories();

        TreeSet<Story> storyList = new TreeSet<>();
        for (String storyId : allStories) {
            Story story = restTemplateService.getLatestStoryById(Integer.parseInt(storyId));
            storyList.add(story);
        }
        bestStorySet = storyList.stream().sorted().limit(10).collect(Collectors.toSet());
        storyRepository.saveAll(bestStorySet);
        HashMap<Instant, Set<Story>> map = new HashMap<>();
        map.put(Instant.now(), bestStorySet);

        context.setAttribute("bestStories", map);

        return bestStorySet;
    }

    @Override
    public Set<Story> getPastStories() {
        List<Story> storiesList = storyRepository.findAll();
        if (storiesList == null)
            return new HashSet<>();
         else
            return new HashSet<>(storiesList);
    }

    @Override
    public List<CommentResponse> getCommentsById(int storyId) {
        Story story = getStoryById(storyId);
        if(story == null){
            story = restTemplateService.getLatestStoryById(storyId);
            if(story == null){
                throw new InvalidParameterException("Invalid story Id. Story Id does not exists");
            }
        }
        List<CommentResponse> commentList = new ArrayList<>();
        for(int childId : story.getKids()){
            Comment comment = restTemplateService.getCommentById(childId);

            User user = restTemplateService.getUserByUserId(comment.getBy());

            LocalDate date = Instant.ofEpochSecond(user.getCreated()).atZone(ZoneId.systemDefault())
                    .toLocalDate();
            int oldAccountYear = LocalDate.now().getYear()-date.getYear();

            int size = comment.getKids() == null ? 0 : comment.getKids().size();
            commentList.add(new CommentResponse(comment.getText(), comment.getBy(), oldAccountYear, size));
        }
        Collections.sort(commentList);
        commentList = commentList.stream().sorted().limit(10).collect(Collectors.toList());
        return commentList;
    }

    private Story getStoryById(int storyId){
        return storyRepository.findById((long)storyId);
    }
}
