package com.example.demo.service;

import com.example.demo.dto.NoticeRequest;
import com.example.demo.dto.NoticeResponse;
import com.example.demo.dto.NoticeSearchCondition;
import com.example.demo.model.Notice;
import com.example.demo.repository.NoticeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeResponse save(@Valid NoticeRequest noticeRequest) {
        Notice notice = new Notice();
        notice.setTitle(noticeRequest.getTitle());
        notice.setContent(noticeRequest.getContent());
        notice.setWriter(noticeRequest.getWriter());
        Notice saved = noticeRepository.save(notice);
        return NoticeResponse.builder()
                .no(saved.getNo())
                .writer(saved.getWriter())
                .content(saved.getContent())
                .createDate(saved.getCreateDate())
                .title(saved.getTitle())
                .build();
    }//end of save
    @Transactional
    public NoticeResponse update(Long no, @Valid NoticeRequest noticeRequest) {
        Notice notice =
                noticeRepository.findById(no)
                        .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 있습니다. no="+no));
        notice.setTitle(noticeRequest.getTitle());
        notice.setContent(noticeRequest.getContent());
        notice.setWriter(noticeRequest.getWriter());
        Notice updated = noticeRepository.save(notice);
        return NoticeResponse.builder()
                .no(updated.getNo())
                .title(updated.getTitle())
                .writer(updated.getWriter())
                .content(updated.getContent())
                .build();
    }//end of update
    @Transactional
    public void delete(Long no) {
        if(!noticeRepository.existsById(no)){
            throw new IllegalArgumentException("공지사항을 찾을 수 없습니다. no="+no);
        }
        noticeRepository.deleteById(no);
    }
    @Transactional(readOnly = true)
    public NoticeResponse findById(Long no) {
        Notice notice = noticeRepository.findById(no)
                .orElseThrow(()-> new IllegalArgumentException("공지사항을 찾을 수 없습니다. no="+no));
        return NoticeResponse.builder()
                .no(notice.getNo())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .createDate(notice.getCreateDate())
                .build();
    }//end of findById

    @Transactional(readOnly = true)
    public List<NoticeResponse> search(NoticeSearchCondition noticeSearchCondition) {
        //검색조건 꺼내기
        String gubun = noticeSearchCondition.getGubun();
        String keyword = noticeSearchCondition.getKeyword();
        log.info(gubun +", " + keyword);
        List<Notice> notices;
        Sort orderByNoDesc = Sort.by(Sort.Direction.DESC, "no");
        if("title".equals(gubun)){
            notices = noticeRepository.findByTitleContainingOrderByNoDesc(keyword);
        }else if("writer".equals(gubun)){
            notices = noticeRepository.findByWriterContainingOrderByNoDesc(keyword);
        }else if("content".equals(gubun)){
            notices = noticeRepository.findByContentContainingOrderByNoDesc(keyword);
        }else{
            notices = noticeRepository.findAll(orderByNoDesc);
        }
        //Entity -> DTO변환
        List<NoticeResponse> result = new ArrayList<>();
        for (Notice notice : notices) {
            NoticeResponse noticeResponse = NoticeResponse.builder()
                    .no(notice.getNo())
                    .title(notice.getTitle())
                    .writer(notice.getWriter())
                    .content(notice.getContent())
                    .createDate(notice.getCreateDate())
                    .build();
                    result.add(noticeResponse);
        }//end of for
        return result;
    }//end of search
}
