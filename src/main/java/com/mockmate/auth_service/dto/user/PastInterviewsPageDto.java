package com.mockmate.auth_service.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PastInterviewsPageDto {
    private int page;
    private int limit;
    private long totalListings;
    private int totalPages;
    private List<PastInterviewDTO> results;
}
