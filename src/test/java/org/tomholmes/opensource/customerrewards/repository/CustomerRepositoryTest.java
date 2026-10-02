package org.tomholmes.opensource.customerrewards.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.customerrewards.model.CustomerEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest
{

    @Autowired
    private CustomerRepository customerRepository;

    private String customerName = "NEW CUSTOMER";

    private CustomerEntity createCustomerEntity()
    {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerName(customerName);
        return customerEntity;
    }

    @Test
    public void testCreateCustomer()
    {
        CustomerEntity customerEntity = createCustomerEntity();
        CustomerEntity saved = customerRepository.saveAndFlush(customerEntity);

        assertThat(saved.getId()).isNotNull();
        Optional<CustomerEntity> found = customerRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getCustomerName()).isEqualTo(customerName);
    }

    @Test
    public void testUpdateCustomer()
    {
        // create and save customer
        CustomerEntity customerEntity = createCustomerEntity();
        CustomerEntity saved = customerRepository.save(customerEntity);
        assertThat(saved.getId()).isNotNull();
        Optional<CustomerEntity> found = customerRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getCustomerName()).isEqualTo(customerName);

        // update customer to new name
        String newCustomerName = "UPDATED NAME";
        saved.setCustomerName(newCustomerName);
        CustomerEntity updated = customerRepository.saveAndFlush(saved);

        assertThat(updated.getId()).isEqualTo(saved.getId());
        assertThat(updated.getCustomerName()).isEqualTo(newCustomerName);
    }

    @Test
    public void testDeleteCustomer()
    {
        // create and save customer
        CustomerEntity customerEntity = createCustomerEntity();
        CustomerEntity saved = customerRepository.save(customerEntity);
        assertThat(saved.getId()).isNotNull();
        Optional<CustomerEntity> found = customerRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getCustomerName()).isEqualTo(customerName);

        // now delete record we just saved
        customerRepository.deleteById(saved.getId());

        assertThat(customerRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    public void testFindByAllCustomer()
    {
        List<CustomerEntity> all = customerRepository.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(10);
    }
}
