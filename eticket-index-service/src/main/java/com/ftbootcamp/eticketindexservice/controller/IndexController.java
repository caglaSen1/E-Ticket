package com.ftbootcamp.eticketindexservice.controller;

import com.ftbootcamp.eticketindexservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketindexservice.dto.response.TripDocumentResponse;
import com.ftbootcamp.eticketindexservice.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class IndexController {

    private final IndexService indexService;

    @GetMapping("/trip-documents")
    public GenericResponse<List<TripDocumentResponse>> getAllPayments() {
        return GenericResponse.success(indexService.getAllTripDocumentResponses(), HttpStatus.OK);
    }

}
