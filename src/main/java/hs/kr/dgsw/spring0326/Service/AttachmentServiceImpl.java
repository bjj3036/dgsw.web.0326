package hs.kr.dgsw.spring0326.Service;

import hs.kr.dgsw.spring0326.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import hs.kr.dgsw.spring0326.Domain.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<String> saveImage(Long id, MultipartFile uploadFile) {
        Optional<User> found = this.userRepository.findById(id);
        if(!found.isPresent())
            return null;
        User foundUser = found.get();
        String rootDirectory = "C:/Users/baejunjae/IdeaProjects/spring0326/src/main/resources/static/";
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + "_"
                + UUID.randomUUID().toString() + "_"
                + uploadFile.getOriginalFilename();
        String destFilename = rootDirectory + fileName;
        File destFile = new File(destFilename);
        try {
            destFile.getParentFile().mkdirs();
            uploadFile.transferTo(destFile);
            String srcPath = "/"+fileName;
            foundUser.setProfilePath(srcPath);
            foundUser.setStoragePath(destFilename);
            this.userRepository.save(foundUser);
            List<String> paths = new ArrayList<>();
            paths.add(destFilename);
            paths.add(srcPath);
            return paths;
        } catch (IOException e) {
            return null;
        }
    }
}
