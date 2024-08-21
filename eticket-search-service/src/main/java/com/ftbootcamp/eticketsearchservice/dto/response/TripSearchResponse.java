package com.ftbootcamp.eticketsearchservice.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripSearchResponse {

    private List<TripDocumentResponse> tripDocumentResponses;
    private int totalPage;
    private long totalElement;
}
