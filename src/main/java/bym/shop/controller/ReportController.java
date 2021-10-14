package bym.shop.controller;


import bym.shop.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bym.shop.constants.BaseURL.REPORT_BASE_URL;

@RestController
@RequestMapping(REPORT_BASE_URL)
@Tag(name = "Report API")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

}
