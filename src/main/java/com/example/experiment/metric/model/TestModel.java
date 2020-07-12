package com.example.experiment.metric.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TestModel {

    @ApiModelProperty(required = true)
    private String product;
    @ApiModelProperty(required = true)
    private String locationType;
    @ApiModelProperty(required = true)
    private String locationId;
    @ApiModelProperty(required = true)
    private String description;
}
