package org.amrat.hackerNewsDemo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 *  Entity class for Kids, part of Story and Comment
 */
@Data
@Entity
@Table(name = "kids")
public class Kids {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int storyid;

    private int kidsid;

}
