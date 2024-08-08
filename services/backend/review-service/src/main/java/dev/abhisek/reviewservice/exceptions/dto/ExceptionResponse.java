package dev.abhisek.reviewservice.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(NON_EMPTY)
public class ExceptionResponse {
    private String status;
    private String code;
    private List<String> messages;
    private Set<String> details;
}
