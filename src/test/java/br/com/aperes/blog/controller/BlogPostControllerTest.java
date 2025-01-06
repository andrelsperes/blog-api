package br.com.aperes.blog.controller;


import br.com.aperes.blog.dto.BlogPostDTO;
import br.com.aperes.blog.dto.BlogPostSummaryDTO;
import br.com.aperes.blog.dto.CommentDTO;
import br.com.aperes.blog.service.BlogPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BlogPostControllerTest {

  private MockMvc mockMvc;

  @Mock
  private BlogPostService blogPostService;

  @InjectMocks
  private BlogPostController blogPostController;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(blogPostController).build();
  }

  @Test
  void getAllPosts_ShouldReturnPosts() throws Exception {
    List<BlogPostSummaryDTO> posts = List.of(new BlogPostSummaryDTO(1L, "Test Post", 5));

    when(blogPostService.getAllPosts()).thenReturn(posts);

    mockMvc.perform(get("/api/v1/posts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].title").value("Test Post"))
        .andExpect(jsonPath("$[0].commentCount").value(5));
  }

  @Test
  void createPost_ShouldReturnCreatedPost() throws Exception {
    BlogPostDTO newPost = new BlogPostDTO(null, "Test Post", "Test Content", List.of());
    BlogPostDTO createdPost = new BlogPostDTO(1L, "Test Post", "Test Content", List.of());

    when(blogPostService.createPost(any(BlogPostDTO.class))).thenReturn(createdPost);

    mockMvc.perform(post("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPost)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.title").value("Test Post"))
        .andExpect(jsonPath("$.content").value("Test Content"));
  }

  @Test
  void addComment_ShouldReturnCreatedComment() throws Exception {
    CommentDTO newComment = new CommentDTO(null, "New Comment");
    CommentDTO createdComment = new CommentDTO(1L, "New Comment");

    when(blogPostService.addComment(eq(1L), any(CommentDTO.class))).thenReturn(createdComment);

    mockMvc.perform(post("/api/v1/posts/1/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newComment)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.content").value("New Comment"));
  }
}