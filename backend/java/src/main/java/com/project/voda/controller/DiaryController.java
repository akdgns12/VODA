package com.project.voda.controller;

import com.project.voda.service.DiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Slf4j
@Api(tags = "다이어리 API - 수정,삭제")
public class DiaryController {

  private final DiaryService diaryService;

  @ApiImplicitParam(name = "diarySeq", value = "diary PK")
  @DeleteMapping("/diary/{diarySeq}")
  ResponseEntity<?> removeDiary(@PathVariable Long diarySeq){
    log.info("delete diary : {}", diarySeq);
    try{
      diaryService.removeDiary(diarySeq);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      return exceptionHandling(e);
    }
  }

  private ResponseEntity<?> exceptionHandling(Exception e) {
    Map<String, String> map = new HashMap<>();
    map.put("message", e.getMessage());
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
