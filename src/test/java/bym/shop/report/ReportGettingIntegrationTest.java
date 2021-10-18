package bym.shop.report;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.ReportController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.report.ReportRequestDto;
import bym.shop.dto.report.ReportResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bym.shop.constants.BaseURL.REPORT_BASE_URL;

/**
 * Integration tests for {@link ReportController#getReport(ReportRequestDto)}.
 */
public class ReportGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/report/insert-data.sql")
    @SqlAfter("/sql/report/delete-data.sql")
    @Test
    public void reportGettingInTheUsualCase() throws Exception {
        final LocalDate to = LocalDate.of(2021, 10, 17);
        final LocalDate from = to.minusDays(3);

        final ResultActions res = executeGet(REPORT_BASE_URL + "?from=" + from + "&to=" + to);
        checkForSuccess(res);

        final CommonArrayResponseDto<ReportResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        Assertions.assertEquals(3, response.getData().size());
        final List<ReportResponseDto> items = Arrays.stream(response.getData().toArray()).map(ReportResponseDto.class::cast).collect(Collectors.toList());

        Assertions.assertEquals(LocalDate.of(2021, 10, 14), items.get(0).getDate());
        Assertions.assertEquals(BigDecimal.valueOf(58), items.get(0).getIncome());

        Assertions.assertEquals(LocalDate.of(2021, 10, 16), items.get(1).getDate());
        Assertions.assertEquals(BigDecimal.valueOf(58), items.get(1).getIncome());

        Assertions.assertEquals(LocalDate.of(2021, 10, 17), items.get(2).getDate());
        Assertions.assertEquals(BigDecimal.valueOf(74), items.get(2).getIncome());
    }

    @SqlBefore("/sql/report/insert-data.sql")
    @SqlAfter("/sql/report/delete-data.sql")
    @Test
    public void reportGettingWhenInputParametersAreMissing() throws Exception {
        final ResultActions res = executeGet(REPORT_BASE_URL);
        checkForSuccess(res);

        final CommonArrayResponseDto<ReportResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        Assertions.assertEquals(4, response.getData().size());

        final List<ReportResponseDto> items = Arrays.stream(response.getData().toArray()).map(ReportResponseDto.class::cast).collect(Collectors.toList());

        Assertions.assertEquals(LocalDate.of(2021, 10, 12), items.get(0).getDate());
        Assertions.assertEquals(BigDecimal.valueOf(58), items.get(0).getIncome());

        Assertions.assertEquals(LocalDate.of(2021, 10, 14), items.get(1).getDate());
        Assertions.assertEquals(BigDecimal.valueOf(58), items.get(1).getIncome());

        Assertions.assertEquals(LocalDate.of(2021, 10, 16), items.get(2).getDate());
        Assertions.assertEquals(BigDecimal.valueOf(58), items.get(2).getIncome());

        Assertions.assertEquals(LocalDate.of(2021, 10, 17), items.get(3).getDate());
        Assertions.assertEquals(BigDecimal.valueOf(74), items.get(3).getIncome());
    }

}
