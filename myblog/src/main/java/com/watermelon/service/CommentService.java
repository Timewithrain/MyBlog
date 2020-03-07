package com.watermelon.service;

import com.watermelon.DAO.CommentRepository;
import com.watermelon.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private List<Comment> replies = new ArrayList<Comment>();

    //此方法不开启事务，并且以只读方式获取数据，修改对象属性后数据库不做修改
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Comment> listCommentByBlogId(Long blogId){
        Sort sort = Sort.by(Sort.Direction.ASC,"createTime");
        List<Comment> list = commentRepository.findByBlogIdAndAndParentCommentNull(blogId,sort);
        return listStructuredComment(list);
    }

    private List<Comment> listStructuredComment(List<Comment> topComments){
        //遍历顶级留言
        for(Comment topComment : topComments){
            List<Comment> replies2nd = topComment.getReplies();
            //顶级留言的回复不为空则获取其所有回复
            if(replies2nd.size()>0){
                //获取所有根节点为此顶级留言的子留言并保存于replies
                ChildReplies(replies2nd);
                topComment.setReplies(replies);
                //清空replies以获取下一个顶级留言的子留言
                replies = new ArrayList<Comment>();
            }
        }
        return topComments;
    }

    private void ChildReplies(List<Comment> replies2nd){
        //遍历所有的子留言，并将其添加进入replies
        for (Comment reply : replies2nd) {
            replies.add(reply);
            List<Comment> replies3rd = reply.getReplies();
            //若此子留言包含有下一级子留言则递归调用自身获取所有的子留言
            if(replies3rd.size()>0){
                ChildReplies(replies3rd);
            }
        }
    }

    public Comment saveComment(Comment comment){
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return  commentRepository.save(comment);
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

}
