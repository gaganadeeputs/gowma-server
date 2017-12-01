package org.mihy.gowma.service;

import org.mihy.gowma.model.File;
import org.mihy.gowma.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<File> upload(MultipartFile[] files) {
      return fileRepository.upload(files);
    }
}
