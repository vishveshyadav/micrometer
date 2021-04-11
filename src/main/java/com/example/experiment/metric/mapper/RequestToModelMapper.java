package com.example.experiment.metric.mapper;

import com.example.experiment.metric.model.TestModel;
import com.example.experiment.metric.model.TestModelRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestToModelMapper {

    TestModelRequest modelToRequest(TestModel testModel);

    @Mapping(target = "description", expression = "java(\"Genrated from Mapper\")")
    TestModel requestToModel(TestModelRequest testModelRequest);
}