package com.project.voda.controller;

import com.project.voda.dto.CalendarListResponseDto;
import com.project.voda.dto.DiaryListResponseDto;
import com.project.voda.service.CalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Slf4j
@Api(tags = {"캘린더 API"})
public class CalendarController {

  private final CalendarService calendarService;

  // 한달 감정
  @ApiOperation(value = "한달 감정", notes = "유저 PK 값과 날짜를 보내면 한달치 주요 감정을 반환하는 API")
  @ApiImplicitParams(value = {@ApiImplicitParam(name = "userSeq", value = "user PK"), @ApiImplicitParam(name = "date", value = "'yyyy-MM-dd' Date 값")})
  @GetMapping("/{userSeq}")
  ResponseEntity<?> getCalendars(@PathVariable Long userSeq, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    log.info("userSeq: {}, date: {}", userSeq, date);
    try {
      List<CalendarListResponseDto> response = calendarService.getCalendarAndEmotion(userSeq, date);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return exceptionHandling(e);
    }
  }

  // 하루 일기 목록
  @ApiOperation(value = "하루 일기 목록", notes = "캘린더에서 날짜 선택 시 해당 날짜에 대한 일기 목록을 반환하는 API")
  @ApiImplicitParam(name = "calendarSeq", value = "calendar PK")
  @GetMapping("/diary/{calendarSeq}")
  ResponseEntity<?> getDiarys(@PathVariable Long calendarSeq) {
    log.info("calendarSeq: {}", calendarSeq);
    try {
      List<DiaryListResponseDto> response = calendarService.getDiarys(calendarSeq);
      if (response.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(response, HttpStatus.OK);
      }
    } catch (Exception e) {
      return exceptionHandling(e);
    }
  }

  private ResponseEntity<?> exceptionHandling(Exception e) {
    Map<String, String> map = new HashMap<>();
    map.put("message", e.getMessage());
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
