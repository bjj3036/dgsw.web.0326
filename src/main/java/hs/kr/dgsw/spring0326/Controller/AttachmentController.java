package hs.kr.dgsw.spring0326.Controller;

import hs.kr.dgsw.spring0326.Domain.User;
import hs.kr.dgsw.spring0326.Protocol.AttachmentProtocol;
import hs.kr.dgsw.spring0326.Repository.UserRepository;
import hs.kr.dgsw.spring0326.Service.CommentService;
import hs.kr.dgsw.spring0326.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@RestController
public class AttachmentController {

    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @PostMapping("/attachment/{id}")
    public AttachmentProtocol upload(@PathVariable Long id, @RequestPart MultipartFile uploadFile) {
        return userService.uploadProfile(id, uploadFile);
    }

    @PostMapping("/attachmentPost")
    public AttachmentProtocol uploadPostImage(@RequestPart MultipartFile uploadFile) {
        return commentService.uploadCommentImage(uploadFile);
    }

    @GetMapping("/attachment/{type}/{id}")
    public void download(@PathVariable String type, @PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if ("user".equals(type))
            userService.showProfile(id, req, res);
        else {
            commentService.showCommentImage(id, req, res);
        }
    }
}
