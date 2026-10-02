package org.tomholmes.opensource.customerrewards.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.customerrewards.model.CustomerEntity;
import org.tomholmes.opensource.customerrewards.model.TransactionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryTest
{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Long customerId = 1L;
    private BigDecimal amount = new BigDecimal("100");

    private CustomerEntity createCustomerEntity()
    {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerId);
        return customerEntity;
    }

    private TransactionEntity createTransactionEntity()
    {
        CustomerEntity customer = createCustomerEntity();
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setCustomer(customer);
        transactionEntity.setTransactionDate(LocalDateTime.now());
        transactionEntity.setTransactionAmount(amount);
        return transactionEntity;
    }

    @Test
    public void testCreateTransaction()
    {
        TransactionEntity transactionEntity = createTransactionEntity();
        TransactionEntity saveAndFlushd = transactionRepository.saveAndFlush(transactionEntity);

        assertThat(saveAndFlushd.getId()).isNotNull();
        Optional<TransactionEntity> found = transactionRepository.findById(saveAndFlushd.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTransactionAmount()).isEqualByComparingTo(amount);
        assertThat(found.get().getCustomer().getId()).isEqualTo(customerId);
    }

    @Test
    public void shouldUpdateTransaction()
    {
        // just like we did initially we create the new transaction
        TransactionEntity transactionEntity = createTransactionEntity();
        TransactionEntity saved = transactionRepository.saveAndFlush(transactionEntity);
        assertThat(saved.getId()).isNotNull();
        Optional<TransactionEntity> found = transactionRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTransactionAmount()).isEqualByComparingTo(amount);
        assertThat(found.get().getCustomer().getId()).isEqualTo(customerId);

        // now we update the xacion amount and save to db
        BigDecimal newAmount = new BigDecimal("200");
        saved.setTransactionAmount(newAmount);
        TransactionEntity updated = transactionRepository.saveAndFlush(saved);

        assertThat(updated.getId()).isEqualTo(saved.getId());
        assertThat(updated.getTransactionAmount()).isEqualByComparingTo(newAmount);
    }

    @Test
    public void testFindAllTransactions()
    {
        // there should already be lots of data in the table in order for this to work
        List<TransactionEntity> all = transactionRepository.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(90);
    }

    @Test
    public void testFindTransactionById()
    {
        Long transactionId = 10L;
        TransactionEntity transaction = transactionRepository.findById(transactionId).orElse(null);
        assertThat(transaction).isNotNull();
        assertThat(transaction.getId()).isEqualTo(transactionId);
    }

    @Test
    public void testFindTransactionByCustomerId()
    {
        Long customerId = 1L;
        List<TransactionEntity> transactionList = transactionRepository.findAllByCustomerId(customerId);
        assertThat(transactionList).isNotEmpty();
        assertThat(transactionList.size()).isEqualTo(14);

    }
}
