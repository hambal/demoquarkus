package org.my.group.json;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@ApplicationScoped
public class PostService {
    public Set<Post> posts = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    public Set<Tag> tags = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public PostService() {
        Tag tag1 = new Tag(1, "Tag1");
        tags.add(tag1);
        Post post1 = new Post(1, "Abc", "Content Abc");
        post1.addToTag(tag1);
        posts.add(post1);

        tags.add(new Tag(2, "Tag2"));
        posts.add(new Post(2, "Bce", "Bcd..."));
    }


}
