package hs.kr.dgsw.spring0326.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    List<String> saveImage(Long id, MultipartFile uploadFile);
}
