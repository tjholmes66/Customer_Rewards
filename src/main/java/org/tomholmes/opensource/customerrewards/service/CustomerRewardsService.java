package org.tomholmes.opensource.customerrewards.service;

import org.tomholmes.opensource.customerrewards.dto.CustomerRewardsReport;

import java.util.List;

public interface CustomerRewardsService
{

    List<CustomerRewardsReport> getCustomerTransReportPerMonth();
}
