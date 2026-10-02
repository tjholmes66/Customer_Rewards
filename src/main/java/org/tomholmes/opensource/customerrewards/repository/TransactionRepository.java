package org.tomholmes.opensource.customerrewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tomholmes.opensource.customerrewards.model.TransactionEntity;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>
{

    List<TransactionEntity> findAllByCustomerId(long customerId);

}
