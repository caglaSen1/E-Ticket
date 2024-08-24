package com.ftbootcamp.eticketuserservice.dto.response.user;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSummaryPaginatedResponse {

    private List<UserSummaryResponse> userSummaryResponses;
    private int currentPage;
    private int numberOfElementsInCurrentPage;
    private int totalPages;
    private long totalElements;
}
