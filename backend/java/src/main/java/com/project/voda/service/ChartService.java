package com.project.voda.service;

import com.project.voda.dto.ChartMonthRepositoryDto;
import com.project.voda.dto.ChartWeekResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface ChartService {

  // 주별 차트
  List<ChartWeekResponseDto> getChartWeek(Long userSeq, LocalDate date);

  // 월별 차트
  ChartMonthRepositoryDto getChartMonth(Long userSeq, LocalDate date);
}
