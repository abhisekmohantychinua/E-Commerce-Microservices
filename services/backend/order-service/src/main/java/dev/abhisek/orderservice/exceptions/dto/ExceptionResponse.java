package dev.abhisek.orderservice.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExceptionResponse {
    private String status;
    private String code;
    private List<String> messages;
    private Set<String> details;
}
