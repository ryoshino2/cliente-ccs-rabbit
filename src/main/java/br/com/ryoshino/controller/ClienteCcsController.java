package br.com.ryoshino.controller;

import br.com.ryoshino.model.ClienteCcsRabbit;
import br.com.ryoshino.service.ClienteMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteCcsController {

    @Autowired
    private final ClienteMessageSender clienteMessageSender;

    public ClienteCcsController(ClienteMessageSender clienteMessageSender) {
        this.clienteMessageSender = clienteMessageSender;
    }

    @PostMapping("/sendClienteCcs")
    public void enviarClienteParaORabbit(@RequestBody ClienteCcsRabbit clienteCcsRabbit) {
            clienteMessageSender.sendOrder(clienteCcsRabbit);
    }

}
