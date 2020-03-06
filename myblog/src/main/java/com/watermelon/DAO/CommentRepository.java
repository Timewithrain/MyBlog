package com.watermelon.DAO;

import com.watermelon.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    //根据BlogId查找所有parentComment为空的评论
    List<Comment> findByBlogIdAndAndParentCommentNull(Long blogId, Sort sort);

}
