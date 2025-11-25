package com.vu.springapi.controller;

import com.vu.springapi.dto.response.ApiResponse;
import com.vu.springapi.dto.response.DashboardSummaryResponse;
import com.vu.springapi.repository.OrderItemRepository;
import com.vu.springapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @GetMapping("/summary")
    public ApiResponse<DashboardSummaryResponse> getSummary(
            @RequestParam String from,
            @RequestParam String to
    ) {
        LocalDateTime start = LocalDate.parse(from).atStartOfDay();
        LocalDateTime end   = LocalDate.parse(to).atTime(23, 59, 59);

        BigDecimal revenue       = orderRepository.sumRevenue(start, end);
        Long orders              = orderRepository.countOrders(start, end);
        Long customers           = orderRepository.countCustomers(start, end);
        Long productsSold        = orderItemRepository.countProductsSold(start, end);

        DashboardSummaryResponse summary = DashboardSummaryResponse.builder()
                .revenue(revenue)
                .totalOrders(orders)
                .totalCustomers(customers)
                .totalProductsSold(productsSold)
                .build();

        return ApiResponse.<DashboardSummaryResponse>builder()
                .result(summary)
                .build();
    }
}
