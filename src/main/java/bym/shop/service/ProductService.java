package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.product.ProductRequestDto;
import bym.shop.dto.product.ProductResponseDto;
import bym.shop.elasticsearch.OrderElasticSearchService;
import bym.shop.entity.OrderItemEntity;
import bym.shop.entity.ProductEntity;
import bym.shop.exception.ResourceDeletedException;
import bym.shop.repository.OrderItemRepository;
import bym.shop.repository.ProductRepository;
import bym.shop.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderElasticSearchService orderElasticSearchService;

    public ProductResponseDto create(@NonNull final ProductRequestDto request) {
        final ProductEntity product = new ProductEntity();
        setFields(request, product);
        return ProductResponseDto.from(productRepository.save(product));
    }

    public void update(@NonNull final UUID id, @NonNull final ProductRequestDto request) {
        final ProductEntity product = productRepository.findByIdAndDeletedIsNull(id).orElseThrow(() -> new EntityNotFoundException("Product is not found"));
        final String oldProductName = product.getName();
        setFields(request, product);
        productRepository.save(product);

        final Collection<OrderItemEntity> items = orderItemRepository.findAllByDeletedIsNullAndProductId(product.getId());
        final List<UUID> orderIds = items.stream().map(OrderItemEntity::getOrderId).collect(Collectors.toList());

        if (!oldProductName.equals(product.getName())) {
            orderElasticSearchService.updateOrders(orderIds, oldProductName, product.getName());
        }
    }

    public CommonArrayResponseDto<ProductResponseDto> get(@Nullable final Collection<String> ids) {
        return CommonUtil.getByIds(ids, ProductResponseDto::from, productRepository::findAllByDeletedIsNull, productRepository::findAllByDeletedIsNullAndIdIn);
    }

    public void delete(@NonNull final UUID id) {
        final ProductEntity product = productRepository.findByIdAndDeletedIsNull(id).orElseThrow(() -> new EntityNotFoundException("Product is not found"));
        if (product.getDeleted() != null) throw new ResourceDeletedException("Product has already been deleted");
        product.setDeleted(LocalDateTime.now());
        productRepository.save(product);
    }

    private void setFields(@NonNull final ProductRequestDto request, @NonNull final ProductEntity product) {
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setCategoryId(UUID.fromString(request.getCategoryId()));
    }

}
