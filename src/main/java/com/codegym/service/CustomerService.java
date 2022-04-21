package com.codegym.service;

import com.codegym.model.Customer;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
@Service
public class CustomerService implements ICustomerService{

    private List<Customer> customers=new ArrayList<>();
    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public void save(Customer customer) {
        customers.add(customer);

    }

    @Override
    public Customer findById(int id) {
        return customers.get(id);
    }

    @Override
    public void update(int id, Customer customer) {
        for (Customer c:customers){
            if (c.getId()==id){
                c=customer;
                break;
            }
        }

    }

    @Override
    public void remove(int id) {
        for (int i=0;i<customers.size();i++){
            if (customers.get(i).getId()==id){
                customers.remove(i);
                break;
            }
        }

    }
}
