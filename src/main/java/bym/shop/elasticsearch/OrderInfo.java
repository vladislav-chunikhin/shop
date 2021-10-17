package bym.shop.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Document(indexName = "order")
@Data
public class OrderInfo {

    @Id
    private UUID id;

    private UUID orderId;

    private UUID userId;

    private BigDecimal totalAmount;

    private Collection<String> products;

}
