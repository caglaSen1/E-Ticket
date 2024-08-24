package com.ftbootcamp.eticketsearchservice.dto.request;

import com.ftbootcamp.eticketsearchservice.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BaseSearchRequest {

    private int page;
    private int size;
    private SortDirection sortDirection;
}
