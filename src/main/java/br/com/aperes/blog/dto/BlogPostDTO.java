package br.com.aperes.blog.dto;

import java.util.List;



public class BlogPostDTO {
  private Long id;
  private String title;
  private String content;
  private List<CommentDTO> comments;

  public BlogPostDTO() {
  }

  public BlogPostDTO(Long id, String title, String content, List<CommentDTO> comments) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.comments = comments;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<CommentDTO> getComments() {
    return comments;
  }

  public void setComments(List<CommentDTO> comments) {
    this.comments = comments;
  }
}
