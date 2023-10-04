package com.wooin.hahahaback.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto {
    private int statusCode;
    private String statusMessage;
}