package com.fedorov.chat_v3.repositories;

import com.fedorov.chat_v3.models.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Integer> {

    Optional<FileData> findByUsername(String username);
}
