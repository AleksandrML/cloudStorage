package com.example.cloudStorage.repositories;

import com.example.cloudStorage.models.FileEntity;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    List<FileEntity> findByUserNameOrderByFilename(String userName);
    Optional<FileEntity> findByFilenameAndUserName(String filename, String userName);
    Long deleteByUserNameAndFilename(String userName, String filename);
    @Modifying
    @Query(value = "UPDATE files SET filename = :filenameNew WHERE filename = :filenameOld and user_name = :userName",
            nativeQuery = true)
    void updateFilename(@Param("filenameNew") String filenameNew,
                        @Param("userName") String userName,
                        @Param("filenameOld") String filenameOld);
}
