package hac.ex5.service;

import hac.ex5.model.Post;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PostService {
    public List<Post> getPosts() {

        return Arrays.asList(
                new Post(1L, "Post One", "Content for post one"),
                new Post(2L, "Post Two", "Content for post two"),
                new Post(3L, "Post Three", "Content for post three")
        );
    }
}