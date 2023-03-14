package com.project.voda.repository;

import com.project.voda.domain.Calendar;
import com.project.voda.domain.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

  // 한달 감정
  List<Calendar> findByUserAndDayBetween(User user, LocalDate fromDate, LocalDate toDate);

}
