package br.com.aperes.blog.service;

import br.com.aperes.blog.dto.BlogPostDTO;
import br.com.aperes.blog.dto.BlogPostSummaryDTO;
import br.com.aperes.blog.dto.CommentDTO;
import br.com.aperes.blog.model.BlogPost;
import br.com.aperes.blog.model.Comment;
import br.com.aperes.blog.repository.BlogPostRepository;
import br.com.aperes.blog.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class BlogPostService {

  private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);

  private final BlogPostRepository blogPostRepository;
  private final CommentRepository commentRepository;

  public BlogPostService(BlogPostRepository blogPostRepository, CommentRepository commentRepository) {
    this.blogPostRepository = blogPostRepository;
    this.commentRepository = commentRepository;
  }

  public List<BlogPostSummaryDTO> getAllPosts() {
    return blogPostRepository.findAll().stream()
        .map(this::convertToSummaryDTO)
        .collect(Collectors.toList());
  }

  public BlogPostDTO getPostById(Long id) {
    BlogPost blogPost = getBlogPostById(id);

    return convertToDTO(blogPost);
  }

  public BlogPostDTO createPost(BlogPostDTO blogPostDTO) {
    BlogPost blogPost = new BlogPost();
    blogPost.setTitle(blogPostDTO.getTitle());
    blogPost.setContent(blogPostDTO.getContent());
    blogPost.setComments(List.of());

    BlogPost savedPost = blogPostRepository.save(blogPost);
    return convertToDTO(savedPost);
  }

  public CommentDTO addComment(Long postId, CommentDTO commentDTO) {
    BlogPost blogPost = getBlogPostById(postId);

    Comment comment = new Comment();
    comment.setContent(commentDTO.getContent());
    comment.setBlogPost(blogPost);

    Comment savedComment = commentRepository.save(comment);
    return convertToDTO(savedComment);
  }

  private BlogPost getBlogPostById(Long id) {
    return blogPostRepository.findById(id)
        .orElseThrow(() -> {
          logger.error("Blog post with ID {} not found", id);
          return new EntityNotFoundException("Blog post not found");
        });
  }

  private BlogPostSummaryDTO convertToSummaryDTO(BlogPost blogPost) {
    return new BlogPostSummaryDTO(blogPost.getId(), blogPost.getTitle(), blogPost.getComments().size());
  }

  private BlogPostDTO convertToDTO(BlogPost blogPost) {
    return new BlogPostDTO(
        blogPost.getId(),
        blogPost.getTitle(),
        blogPost.getContent(),
        blogPost.getComments().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList())
    );
  }

  private CommentDTO convertToDTO(Comment comment) {
    return new CommentDTO(comment.getId(), comment.getContent());
  }
}
