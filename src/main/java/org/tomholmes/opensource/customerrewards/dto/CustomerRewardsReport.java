package org.tomholmes.opensource.customerrewards.dto;

import java.util.List;

public record CustomerRewardsReport(Long customerId,
                                    String customerName,
                                    List<MonthlyPoints> pointsPerMonth,
                                    long totalPoints)
{
}
