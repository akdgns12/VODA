package com.project.voda.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChartWeekResponseDto {

  private String label;
  private List<Integer> data;

  @Builder
  private ChartWeekResponseDto(String label, List<Integer> data) {
    this.label = label;
    this.data = data;
  }

}
