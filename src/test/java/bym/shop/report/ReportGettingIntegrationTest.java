package bym.shop.report;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.ReportController;
import bym.shop.dto.report.ReportRequestDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static bym.shop.constants.BaseURL.REPORT_BASE_URL;

/**
 * Integration tests for {@link ReportController#getReport(ReportRequestDto)}.
 */
public class ReportGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/report/insert-data.sql")
    @SqlAfter("/sql/report/delete-data.sql")
    @Test
    public void reportGettingInTheUsualCase() throws Exception {
        final LocalDate to = LocalDate.now();
        final LocalDate from = to.minusDays(3);

        final ResultActions res = executeGet(REPORT_BASE_URL + "?from=" + from + "&to=" + to);
        checkForSuccess(res);
    }

}
