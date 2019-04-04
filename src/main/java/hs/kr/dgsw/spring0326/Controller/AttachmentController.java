package hs.kr.dgsw.spring0326.Controller;

import hs.kr.dgsw.spring0326.Service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/attachment/{id}")
    public List<String> upload(@PathVariable Long id, @RequestPart MultipartFile uploadFile) {
        return attachmentService.saveImage(id, uploadFile);
    }
}
