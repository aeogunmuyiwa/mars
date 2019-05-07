package com.mars.repository;

import com.mars.domain.BlogPost;
import com.mars.domain.enumeration.BlogPostType;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Spring Data JPA repository for the BlogPost entity.
 */
@SuppressWarnings("unused")
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

	@Query("select blogPost from BlogPost blogPost where blogPost.author.login = ?#{principal.username}")
	List<BlogPost> findByAuthorIsCurrentUser();

	Page<BlogPost> findAllByType(BlogPostType type, Pageable pageable);

}
