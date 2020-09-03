package org.amrat.hackerNewsDemo.entity;

import lombok.Data;

/**
 *  Entity class for User
 */
import java.util.Set;
@Data
public class User {

    private String id;

    private String delay;

    private long created;

    private int karma;

    private String about;

    private Set<Integer> submitted;
}
