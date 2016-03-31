package fr.clouddev.protobufpowered.rest;

import fr.clouddev.protobufpowered.proto.Github;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.List;

/**
 * Created by CopyCat on 31/03/2016.
 */
public interface GithubService {

    @GET("/repos/{user}/{repo}/issues")
    public List<Github.Issue> fetchIssues(@Path("user")String user, @Path("repo") String repo);
}
