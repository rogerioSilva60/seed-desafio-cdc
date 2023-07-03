package com.deveficiente.lojadelivro.controller.mapper;

import com.deveficiente.lojadelivro.controller.request.AutorRequest;
import com.deveficiente.lojadelivro.controller.response.AutorResponse;
import com.deveficiente.lojadelivro.entity.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AutorMapper {

  Autor toAutorRequest(AutorRequest autorRequest);

  AutorResponse toAutorResponse(Autor autor);

}
