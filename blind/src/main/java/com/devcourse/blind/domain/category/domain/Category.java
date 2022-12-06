package com.devcourse.blind.domain.category.domain;

import com.devcourse.blind.base.domain.BaseEntity;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category extends BaseEntity {

    private final Long id;
    private CategoryTitle categoryTitle;
}
