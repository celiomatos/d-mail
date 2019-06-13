package br.com.dmail.controller;

import br.com.dmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/d-alert")
    public void sendDAlertMessage()  {
        emailService.sendDAlert();
    }

    @GetMapping("/d-payment")
    public void sendPaymentMessage()  {
        emailService.sendPayment();
    }
}
