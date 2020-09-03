package org.amrat.hackerNewsDemo.service;


import org.amrat.hackerNewsDemo.entity.Comment;
import org.amrat.hackerNewsDemo.entity.Story;
import org.amrat.hackerNewsDemo.entity.User;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@SpringBootTest
public class RestTemplateServiceTest {

    @InjectMocks
    RestTemplateService restTemplateService;

    @Mock
    RestTemplate restTemplate;

    final private String STORY_ID_1 = "24323778";

    final private String STORY_ID_2 = "24323779";

    @Before
    public void init() {
        restTemplateService = new RestTemplateService();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidInput_thenGetBestStoriesReturns200(){
        //  given
        List<String> stories = Arrays.asList(STORY_ID_1,STORY_ID_2);
        ResponseEntity<List<String>> myEntity = new ResponseEntity<List<String>>(stories, HttpStatus.ACCEPTED);

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<String>>>any())
        ).thenReturn(myEntity);
        //  when
        List<String> bestStories=  restTemplateService.getBestStories();

        //  then
        Assertions.assertThat(bestStories).isNotEmpty();
        Assertions.assertThat(bestStories.size()).isEqualTo(2);
    }

    @Test
    public void whenValidInput_thenGetStoryByIdReturns200(){
        //  given
        Story story_1 = TestUtils.createStory("mlthoughts2018",36,STORY_ID_1, new HashSet<>(Arrays.asList(24324461, 24324616)),
                336, "1598572670","Zoom still don't understand GDPR","story","http:test.com");
        ResponseEntity<Story> myEntity = new ResponseEntity<Story>(story_1, HttpStatus.ACCEPTED);

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Story>>any())
        ).thenReturn(myEntity);
        //  when
        Story story =  restTemplateService.getLatestStoryById(Integer.valueOf(STORY_ID_1));

        //  then
        Assertions.assertThat(story).isNotNull();
        Assert.assertEquals(story.getId(), story_1.getId());

    }

    @Test
    public void whenValidInput_thenGetCommentByIdReturns200(){
        //  given
        Comment comment = TestUtils.createComment("mlthoughts2018",24324461,
                new HashSet<>(Arrays.asList(24324606)),24323778, "text1","1598809542");

        ResponseEntity<Comment> commentResponseEntity = new ResponseEntity<Comment>(comment, HttpStatus.ACCEPTED);

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Comment>>any())
        ).thenReturn(commentResponseEntity);
        //  when
        Comment commentResponse =  restTemplateService.getCommentById(comment.getId());

        //  then
        Assertions.assertThat(commentResponse).isNotNull();
        Assert.assertEquals(commentResponse.getId(), comment.getId());
    }

    @Test
    public void whenValidInput_thenGetUserByUserIdReturns200(){
        //  given
        User user = TestUtils.createUser("testAbout",1525970478,"mlthoughts2018","testDelay",
                394,new HashSet<>(Arrays.asList(24324606)));

        ResponseEntity<User> commentResponseEntity = new ResponseEntity<User>(user, HttpStatus.ACCEPTED);

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<User>>any())
        ).thenReturn(commentResponseEntity);
        //  when
        User userResponse =  restTemplateService.getUserByUserId(user.getId());

        //  then
        Assertions.assertThat(userResponse).isNotNull();
        Assert.assertEquals(userResponse.getId(), user.getId());
    }
}
