package bym.shop.dto.report;

import bym.shop.dto.ResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto implements ResponseDto {

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    private BigDecimal income;

}
