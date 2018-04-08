package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.comment.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CommentDao extends CrudRepository<Comment, Integer> {
    int countAllByProduct(Product product);
    Comment findById(Long id);
    List<Comment> findAllByProduct(Product product);
}
