package com.fedorov.chat_v3.services;

import com.fedorov.chat_v3.models.FileData;
import com.fedorov.chat_v3.repositories.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

// Аннотация @Service указывает, что данный класс является сервисом для работы с файлами
@Service
public class FileDataService {

    // Внедрение зависимости репозитория данных файлов
    @Autowired
    private FileDataRepository fileDataRepository;

    // Константа для хранения пути к директории файлов пользователей
    private final String FOLDER_PATH = "C:\\Users\\anton\\IdeaProjects\\chat-v3\\src\\main\\resources\\static\\FileSystem\\Users";

    // Метод для загрузки изображения
    public String uploadImage(MultipartFile file, String username) throws IOException {
        // Создание нового объекта FileData для сохранения информации о файле
        FileData fileData = new FileData();
        // Формирование полного пути для сохранения файла
        String filePath = FOLDER_PATH + "/" + username + "/" + file.getOriginalFilename();

        // Установка свойств объекта fileData
        fileData.setName(file.getOriginalFilename()); // Установка имени файла
        fileData.setUsername(username); // Установка имени пользователя
        fileData.setType(file.getContentType()); // Установка типа файла
        fileData.setPath(filePath); // Установка пути к файлу

        // Сохранение информации о файле в репозитории
        fileDataRepository.save(fileData);

        // Перемещение загруженного файла в указанную директорию
        file.transferTo(new File(filePath));

        // Проверка, был ли файл успешно загружен
        if (fileData != null) {
            return filePath; // Возвращает путь к загруженному файлу
        } else {
            return null; // Если файл не был загружен, возвращает null
        }
    }

    // Метод для скачивания изображения из файловой системы
    public byte[] downloadImageFromFileSystem(String username) throws IOException {
        // Поиск данных о файле по имени пользователя
        Optional<FileData> fileDataOptional = fileDataRepository.findByUsername(username);

        // Проверка, существует ли файл данных для данного пользователя
        if (!fileDataOptional.isPresent()) {
            throw new FileNotFoundException("File data not found for user: " + username); // Если не найден, выбрасывается исключение
        }

        // Получение пути к файлу из объекта FileData
        String filePath = fileDataOptional.get().getPath();
        File file = new File(filePath);

        // Проверка, существует ли файл по указанному пути
        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist at path: " + filePath); // Если не существует, выбрасывается исключение
        }

        // Чтение байтов содержимого файла и возвращение их
        return Files.readAllBytes(file.toPath());
    }
}
