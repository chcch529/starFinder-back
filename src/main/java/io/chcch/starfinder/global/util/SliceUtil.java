package io.chcch.starfinder.global.util;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class SliceUtil {

    public static <T> Slice<T> toSlice(List<T> contentWithExtra, Pageable pageable) {
        boolean hasNext = contentWithExtra.size() > pageable.getPageSize();

        List<T> content = hasNext
            ? contentWithExtra.subList(0, pageable.getPageSize())
            : contentWithExtra;

        return new SliceImpl<>(content, pageable, hasNext);
    }

}
