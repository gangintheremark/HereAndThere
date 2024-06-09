package com.ssafy.s3;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ProductAwsS3Service {
    String fileUpload(String category, MultipartFile uploadFile) throws IOException;
}

