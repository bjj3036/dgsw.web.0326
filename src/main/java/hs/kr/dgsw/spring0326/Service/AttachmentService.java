package hs.kr.dgsw.spring0326.Service;

import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    String saveImage(Long id, MultipartFile uploadFile);
}
