package com.ftbootcamp.eticketuserservice.dto.response.admin;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminUserPaginatedResponse {

    private List<AdminUserSummaryResponse> userSummaryResponses;
    private int currentPage;
    private int numberOfElementsInCurrentPage;
    private int totalPages;
    private long totalElements;
}
