package com.example.experiment.metric.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TestModel {

  @Schema(required = true)
  private String product;
  @Schema(required = true)
  private String locationType;
  @Schema(required = true)
  private String locationId;
  @Schema(required = true)
  private String description;

}

