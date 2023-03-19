package com.example.cloudStorage.repositories;

import com.example.cloudStorage.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    List<FileEntity> findByUserNameOrderByFilename(String userName);
    Optional<FileEntity> findByFilenameAndUserName(String filename, String userName);
}
