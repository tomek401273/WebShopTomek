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
        return commentMapper.mapToCommentDtos(commentDao.findAllByProduct(product));
    }

    @Transactional
    public boolean removeComment(Long commentId) {
        Comment comment = commentDao.findById(commentId);
        Product product = comment.getProduct();
        List<Comment> commentListProduct = product.getComments();
        commentListProduct.remove(comment);
        product.setComments(commentListProduct);
//        productDao.save(product);
        commentDao.delete(comment);
        return true;
    }

// objekt musi pochodzić z bazy
    @Transactional
    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = commentDao.findById(commentDto.getId());
        comment.setMessage(commentDto.getMessage());
//        commentDao.save(comment);
        CommentDto commentDtoUpdated= commentMapper.mapToCommentDto(commentDao.findById(commentDto.getId()));
        return commentDtoUpdated;
    }
}
