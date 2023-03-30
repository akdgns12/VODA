package com.project.voda.controller;

import com.project.voda.dto.DiaryDetailResponseDto;
import com.project.voda.service.DiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Slf4j
@Api(tags = "다이어리 API - 읽기, 삭제")
public class DiaryController {

  private final DiaryService diaryService;

  @ApiImplicitParam(name = "diarySeq", value = "diary PK")
  @DeleteMapping("/{diarySeq}")
  ResponseEntity<?> removeDiary(@PathVariable Long diarySeq){
    log.info("delete diary : {}", diarySeq);
    try{
      if(diaryService.removeDiary(diarySeq)) {
        log.info("success delete diary : {}", diarySeq);
      } else {
        log.info("already deleted diary : {}", diarySeq);
      }
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      return exceptionHandling(e);
    }
  }

  @ApiOperation(value = "하루 일기 상세", notes = "diary pk 값을 통해 diary의 문장별 감정, 전체 감정을 반환")
  @ApiImplicitParam(name="diarySeq", value = "diary PK")
  @GetMapping("/{diarySeq}")
  ResponseEntity<?> getDiaryDetail(@PathVariable Long diarySeq){
    log.info("diarySeq : {}", diarySeq);
    try {
      DiaryDetailResponseDto response = diaryService.getDiaryDetail(diarySeq);
      if (response==null){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(response,HttpStatus.OK);
      }
    } catch(Exception e){
      return exceptionHandling(e);
    }
  }

  private ResponseEntity<?> exceptionHandling(Exception e) {
    Map<String, String> map = new HashMap<>();
    map.put("message", e.getMessage());
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
