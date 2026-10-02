package org.tomholmes.opensource.customerrewards.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.tomholmes.opensource.customerrewards.CustomerRewardsApplication;
import org.tomholmes.opensource.customerrewards.dto.CustomerRewardsReport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CustomerRewardsApplication.class)
@Transactional
@ComponentScan("org.tomholmes.opensource.customerrewards")
public class CustomerRewardsServiceTest
{
    @Autowired
    private CustomerRewardsService customerRewardsService;

    @Test
    // List<CustomerRewardsReport> getCustomerTransReportPerMonth();
    public void testGetCustomerTransReportPerMonth()
    {
        List<CustomerRewardsReport> customerList = customerRewardsService.getCustomerTransReportPerMonth();
        assertThat(customerList).isNotNull();
        assertThat(customerList).isNotEmpty();
    }
}
