package com.tgrajkowski.model.product.comment;

import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Comment mapToComment(CommentDto commentDto) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Users user = userDao.findByLogin(userLogin);
        Product product = productDao.findById(commentDto.getProductId());
        Comment comment = new Comment(user, commentDto.getMessage());
        comment.setProduct(product);
        return comment;
    }

    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        commentDto.setLogin(userLogin);
        commentDto.setCreatedDate(dateFormat.format(comment.getCreated()));
        commentDto.setMessage(comment.getMessage());
        commentDto.setProductId(comment.getProduct().getId());
        return commentDto;
    }

    public List<CommentDto> mapToCommentDtos(List<Comment> commentList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment: commentList) {
            commentDtoList.add(mapToCommentDto(comment));
        }
        return commentDtoList;
    }
}
