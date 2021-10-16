package bym.shop.util;

import bym.shop.dto.CommonArrayResponseDto;
import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@UtilityClass
public class CommonUtil {

    public <ReturnType, TargetType> CommonArrayResponseDto<ReturnType> getByIds(
            @Nullable final Collection<String> ids,
            @NonNull final Function<TargetType, ReturnType> mapper,
            @NonNull final Supplier<Collection<TargetType>> functionToGetAll,
            @NonNull final Function<Collection<UUID>, Collection<TargetType>> functionToGetByIds
    ) {
        if (CollectionUtils.isEmpty(ids)) return new CommonArrayResponseDto<>(functionToGetAll.get().stream().map(mapper).collect(Collectors.toList()));
        final List<UUID> idsAsUUID = ids.stream().map(UUID::fromString).collect(Collectors.toList());
        return new CommonArrayResponseDto<>(functionToGetByIds.apply(idsAsUUID).stream().map(mapper).collect(Collectors.toList()));
    }

}
