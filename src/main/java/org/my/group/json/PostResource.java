package org.my.group.json;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

@Path("/posts")
@Consumes("application/json")
@Produces("application/json")
public class PostResource {
    @Inject
    PostService service;

    @GET
    public Set<Post> list() {
        return service.posts;
    }

    @GET
    @Path("{id}")
    public Post getPost(@PathParam("id") Integer id) {
        return service.posts.stream().filter(toFind -> toFind.id.equals(id)).findFirst().get();
    }

    @Transactional
    @PUT
    @Path("{id}")
    public Response updatePost(@PathParam("id") Integer id, Post post) {
        Optional<Post> optPost = service.posts.stream().filter(toFind -> toFind.id.equals(id)).findFirst();
        if (optPost.isEmpty()) {
            return Response.status(404).build();
        }
        service.posts.add(post);
        return Response.ok(post).build();
    }

    @Transactional
    @POST
    public Response add(Post post) {
        service.posts.add(post);
        return Response.status(201).build();

    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        if (service.posts.removeIf(existingPost -> existingPost.id.equals(id))) {
            return Response.status(204).build();
        }
        else
            return Response.status(404).build();
    }
}
