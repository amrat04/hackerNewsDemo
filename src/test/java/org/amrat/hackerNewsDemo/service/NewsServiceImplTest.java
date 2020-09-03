package org.amrat.hackerNewsDemo.service;

import org.amrat.hackerNewsDemo.entity.Comment;
import org.amrat.hackerNewsDemo.entity.CommentResponse;
import org.amrat.hackerNewsDemo.entity.Story;
import org.amrat.hackerNewsDemo.entity.User;
import org.amrat.hackerNewsDemo.repository.StoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.ServletContext;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class NewsServiceImplTest {

    @InjectMocks
    NewsService newsService;

    @Mock
    RestTemplateService restTemplateService;

    @Mock
    ServletContext context;

    @Mock
    StoryRepository storyRepository;

    final private String STORY_ID_1 = "24323778";

    final private String STORY_ID_2 = "24323779";
    @Before
    public void init() {
        newsService = new NewsServiceImpl();
        ReflectionTestUtils.setField(newsService, "newsFetchInterval", 15);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidInput_thenGetBestStoriesReturns200(){
        // given
        Story story_1 = TestUtils.createStory("mlthoughts2018",36,STORY_ID_1, new HashSet<>(Arrays.asList(24324461, 24324616)),
                336, "1598572670","Zoom still don't understand GDPR","story","http:test.com");
        Story story_2 = TestUtils.createStory("dustingetz",36,STORY_ID_2, new HashSet<>(Arrays.asList(24324462, 24324613)),
                23, "1598572671","Zoom still don't understand GDPR","story","http:test.com");
        Mockito.when(context.getAttribute("bestStories")).thenReturn(new HashMap<Instant, Set<Story>>());
        List<String> stories = Arrays.asList(STORY_ID_1,STORY_ID_2);
        Mockito.when(restTemplateService.getBestStories()).thenReturn(stories);
        Mockito.when(restTemplateService.getLatestStoryById(Integer.valueOf(STORY_ID_1))).thenReturn(story_1);
        Mockito.when(restTemplateService.getLatestStoryById(Integer.valueOf(STORY_ID_2))).thenReturn(story_2);

        // when
        Set<Story> bestStories = newsService.getBestStories();

        // then
        Mockito.verify(restTemplateService, times(1)).getBestStories();
        Mockito.verify(restTemplateService, times(1)).getLatestStoryById(Integer.valueOf(STORY_ID_1));
        Mockito.verify(restTemplateService, times(1)).getLatestStoryById(Integer.valueOf(STORY_ID_2));
        Assertions.assertThat(bestStories).isNotEmpty();
        Assertions.assertThat(bestStories.size()).isEqualTo(2);
        Assertions.assertThat(bestStories.contains(story_1));
        Assertions.assertThat(bestStories.contains(story_2));
    }

    @Test
    public void whenValidInput_thenGetBestStoriesAlreadyStoredInContextReturns200(){
        // given
        Story story_1 = TestUtils.createStory("mlthoughts2018",36,STORY_ID_1, new HashSet<>(Arrays.asList(24324461, 24324616)),
                336, "1598572670","Zoom still don't understand GDPR","story","http:test.com");
        Story story_2 = TestUtils.createStory("dustingetz",36,STORY_ID_2, new HashSet<>(Arrays.asList(24324462, 24324613)),
                23, "1598572671","Zoom still don't understand GDPR","story","http:test.com");
        Set<Story> stories = new HashSet<>();
        stories.add(story_1);
        stories.add(story_2);
        HashMap<Instant, Set<Story>> map = new HashMap<>();
        map.put(Instant.now(), stories);
        Mockito.when(context.getAttribute("bestStories")).thenReturn(map);
        List<String> storiesIdList = Arrays.asList(STORY_ID_1,STORY_ID_2);
        Mockito.when(restTemplateService.getBestStories()).thenReturn(storiesIdList);
        Mockito.when(restTemplateService.getLatestStoryById(Integer.valueOf(STORY_ID_1))).thenReturn(story_1);
        Mockito.when(restTemplateService.getLatestStoryById(Integer.valueOf(STORY_ID_2))).thenReturn(story_2);

        // when
        Set<Story> bestStories = newsService.getBestStories();

        // then
        Mockito.verify(restTemplateService, never()).getBestStories();
        Mockito.verify(restTemplateService, never()).getLatestStoryById(Integer.valueOf(STORY_ID_1));
        Mockito.verify(restTemplateService, never()).getLatestStoryById(Integer.valueOf(STORY_ID_2));
        Assertions.assertThat(bestStories).isNotEmpty();
        Assertions.assertThat(bestStories.size()).isEqualTo(2);
        Assertions.assertThat(bestStories.contains(story_1));
        Assertions.assertThat(bestStories.contains(story_2));
    }

    @Test
    public void whenValidInput_thenGetPastStoriesReturns200(){
        // given
        Story story_1 = TestUtils.createStory("mlthoughts2018",36,STORY_ID_1, new HashSet<>(Arrays.asList(24324461, 24324616)),
                336, "1598572670","Zoom still don't understand GDPR","story","http:test.com");
        Story story_2 = TestUtils.createStory("dustingetz",36,STORY_ID_2, new HashSet<>(Arrays.asList(24324462, 24324613)),
                23, "1598572671","Zoom still don't understand GDPR","story","http:test.com");

        when(storyRepository.findAll()).thenReturn(Arrays.asList(story_1, story_2));

        // when
        Set<Story> pastStories = newsService.getPastStories();

        // then
        Mockito.verify(storyRepository, times(1)).findAll();
        Assertions.assertThat(pastStories).isNotEmpty();
        Assertions.assertThat(pastStories.size()).isEqualTo(2);
        Assertions.assertThat(pastStories.contains(story_1));
        Assertions.assertThat(pastStories.contains(story_2));
    }

    @Test
    public void whenDatabaseIsEmpty_thenGetPastStoriesReturns200(){
        // given
        when(storyRepository.findAll()).thenReturn(null);

        // when
        Set<Story> pastStories = newsService.getPastStories();

        // then
        Mockito.verify(storyRepository, times(1)).findAll();
        Assertions.assertThat(pastStories).isEmpty();
        Assertions.assertThat(pastStories.size()).isEqualTo(0);
    }

    @Test
    public void whenValidStoryId_thenGetCommentReturns200(){
        // given
        Story story_1 = TestUtils.createStory("mlthoughts2018",36,STORY_ID_1, new HashSet<>(Arrays.asList(24324461, 24324616)),
                336, "1598572670","Zoom still don't understand GDPR","story","http:test.com");
        Mockito.when(restTemplateService.getLatestStoryById(Integer.valueOf(STORY_ID_1))).thenReturn(story_1);

        Comment comment_1 = TestUtils.createComment("mlthoughts2018",24324461,new HashSet<>(Arrays.asList(24324606)),24323778, "text1","1598809542");
        Mockito.when(restTemplateService.getCommentById(24324616)).thenReturn(comment_1);
        Comment comment_2 = TestUtils.createComment("andrewnicolalde",24324616,new HashSet<>(Arrays.asList(24324607)),24323778, "text2","1598809542");
        Mockito.when(restTemplateService.getCommentById(24324461)).thenReturn(comment_2);

        User user_1 = TestUtils.createUser("testAbout",1525970478,comment_1.getBy(),"testDelay",394,new HashSet<>(Arrays.asList(24324606)));
        Mockito.when(restTemplateService.getUserByUserId(comment_1.getBy())).thenReturn(user_1);
        User user_2 = TestUtils.createUser("testAbout",1525970478,comment_2.getBy(),"testDelay",394,new HashSet<>(Arrays.asList(24324606)));
        Mockito.when(restTemplateService.getUserByUserId(comment_2.getBy())).thenReturn(user_2);

        // when
        List<CommentResponse> comments = newsService.getCommentsById(Integer.valueOf(STORY_ID_1));

        // then
        Mockito.verify(restTemplateService, times(1)).getLatestStoryById(Integer.valueOf(STORY_ID_1));
        Mockito.verify(restTemplateService,times(1)).getCommentById(24324616);
        Mockito.verify(restTemplateService,times(1)).getCommentById(24324461);

        Mockito.verify(restTemplateService,times(1)).getUserByUserId(comment_1.getBy());
        Mockito.verify(restTemplateService,times(1)).getUserByUserId(comment_2.getBy());

        Assertions.assertThat(comments).isNotEmpty();
        Assertions.assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    public void whenValidStoryIdFromDatabase_thenGetCommentReturns200(){
        // given
        Story story_1 = TestUtils.createStory("mlthoughts2018",36,STORY_ID_1, new HashSet<>(Arrays.asList(24324461, 24324616)),
                336, "1598572670","Zoom still don't understand GDPR","story","http:test.com");
        Mockito.when(storyRepository.findById(Integer.valueOf(STORY_ID_1))).thenReturn(story_1);

        Comment comment_1 = TestUtils.createComment("mlthoughts2018",24324461,new HashSet<>(Arrays.asList(24324606)),24323778, "text1","1598809542");
        Mockito.when(restTemplateService.getCommentById(24324616)).thenReturn(comment_1);
        Comment comment_2 = TestUtils.createComment("andrewnicolalde",24324616,new HashSet<>(Arrays.asList(24324607)),24323778, "text2","1598809542");
        Mockito.when(restTemplateService.getCommentById(24324461)).thenReturn(comment_2);

        User user_1 = TestUtils.createUser("testAbout",1525970478,comment_1.getBy(),"testDelay",394,new HashSet<>(Arrays.asList(24324606)));
        Mockito.when(restTemplateService.getUserByUserId(comment_1.getBy())).thenReturn(user_1);
        User user_2 = TestUtils.createUser("testAbout",1525970478,comment_2.getBy(),"testDelay",394,new HashSet<>(Arrays.asList(24324606)));
        Mockito.when(restTemplateService.getUserByUserId(comment_2.getBy())).thenReturn(user_2);

        // when
        List<CommentResponse> comments = newsService.getCommentsById(Integer.valueOf(STORY_ID_1));

        // then
        Mockito.verify(storyRepository, times(1)).findById(Integer.valueOf(STORY_ID_1));
        Mockito.verify(restTemplateService,times(1)).getCommentById(24324616);
        Mockito.verify(restTemplateService,times(1)).getCommentById(24324461);

        Mockito.verify(restTemplateService,times(1)).getUserByUserId(comment_1.getBy());
        Mockito.verify(restTemplateService,times(1)).getUserByUserId(comment_2.getBy());

        Assertions.assertThat(comments).isNotEmpty();
        Assertions.assertThat(comments.size()).isEqualTo(2);
    }

    @Test(expected = InvalidParameterException.class)
    public void whenInValidStoryId_thenGetCommentReturnException() throws InvalidParameterException {
        String invalid_StoryId = "987987987";
        // when
        newsService.getCommentsById(Integer.valueOf(invalid_StoryId));

        // then
    }
}
