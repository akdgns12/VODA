package com.project.voda.service;

import com.project.voda.dto.ChartWeekResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface ChartService {

  // 주별 차트
  List<ChartWeekResponseDto> getChartWeek(Long userSeq, LocalDate date);

}
