package org.test.projectjavaservice.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.Court;
import org.test.projectjavaservice.modal.CourtImage;
import org.test.projectjavaservice.repository.CourtRepository;
import org.test.projectjavaservice.service.ManagerSevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerSevice {
    private final CourtRepository courtRepository;
    private final Cloudinary cloudinary;
    @Override
    public List<String> uploadCourtImages(Long courtId, List<MultipartFile> files) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sân"));
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() > 10 * 1024 * 1024) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dung lượng file không được vượt quá 10MB");
            }
            try {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String secureUrl = (String) uploadResult.get("secure_url");
                CourtImage image = new CourtImage();
                image.setCourt(court);
                image.setImageUrl(secureUrl);
                courtRepository.save(image);
                urls.add(secureUrl);
            } catch (IOException e) {
                throw new RuntimeException("Cloudinary upload failed", e);
            }
        }
        return urls;
    }
}