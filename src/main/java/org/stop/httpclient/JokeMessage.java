package org.stop.httpclient;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class JokeMessage {

    private Integer code;
    private String message;
    private List<Content> result;
}

@Data
@ToString
class Content {
    private String sid;
    private String text;
    private String type;
    private String thumbnail;
    private String video;
    private String images;
    private Integer up;
    private Integer down;
    private Integer forward;
    private Integer comment;
    private String uid;
    private String name;
    private String header;
    private String top_comments_content;
    private String top_comments_voiceuri;
    private String top_comments_uid;
    private String top_comments_name;
    private String top_comments_header;
    private String passtime;

}
