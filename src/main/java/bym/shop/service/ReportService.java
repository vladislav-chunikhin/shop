package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.report.ReportRequestDto;
import bym.shop.dto.report.ReportResponseDto;
import bym.shop.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public CommonArrayResponseDto<ReportResponseDto> getReport(@NonNull final ReportRequestDto requestDto) {
        return new CommonArrayResponseDto<>(reportRepository.getReport(requestDto));
    }
}
