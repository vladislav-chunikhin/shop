package bym.shop.repository;

import bym.shop.dto.ReportResultDto;
import bym.shop.dto.report.ReportRequestDto;

import java.util.Collection;

public interface ReportRepository {

    Collection<ReportResultDto> getReport(final ReportRequestDto dto);

}
