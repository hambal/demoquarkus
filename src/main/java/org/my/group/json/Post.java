package org.my.group.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Post {
    public Integer id;
    public String title;
    public String content;
    public Set<Tag> tags = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public Post(Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Post() {

    }

    public Post(Integer id, String title, String content, Tag tag) {
        this(id,title,content);
        tags.add(tag);
    }

    public void addToTag(Tag tag) {
        this.tags.add(tag);
        tag.posts.add(this);
    }

    public void removeFromTag(Tag tag) {
        this.tags.removeIf(existTag -> existTag.id.equals(tag.id));
        tag.posts.remove(this);
    }
}
