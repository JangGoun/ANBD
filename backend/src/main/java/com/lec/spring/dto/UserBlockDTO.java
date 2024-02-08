package com.lec.spring.dto;

import com.lec.spring.domain.UserBlockId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBlockDTO {

    private Integer userId;
    private Integer targetId;
}