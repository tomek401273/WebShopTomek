package com.tgrajkowski.controller;

import com.tgrajkowski.model.model.dao.CommentDao;
import com.tgrajkowski.model.product.comment.CommentDto;
import com.tgrajkowski.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody
    List<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        return commentService.productComment(commentDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/remove")
    public @ResponseBody
    boolean removeComment(@RequestParam Long commentId) {
        return commentService.removeComment(commentId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    public @ResponseBody
    CommentDto updateComment(@RequestBody CommentDto commentDto) {
       return commentService.updateComment(commentDto);
    }
}
