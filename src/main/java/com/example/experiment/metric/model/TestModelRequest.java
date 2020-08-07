package com.example.experiment.metric.model;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
