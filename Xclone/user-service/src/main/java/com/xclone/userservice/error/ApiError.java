package com.xclone.userservice.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {
    @JsonProperty("errorId")
    private String errorId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("informationLink")
    private String informationLink;

    @JsonProperty("details")
    private List<ApiErrorDetails> details;

    @JsonIgnore
    private HttpStatus httpStatus;
}
