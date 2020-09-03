package org.amrat.hackerNewsDemo.entity;

import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import java.util.Set;

/**
 *  Entity class for comment
 */
@Data
public class Comment {

    private String by;

    private int id;

    @ElementCollection
    @CollectionTable(name="kids", joinColumns=@JoinColumn(name="storyid"))
    @Column(name="kidsid")
    private Set<Integer> kids;

    private int parent;

    private String text;

    private String time;

    private String type;

}
