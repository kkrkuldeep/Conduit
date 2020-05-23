package com.mountBlue.canduitBoot.DAO;

import com.mountBlue.canduitBoot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>  {

    @Query(value = "select * from posts where is_published = true", nativeQuery = true)
    Page<Post> findByIs_publishedTrue(Pageable pageable);

    @Query(value = "select * from posts where is_published = false", nativeQuery = true)
    Page<Post> findByIs_publishedFalse(Pageable pageable);

    @Query(value =
            "select p from Post p where p.is_published = true and concat(p.tags,p.title,p.excerpt,p.content,p.author)" +
                    " like %?1%")
    Page<Post> findBySearchString(String title, Pageable pageable);

    @Query(value =
            "select p from Post p where p.is_published = true and p.tags " +
                    " like %?1%")
    Page<Post> findByTags(String tag, Pageable pageable);
}
