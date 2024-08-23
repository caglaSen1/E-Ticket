package com.ftbootcamp.eticketuserservice.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IndividualUserPaginatedResponse {

    private List<IndividualUserSummaryResponse> userSummaryResponses;
    private int currentPage;
    private int numberOfElementsInCurrentPage;
    private int totalPages;
    private long totalElements;
}
