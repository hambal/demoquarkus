package org.my.group.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {
    public Integer id;
    public String label;
    @JsonIgnore
    public Set<Post> posts = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public Tag(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Tag() {

    }
}
