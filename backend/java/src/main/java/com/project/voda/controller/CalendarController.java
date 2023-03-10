package com.project.voda.controller;

import com.project.voda.service.CalendarService;
import com.project.voda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Slf4j
public class CalendarController {

  private final CalendarService calendarService;

}
