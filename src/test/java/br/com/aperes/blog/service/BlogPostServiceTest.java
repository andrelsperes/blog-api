package br.com.aperes.blog.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.aperes.blog.dto.BlogPostDTO;
import br.com.aperes.blog.dto.BlogPostSummaryDTO;
import br.com.aperes.blog.dto.CommentDTO;
import br.com.aperes.blog.model.BlogPost;
import br.com.aperes.blog.model.Comment;
import br.com.aperes.blog.repository.BlogPostRepository;
import br.com.aperes.blog.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BlogPostServiceTest {

  private final BlogPostRepository blogPostRepository = mock(BlogPostRepository.class);
  private final CommentRepository commentRepository = mock(CommentRepository.class);
  private final BlogPostService blogPostService = new BlogPostService(blogPostRepository, commentRepository);

  @Test
  void getAllPosts_ShouldReturnPostSummaries() {
    BlogPost blogPost = new BlogPost();
    blogPost.setId(1L);
    blogPost.setTitle("Test Post");
    blogPost.setComments(List.of(new Comment()));

    when(blogPostRepository.findAll()).thenReturn(List.of(blogPost));

    List<BlogPostSummaryDTO> summaries = blogPostService.getAllPosts();

    assertEquals(1, summaries.size());
    assertEquals("Test Post", summaries.get(0).getTitle());
    assertEquals(1, summaries.get(0).getCommentCount());

    verify(blogPostRepository, times(1)).findAll();
  }

  @Test
  void getPostById_ShouldReturnPostWithComments() {
    BlogPost blogPost = new BlogPost();
    blogPost.setId(1L);
    blogPost.setTitle("Test Post");
    blogPost.setContent("Test Content");
    blogPost.setComments(List.of(new Comment(1L, "Test Comment", null)));

    when(blogPostRepository.findById(1L)).thenReturn(Optional.of(blogPost));

    BlogPostDTO dto = blogPostService.getPostById(1L);

    assertEquals("Test Post", dto.getTitle());
    assertEquals("Test Content", dto.getContent());
    assertEquals(1, dto.getComments().size());
    assertEquals("Test Comment", dto.getComments().get(0).getContent());
  }

  @Test
  void getPostById_ShouldThrowException_WhenPostNotFound() {
    when(blogPostRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> blogPostService.getPostById(1L));

    verify(blogPostRepository, times(1)).findById(1L);
  }

  @Test
  void addComment_ShouldAddCommentToPost() {
    BlogPost blogPost = new BlogPost();
    blogPost.setId(1L);

    when(blogPostRepository.findById(1L)).thenReturn(Optional.of(blogPost));

    CommentDTO commentDTO = new CommentDTO(null, "New Comment");
    Comment savedComment = new Comment(1L, "New Comment", blogPost);

    when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(savedComment);

    CommentDTO result = blogPostService.addComment(1L, commentDTO);

    assertEquals("New Comment", result.getContent());
    verify(commentRepository, times(1)).save(Mockito.any(Comment.class));
  }

}