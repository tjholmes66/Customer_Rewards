package org.tomholmes.opensource.customerrewards.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tomholmes.opensource.customerrewards.dto.CustomerRewardsReport;
import org.tomholmes.opensource.customerrewards.dto.MonthlyPoints;
import org.tomholmes.opensource.customerrewards.model.CustomerEntity;
import org.tomholmes.opensource.customerrewards.model.TransactionEntity;
import org.tomholmes.opensource.customerrewards.repository.CustomerRepository;
import org.tomholmes.opensource.customerrewards.repository.TransactionRepository;
import org.tomholmes.opensource.customerrewards.util.RewardTools;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional(readOnly = true)
public class CustomerRewardsServiceImpl implements CustomerRewardsService
{

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final RewardTools rewardTools;

    public CustomerRewardsServiceImpl(CustomerRepository customerRepository,
    TransactionRepository transactionRepository, RewardTools rewardTools)
    {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.rewardTools = rewardTools;
    }

    @Override
    public List<CustomerRewardsReport> getCustomerTransReportPerMonth() {
        List<CustomerRewardsReport> report = new ArrayList<>();

        for (CustomerEntity customer : customerRepository.findAll()) {
            List<TransactionEntity> transactions = transactionRepository.findAllByCustomerId(customer.getId());
            Map<YearMonth, Long> pointsByMonth = new TreeMap<>();

            for (TransactionEntity transaction : transactions) {
                YearMonth month = YearMonth.from(transaction.getTransactionDate());
                long points = rewardTools.calculatePoints(transaction.getTransactionAmount());
                pointsByMonth.merge(month, points, Long::sum);
            }

            List<MonthlyPoints> monthly = pointsByMonth.entrySet().stream()
                    .map(e -> new MonthlyPoints(e.getKey().toString(), e.getValue()))
                    .toList();
            long total = monthly.stream().mapToLong(MonthlyPoints::points).sum();
            report.add(new CustomerRewardsReport(customer.getId(), customer.getCustomerName(), monthly, total));
        }
        return report;
    }

}
