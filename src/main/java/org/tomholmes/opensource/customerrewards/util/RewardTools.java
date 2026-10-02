package org.tomholmes.opensource.customerrewards.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RewardTools
{
    public long calculatePoints(BigDecimal amount)
    {
        if (amount == null)
        {
            return 0;
        }
        long dollars = amount.longValue();
        long points = 0;
        if (dollars > 100)
        {
            points += (dollars - 100) * 2;
        }
        if (dollars > 50)
        {
            points += Math.min(dollars, 100) - 50;
        }
        return points;
    }

}
