package org.amrat.hackerNewsDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  DTO Entity for response to comments API
 */
@AllArgsConstructor
@Data
public class CommentResponse  implements Comparable<CommentResponse>{

    private String text;

    private String userNewsHandle;

    private int oldProfileInYears;

    @JsonIgnore
    private int childSize;

    @Override
    public int compareTo(CommentResponse o) {
        if(childSize==o.getChildSize())
            return 0;
        else if(childSize>o.getChildSize())
            return 1;
        else
            return -1;
    }
}
