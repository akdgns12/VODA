package com.project.voda.controller;

import com.project.voda.service.ChartService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chart")
@Slf4j
@Api(tags = {"감정 곡선 API"})
public class ChartController {

  private final ChartService chartService;

}
