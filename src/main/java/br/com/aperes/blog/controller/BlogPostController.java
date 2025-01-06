package br.com.aperes.blog.controller;


import br.com.aperes.blog.dto.BlogPostDTO;
import br.com.aperes.blog.dto.BlogPostSummaryDTO;
import br.com.aperes.blog.dto.CommentDTO;
import br.com.aperes.blog.service.BlogPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Blog Posts", description = "Endpoints to manage blog posts")
public class BlogPostController {

  private final BlogPostService blogPostService;

  public BlogPostController(BlogPostService blogPostService) {
    this.blogPostService = blogPostService;
  }

  @GetMapping
  @Operation(summary = "List all posts", description = "Retrieve a list of all blog posts")
  public ResponseEntity<List<BlogPostSummaryDTO>> getAllPosts() {
    List<BlogPostSummaryDTO> posts = blogPostService.getAllPosts();
    return ResponseEntity.ok(posts);
  }

  @PostMapping
  @Operation(summary = "Create a new post", description = "Add a new post to the blog")
  public ResponseEntity<BlogPostDTO> createPost(@RequestBody BlogPostDTO blogPost) {
    BlogPostDTO createdPost = blogPostService.createPost(blogPost);
    return ResponseEntity.ok(createdPost);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get post by ID", description = "Retrieve a blog post by its ID")
  public ResponseEntity<BlogPostDTO> getPostById(@PathVariable Long id) {
    BlogPostDTO blogPost = blogPostService.getPostById(id);
    return ResponseEntity.ok(blogPost);
  }

  @PostMapping("/{id}/comments")
  @Operation(summary = "Add a comment", description = "Add a new comment to a blog post")
  public ResponseEntity<CommentDTO> addComment(@PathVariable Long id, @RequestBody CommentDTO comment) {
    CommentDTO createdComment = blogPostService.addComment(id, comment);
    return ResponseEntity.ok(createdComment);
  }
}
