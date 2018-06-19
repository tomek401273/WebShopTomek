package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.CommentDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.comment.Comment;
import com.tgrajkowski.model.product.comment.CommentDto;
import com.tgrajkowski.model.product.comment.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.CommentToken;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ProductDao productDao;

    public List<CommentDto> productComment(CommentDto commentDto) {
        Product product = productDao.findById(commentDto.getProductId());
        Comment comment = commentMapper.mapToComment(commentDto);
        commentDao.save(comment);
        List<Comment> commentList = commentDao.findAllByProduct(product);
        return commentMapper.mapToCommentDtos(commentList);
    }

    @Transactional
    public boolean removeComment(Long commentId) {
        Comment comment = commentDao.findById(commentId);
        Product product = comment.getProduct();
        List<Comment> commentListProduct = product.getComments();
        commentListProduct.remove(comment);
        product.setComments(commentListProduct);
        commentDao.delete(comment);
        return true;
    }

    @Transactional
    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = commentDao.findById(commentDto.getId());
        comment.setMessage(commentDto.getMessage());
        Comment comment2 = commentDao.findById(commentDto.getId());
        CommentDto commentDtoUpdated= commentMapper.mapToCommentDto(comment2);
        return commentDtoUpdated;
    }
}
