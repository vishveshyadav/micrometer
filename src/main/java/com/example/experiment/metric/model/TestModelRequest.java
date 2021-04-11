package com.example.experiment.metric.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestModelRequest {

    @Schema(required = true, example = "126009")
    @NotBlank
    private String product;
    @Schema(required = true, example = "STORE")
    @NotBlank
    private String locationType;
    @Schema(required = true, example = "S001")
    @NotBlank
    private String locationId;
}
