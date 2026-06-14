package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ManagerSevice {
    List<String> uploadCourtImages(Long courtId, List<MultipartFile> files);
}
