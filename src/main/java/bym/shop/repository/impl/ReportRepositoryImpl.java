package bym.shop.repository.impl;

import bym.shop.dto.report.ReportRequestDto;
import bym.shop.dto.report.ReportResponseDto;
import bym.shop.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Collection<ReportResponseDto> getReport(@NonNull final ReportRequestDto dto) {
        final String dateFilter = ObjectUtils.allNull(dto.getFrom(), dto.getTo())
                ? ""
                : "WHERE cast(o.created as date) BETWEEN cast(:from as date) AND cast(:to as date)\n";

        final String sql = "SELECT cast(o.created as date) date, sum(o.total_amount) income\n" +
                "FROM amazon.orders o\n" +
                 dateFilter +
                "GROUP BY date\n" +
                "ORDER BY date;";

        final Map<String, LocalDate> params = new HashMap<>();
        params.put("from", dto.getFrom());
        params.put("to", dto.getTo());
        return jdbcTemplate.query(sql, params, new ReportRowMapper());
    }

    private static class ReportRowMapper implements RowMapper<ReportResponseDto> {
        @Override
        public ReportResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ReportResponseDto(rs.getDate("date").toLocalDate(), rs.getBigDecimal("income"));
        }
    }
}
