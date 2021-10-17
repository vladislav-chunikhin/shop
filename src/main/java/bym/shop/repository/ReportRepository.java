package bym.shop.repository;

import bym.shop.dto.report.ReportRequestDto;
import bym.shop.dto.report.ReportResponseDto;

import java.util.Collection;

public interface ReportRepository {

    Collection<ReportResponseDto> getReport(final ReportRequestDto dto);

}
