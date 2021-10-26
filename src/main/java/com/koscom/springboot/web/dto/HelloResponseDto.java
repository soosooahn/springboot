package com.koscom.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name; // final 이므로 무조건 값이 있어야 한다.
    private final int amount;
}
