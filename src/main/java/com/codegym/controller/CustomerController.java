package com.codegym.controller;


import com.codegym.model.Customer;
import com.codegym.model.CustomerForm;
import com.codegym.service.CustomerService;
import com.codegym.service.ICustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("customer")
public class CustomerController {
    private final ICustomerService customerService=new CustomerService();
    @Value("C:/Demoupload/")
    private String fileUpload;

    @GetMapping("")
    public String index(Model model){
        List<Customer> customers= customerService.findAll();
        model.addAttribute("customers",customers);
        return "/index";
    }
    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView=new ModelAndView("/create");
        modelAndView.addObject("customerForm",new CustomerForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveCustomer(@ModelAttribute CustomerForm customerForm){
        MultipartFile multipartFile=customerForm.getImage();
        String fileName=multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(customerForm.getImage().getBytes(),new File(fileUpload+fileName));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Customer customer=new Customer(customerForm.getId(), customerForm.getName(), customerForm.getAddress(), fileName);
        customerService.save(customer);
        ModelAndView modelAndView=new ModelAndView("/create");
        modelAndView.addObject("customerForm",customerForm);
        modelAndView.addObject("message","Create new customer successfully !");
        return modelAndView;
    }
}
