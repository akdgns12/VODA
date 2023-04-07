package com.project.voda.util;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  @Column(updatable = false)
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDateTime regDtm;

  @LastModifiedDate
  @ColumnDefault("CURRENT_TIMESTAMP")
  private LocalDateTime modDtm;

}
