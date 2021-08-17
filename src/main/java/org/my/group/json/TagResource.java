package org.my.group.json;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

@Path("/tags")
@Consumes("application/json")
@Produces("application/json")
public class TagResource {
    @Inject
    PostService service;

    @GET
    public Set<Tag> list() {
        return service.tags;
    }

    @GET
    @Path("{id}")
    public Tag getTag(@PathParam("id") Integer id) {
        return service.tags.stream().filter(toFind -> toFind.id.equals(id)).findFirst().get();
    }

    @GET
    @Path("{id}/posts")
    public Set<Post> tagsGetPosts(@PathParam("id") Integer id) {
        return service.tags.stream().filter(toFind -> toFind.id.equals(id)).findFirst().get().posts;
    }

    @Transactional
    @PUT
    @Path("{id}")
    public Response updateTags(@PathParam("id") Integer id, Tag tag) {
        Optional<Tag> optTag = service.tags.stream().filter(toFind -> toFind.id.equals(id)).findFirst();
        if (optTag.isEmpty()) {
            return Response.status(404).build();
        }
        service.tags.add(tag);
        return Response.ok(tag).build();
    }

    @Transactional
    @POST
    public Response add(Tag tag) {
        service.tags.add(tag);
        return Response.status(201).build();

    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        if (service.tags.removeIf(existingTag -> existingTag.id.equals(id)))
            return Response.status(204).build();
        else
            return Response.status(404).build();
    }

    @Transactional
    @PUT
    @Path("{id}/posts/{postId}")
    public Response tagsAddPost(@PathParam("id") Integer id, @PathParam("postId") Integer postId) {
        Optional<Tag> optTag = service.tags.stream().filter(toFind -> toFind.id.equals(id)).findFirst();
        if (optTag.isEmpty()) {
            return Response.status(404).build();
        }
        Optional<Post> optPost = service.posts.stream().filter(toFind -> toFind.id.equals(postId)).findFirst();
        if (optPost.isEmpty()) {
            return Response.status(404).build();
        }
        optPost.get().addToTag(optTag.get());
        return Response.ok().build();
    }

    @Transactional
    @DELETE
    @Path("{id}/posts/{postId}")
    public Response tagsDeletePost(@PathParam("id") Integer id, @PathParam("postId") Integer postId) {
        Optional<Tag> optTag = service.tags.stream().filter(toFind -> toFind.id.equals(id)).findFirst();
        if (optTag.isEmpty()) {
            return Response.status(404).build();
        }
        Optional<Post> optPost = service.posts.stream().filter(toFind -> toFind.id.equals(postId)).findFirst();
        if (optPost.isEmpty()) {
            return Response.status(404).build();
        }
        optPost.get().removeFromTag(optTag.get());
        return Response.noContent().build();
    }
}
