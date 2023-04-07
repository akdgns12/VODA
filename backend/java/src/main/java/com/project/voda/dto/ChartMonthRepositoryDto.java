package com.project.voda.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChartMonthRepositoryDto {

  private List<String> labels;

  private List<Integer> data;

  @Builder
  ChartMonthRepositoryDto(List<String> labels, List<Integer> data) {
    this.labels = labels;
    this.data = data;
  }

}
