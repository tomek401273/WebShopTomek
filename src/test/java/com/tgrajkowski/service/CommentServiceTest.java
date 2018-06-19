package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.CommentDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.comment.Comment;
import com.tgrajkowski.model.product.comment.CommentDto;
import com.tgrajkowski.model.product.comment.CommentMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @InjectMocks
    CommentService commentService;

    @Mock
    private CommentDao commentDao;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private ProductDao productDao;

    private long idOne = 1;

    @Test
    public void productCommentEmptyListTest() {
        // Given
        Product product = new Product();
        Mockito.when(productDao.findById(idOne)).thenReturn(product);
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();
        Mockito.when(commentMapper.mapToComment(commentDto)).thenReturn(comment);
        List<Comment> commentList = new ArrayList<>();
        Mockito.when(commentDao.findAllByProduct(product)).thenReturn(commentList);
        CommentDto commentDto2 = new CommentDto();

        List<CommentDto> commentDtoList1 = new ArrayList<>();
        Mockito.when(commentMapper.mapToCommentDtos(commentList)).thenReturn(commentDtoList1);
        // when
        List<CommentDto> commentDtoList2 = commentService.productComment(commentDto2);
        // Then
        Assert.assertEquals(0, commentDtoList2.size());
    }

    @Test
    public void productComment() {
        // Given
        Product product = new Product();
        Mockito.when(productDao.findById(idOne)).thenReturn(product);

        Comment comment = new Comment();
        comment.setMessage("message");
        CommentDto commentDto = new CommentDto();
        Mockito.when(commentMapper.mapToComment(commentDto)).thenReturn(comment);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        Mockito.when(commentDao.findAllByProduct(product)).thenReturn(commentList);
        CommentDto commentDto2 = new CommentDto();
        commentDto2.setProductId(idOne);
        commentDto2.setMessage("message");

        List<CommentDto> commentDtoList1 = new ArrayList<>();
        commentDtoList1.add(commentDto2);
        Mockito.when(commentMapper.mapToCommentDtos(commentList)).thenReturn(commentDtoList1);
        // when
        List<CommentDto> commentDtoList2 = commentService.productComment(commentDto2);
        // Then
        Assert.assertEquals(1, commentDtoList2.size());
        Assert.assertEquals("message", commentDtoList2.get(0).getMessage());
    }

    @Test
    public void removeComment() {
        // Given
        Comment comment = new Comment();
        Product product = new Product();
        product.setId(idOne);
        comment.setProduct(product);
        product.getComments().add(comment);
        Mockito.when(commentDao.findById(idOne)).thenReturn(comment);
        // When
        boolean retrieved = commentService.removeComment(idOne);
        // Then
        Assert.assertTrue(retrieved);
        Assert.assertEquals(0, product.getComments().size());
    }

    @Test
    public void updateComment() {
        // Given
        Comment comment = new Comment();
        Mockito.when(commentDao.findById(idOne)).thenReturn(comment);
        CommentDto commentDto1 = new CommentDto();
        commentDto1.setMessage("message2");
        Mockito.when(commentMapper.mapToCommentDto(comment)).thenReturn(commentDto1);
        CommentDto commentDto2 = new CommentDto();
        commentDto2.setId(idOne);
        commentDto2.setMessage("message2");
        // When
        CommentDto retrieved = commentService.updateComment(commentDto2);
        // Then
        Assert.assertEquals("message2", retrieved.getMessage());
    }
}