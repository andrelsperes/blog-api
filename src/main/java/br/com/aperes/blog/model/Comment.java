package br.com.aperes.blog.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  @ManyToOne
  @JoinColumn(name = "blog_post_id", nullable = false)
  private BlogPost blogPost;


  public Comment() {
  }

  public Comment(Long id, String content, BlogPost blogPost) {
    this.id = id;
    this.content = content;
    this.blogPost = blogPost;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public BlogPost getBlogPost() {
    return blogPost;
  }

  public void setBlogPost(BlogPost blogPost) {
    this.blogPost = blogPost;
  }
}
