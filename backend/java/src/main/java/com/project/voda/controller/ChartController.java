package com.project.voda.controller;

import com.project.voda.dto.ChartMonthRepositoryDto;
import com.project.voda.dto.ChartWeekResponseDto;
import com.project.voda.service.ChartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/chart")
@Slf4j
@Api(tags = {"감정 곡선 API"})
public class ChartController {

  private final ChartService chartService;

  // 주별 차트
  @ApiOperation(value = "주별 차트", notes = "유저 PK 값과 날짜를 보내면 해당 날짜 포함 이전 7일 조회해서 일주일치 감정별 개수를 반환하는 API")
  @ApiImplicitParams(value = {@ApiImplicitParam(name = "userSeq", value = "user PK"), @ApiImplicitParam(name = "date", value = "'yyyy-MM-dd' Date 값")})
  @GetMapping("/week/{userSeq}")
  ResponseEntity<?> getChartWeek(@PathVariable Long userSeq, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    log.info("userSeq: {}, date: {}", userSeq, date);
    try {
      List<ChartWeekResponseDto> response = chartService.getChartWeek(userSeq, date);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return exceptionHandling(e);
    }
  }

  // 월별 차트
  @ApiOperation(value = "월별 차트", notes = "유저 PK 값과 날짜를 보내면 해당 월의 감정별 개수를 반환하는 API")
  @ApiImplicitParams(value = {@ApiImplicitParam(name = "userSeq", value = "user PK"), @ApiImplicitParam(name = "date", value = "'yyyy-MM-dd' Date 값")})
  @GetMapping("/month/{userSeq}")
  ResponseEntity<?> getChartMonth(@PathVariable Long userSeq, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    log.info("userSeq: {}, date: {}", userSeq, date);
    try {
      ChartMonthRepositoryDto response = chartService.getChartMonth(userSeq, date);
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
