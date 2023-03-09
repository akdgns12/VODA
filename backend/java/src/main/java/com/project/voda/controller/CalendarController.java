package com.project.voda.controller;

import com.project.voda.dto.CalendarListResponseDto;
import com.project.voda.service.CalendarService;
import com.project.voda.service.DiaryService;
import com.project.voda.service.UserService;
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
public class CalendarController {

  private final CalendarService calendarService;

  // 한달 감정
  @ApiOperation(value = "한달 감정", notes = "유저 PK 값과 날짜를 보내면 한달치 주요 감정을 반환하는 API")
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

  private ResponseEntity<?> exceptionHandling(Exception e) {
    Map<String, String> map = new HashMap<>();
    map.put("message", e.getMessage());
    return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
