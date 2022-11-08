package com.valex.domain.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.valex.domain.dto.InvoiceDto;
import com.valex.domain.response.InvoiceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)

public interface InvoiceMapper {
  InvoiceResponse dtoToResponse (InvoiceDto invoice);
}
