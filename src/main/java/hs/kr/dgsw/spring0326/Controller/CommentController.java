package hs.kr.dgsw.spring0326.Controller;

import hs.kr.dgsw.spring0326.Domain.Comment;
import hs.kr.dgsw.spring0326.Domain.User;
import hs.kr.dgsw.spring0326.Protocol.CommentUsernameProtocol;
import hs.kr.dgsw.spring0326.Service.CommentService;
import hs.kr.dgsw.spring0326.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/listComments")
    public List<CommentUsernameProtocol> listComments() {
        return this.commentService.listAllComments();
    }

    @GetMapping("/listByUserId/{id}")
    public List<CommentUsernameProtocol> listByUserId(@PathVariable Long id) {
        return this.commentService.listByUserId(id);
    }

    @GetMapping("/findCommentById/{id}")
    public CommentUsernameProtocol findCommentById(@PathVariable Long id) {
        return this.commentService.findCommentById(id);
    }

    @PostMapping("/saveComment")
    public CommentUsernameProtocol saveComment(@RequestBody Comment comment) {
        return this.commentService.saveComment(comment);
    }

    @PutMapping("/updateComment")
    public CommentUsernameProtocol updateComment(@RequestBody Comment comment) {
        return this.commentService.updateComment(comment);
    }

    @DeleteMapping("/deleteComment/{id}")
    public boolean deleteComment(@PathVariable Long id) {
        return this.commentService.deleteComment(id);
    }
}
