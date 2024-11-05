package com.springboot.enotes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.enotes.Entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {

}
