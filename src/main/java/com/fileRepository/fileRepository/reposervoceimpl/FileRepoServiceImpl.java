package com.fileRepository.fileRepository.reposervoceimpl;

import com.fileRepository.fileRepository.entity.File;
import com.fileRepository.fileRepository.repository.FileRepository;
import com.fileRepository.fileRepository.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FileRepoServiceImpl implements FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileRepoServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void save(File file) {
        fileRepository.save(file);
    }
}
