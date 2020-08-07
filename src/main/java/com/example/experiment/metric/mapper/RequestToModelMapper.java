package com.example.experiment.metric.mapper;

import com.example.experiment.metric.model.TestModel;
import com.example.experiment.metric.model.TestModelRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestToModelMapper {

  TestModelRequest modelToRequest(TestModel testModel);

  TestModel requestToModel(TestModelRequest testModelRequest);
}