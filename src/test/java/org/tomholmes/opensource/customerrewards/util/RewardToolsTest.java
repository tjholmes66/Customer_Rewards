package org.tomholmes.opensource.customerrewards.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import org.tomholmes.opensource.customerrewards.CustomerRewardsApplication;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CustomerRewardsApplication.class)
@Transactional
@ComponentScan("org.tomholmes.opensource.customerrewards")
public class RewardToolsTest
{
    @Autowired
    private RewardTools rewardTools;

    @Test
    public void testCalculateRewards()
    {
        BigDecimal amount = new BigDecimal("25.00");
        long points = rewardTools.calculatePoints(amount);
        assertEquals(0, points);

        amount = new BigDecimal("55.00");
        points = rewardTools.calculatePoints(amount);
        assertEquals(5, points);

        amount = new BigDecimal("100.00");
        points = rewardTools.calculatePoints(amount);
        assertEquals(50, points);

        amount = new BigDecimal("120.00");
        points = rewardTools.calculatePoints(amount);
        assertEquals(90, points);

        amount = new BigDecimal("150.00");
        points = rewardTools.calculatePoints(amount);
        assertEquals(150, points);

        amount = new BigDecimal("200.00");
        points = rewardTools.calculatePoints(amount);
        assertEquals(250, points);
    }
}
