package com.mountBlue.canduitBoot.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "excerpt")
    private String excerpt;

    @Column(name = "content")
    private String content;

    @Column(name = "author")
    private String author;

    @Column(name = "publish_at")
    private String publish_at;

    @Column(name = "is_published")
    private boolean is_published;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;

    @Column(name = "tags")
    private String tags;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Eager
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // persist, merge
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tags> tagList = new HashSet<>();


    public Post() {
    }

    public Post(String title, String excerpt, String content, String author, String publish_at, boolean is_published,
                String created_at, String updated_at, String tags) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        this.publish_at = publish_at;
        this.is_published = is_published;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish_at() {
        return publish_at;
    }

    public void setPublish_at(String publish_at) {
        this.publish_at = publish_at;
    }

    public boolean isIs_published() {
        return is_published;
    }

    public void setIs_published(boolean is_published) {
        this.is_published = is_published;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Set<Tags> getTagList() {
        return tagList;
    }

    public void setTagList(Set<Tags> tagList) {
        this.tagList = tagList;
    }

    public void addTag(Tags tempTags) {
        if (tagList == null) {
            tagList = new HashSet<>();
        }
        tagList.add(tempTags);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", publish_at='" + publish_at + '\'' +
                ", is_published=" + is_published +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", tags='" + tags + '\'' +
                ", user=" + user +
                ", tagList=" + tagList +
                '}';
    }
}
