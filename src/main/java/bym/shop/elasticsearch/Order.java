package bym.shop.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Document(indexName = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private UUID id;

    private UUID orderId;

    private UUID userId;

    private BigDecimal totalAmount;

    private Collection<String> products;

}
