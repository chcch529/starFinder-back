package io.chcch.starfinder.global.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@Builder
public class CursorSliceResponse<T> {

    private List<T> content;
    private boolean hasNext;

    public static <T> CursorSliceResponse<T> of(Slice<T> slice) {
        return CursorSliceResponse.<T>builder()
            .hasNext(slice.hasNext())
            .content(slice.getContent())
            .build();
    }

}
