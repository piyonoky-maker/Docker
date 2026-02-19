package com.example.demo.repository;

import com.example.demo.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 제목에 keyword가 포함된 공지 목록(no 내림차순= 최신순)
    List<Notice> findByTitleContainingOrderByNoDesc(String keyword);
    // 작성자에 keyword가 포함된 공지 목록(no 내림차순= 최신순)
    List<Notice> findByWriterContainingOrderByNoDesc(String keyword);
    // 내용에 keyword가 포함된 공지 목록(no 내림차순= 최신순)
    List<Notice> findByContentContainingOrderByNoDesc(String keyword);
}
