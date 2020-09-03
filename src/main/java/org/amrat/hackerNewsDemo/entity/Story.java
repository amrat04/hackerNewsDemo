package org.amrat.hackerNewsDemo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 *  Entity class for Story
 */
@Data
@Entity
@Table(name = "story")
public class Story implements Comparable<Story>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    @Column(name="byauthor")
    private String by;

    private long descendants;

    @Column(name="newsid")
    private long id;

    @ElementCollection
    @CollectionTable(name="kids", joinColumns=@JoinColumn(name="storyid"))
    @Column(name="kidsid")
    private Set<Integer> kids;

    private int score;

    @Column(name="newstime")
    private String time;

    @Column(name="newstitle")
    private String title;

    @Column(name="newstype")
    private String type;

    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Story)) return false;
        Story story = (Story) o;
        return getDescendants() == story.getDescendants() &&
                getId() == story.getId() &&
                getScore() == story.getScore() &&
                getBy().equals(story.getBy()) &&
                getKids().equals(story.getKids()) &&
                getTime().equals(story.getTime()) &&
                getTitle().equals(story.getTitle()) &&
                getType().equals(story.getType()) &&
                getUrl().equals(story.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public int compareTo(Story st){
        if(score==st.score)
            return 0;
        else if(score>st.score)
            return 1;
        else
            return -1;
    }

    @Override
    public String toString() {
        return "Story{" +
                "sid=" + sid +
                ", by='" + by + '\'' +
                ", descendants=" + descendants +
                ", id=" + id +
                ", kids=" + kids +
                ", score=" + score +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
