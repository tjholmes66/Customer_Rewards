package org.tomholmes.opensource.customerrewards.ws.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tomholmes.opensource.customerrewards.dto.CustomerRewardsReport;
import org.tomholmes.opensource.customerrewards.service.CustomerRewardsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers/rewards")
public class CustomerRewardsController
{
    private final CustomerRewardsService customerRewardsService;

    public CustomerRewardsController(CustomerRewardsService customerRewardsService)
    {
        this.customerRewardsService = customerRewardsService;
    }

    @GetMapping
    public List<CustomerRewardsReport> getRewardsReport()
    {
        List<CustomerRewardsReport> customerRewardsReports = new ArrayList<>();
        customerRewardsReports = customerRewardsService.getCustomerTransReportPerMonth();
        return customerRewardsReports;
    }
}
