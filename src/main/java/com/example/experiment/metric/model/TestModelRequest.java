package com.example.experiment.metric.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestModelRequest {

  @Schema(required = true, example = "126009", accessMode = AccessMode.READ_WRITE)
  @NotBlank
  private String product;
  @Schema(required = true, example = "STORE", accessMode = AccessMode.READ_WRITE)
  @NotBlank
  private String locationType;
  @Schema(required = true, example = "S001", accessMode = AccessMode.READ_WRITE)
  @NotBlank
  private String locationId;
}
