package com.example.experiment.metric.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestModelRequest {

    @ApiModelProperty(required = true, example = "126009")
    @NotBlank
    private String product;
    @ApiModelProperty(required = true, example = "STORE")
    @NotBlank
    private String locationType;
    @ApiModelProperty(required = true, example = "S001")
    @NotBlank
    private String locationId;
}
