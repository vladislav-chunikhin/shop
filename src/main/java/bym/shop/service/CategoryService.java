package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.category.CategoryRequestDto;
import bym.shop.dto.category.CategoryResponseDto;
import bym.shop.entity.CategoryEntity;
import bym.shop.entity.ProductEntity;
import bym.shop.exception.ResourceDeletedException;
import bym.shop.repository.CategoryRepository;
import bym.shop.repository.ProductRepository;
import bym.shop.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryResponseDto create(@NonNull final CategoryRequestDto request) {
        final CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());
        return CategoryResponseDto.from(categoryRepository.save(category));
    }

    public void update(@NonNull final UUID id, @NonNull final CategoryRequestDto request) {
        final CategoryEntity category = categoryRepository.findByIdAndDeletedIsNull(id).orElseThrow(() -> new EntityNotFoundException("Category is not found"));
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    public CommonArrayResponseDto<CategoryResponseDto> get(@Nullable final Collection<String> ids) {
        return CommonUtil.getByIds(ids, CategoryResponseDto::from, categoryRepository::findAllByDeletedIsNull, categoryRepository::findAllByDeletedIsNullAndIdIn);
    }

    @Transactional
    public void delete(@NonNull final UUID id) {
        final CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category is not found"));
        if (category.getDeleted() != null) throw new ResourceDeletedException("Category has already been deleted");
        category.setDeleted(LocalDateTime.now());
        categoryRepository.save(category);

        final Collection<ProductEntity> products = productRepository.findAllByCategoryIdAndDeletedIsNull(id);
        if (!CollectionUtils.isEmpty(products)) {
            products.forEach(it -> it.setDeleted(LocalDateTime.now()));
            productRepository.saveAll(products);
        }
    }
}
