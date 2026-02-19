package com.example.demo.controller;

import com.example.demo.dto.NoticeRequest;
import com.example.demo.dto.NoticeResponse;
import com.example.demo.dto.NoticeSearchCondition;
import com.example.demo.model.Notice;
import com.example.demo.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final  NoticeService noticeService;
    //공지사항 수정
    @PutMapping("/{no}")
    public ResponseEntity<NoticeResponse> update(@PathVariable Long no
            ,@Valid @RequestBody NoticeRequest noticeRequest){
        NoticeResponse noticeResponse = noticeService.update(no, noticeRequest);
        return ResponseEntity.ok(noticeResponse);
    }
    //공지사항 삭제
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@PathVariable Long no){
        noticeService.delete(no);
        return ResponseEntity.noContent().build();//204번 상태값 반환됨
    }
    //공지사항 상세보기(select)
    @GetMapping("/{no}")
    public ResponseEntity<NoticeResponse> getOne(@PathVariable Long no){
        NoticeResponse noticeResponse = noticeService.findById(no);
        return ResponseEntity.ok(noticeResponse);
    }
    //공지사항 목록(select)
    @GetMapping("/list")
    public ResponseEntity<List<NoticeResponse>> list(@ModelAttribute NoticeSearchCondition noticeSearchCondition){
        List<NoticeResponse> result = noticeService.search(noticeSearchCondition);
        log.info(result);
        return ResponseEntity.ok(result);
    }

    //공지사항 등록
    //http://localhost:8000/notice
    @PostMapping
    public ResponseEntity<NoticeResponse> create(@Valid @RequestBody NoticeRequest noticeRequest) {
        NoticeResponse response = noticeService.save(noticeRequest);
        log.info(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }//end of create

}
