package com.example.experiment.metric.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TestModel {

  @Schema(required = true, accessMode = AccessMode.READ_WRITE)
  private String product;
  @Schema(required = true, accessMode = AccessMode.READ_WRITE)
  private String locationType;
  @Schema(required = true, accessMode = AccessMode.READ_WRITE)
  private String locationId;
  @Schema(required = true, accessMode = AccessMode.READ_WRITE)
  private String description;

}

