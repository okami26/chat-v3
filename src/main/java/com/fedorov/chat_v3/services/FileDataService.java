package com.fedorov.chat_v3.services;

import com.fedorov.chat_v3.models.FileData;
import com.fedorov.chat_v3.models.User;
import com.fedorov.chat_v3.repositories.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileDataService {

    @Autowired
    private FileDataRepository fileDataRepository;


    private final String FOLDER_PATH = "C:\\Users\\anton\\IdeaProjects\\chat-v3\\src\\main\\resources\\static\\FileSystem\\Users";


    public String uploadImage(MultipartFile file, String username) throws IOException {

        FileData fileData = new FileData();
        String filePath = FOLDER_PATH + "/" + username + "/" + file.getOriginalFilename();

        fileData.setName(file.getOriginalFilename());
        fileData.setUsername(username);
        fileData.setType(file.getContentType());

        fileData.setPath(filePath);

        fileDataRepository.save(fileData);

        file.transferTo(new File(filePath));

        if (fileData != null) {

            return filePath;

        }else {

            return null;

        }



    }

    public byte[] downloadImageFromFileSystem(String username) throws IOException {

        Optional<FileData> fileDataOptional = fileDataRepository.findByUsername(username);


        if (!fileDataOptional.isPresent()) {
            throw new FileNotFoundException("File data not found for user: " + username);
        }


        String filePath = fileDataOptional.get().getPath();
        File file = new File(filePath);


        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist at path: " + filePath);
        }


        return Files.readAllBytes(file.toPath());
    }


}
