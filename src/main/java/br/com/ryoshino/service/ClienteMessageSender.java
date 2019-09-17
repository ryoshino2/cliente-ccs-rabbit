package br.com.ryoshino.service;

import br.com.ryoshino.configuration.RabbitConfig;
import br.com.ryoshino.model.ClienteCcsRabbit;
import br.com.ryoshino.repository.ClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class ClienteMessageSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteMessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrder(ClienteCcsRabbit clienteCcsRabbit) {
        try {
            String clienteJson = objectMapper.writeValueAsString(clienteCcsRabbit);
            Message message = MessageBuilder
                    .withBody(clienteJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.CLIENTECCS_QUEUE, clienteCcsRabbit);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public ClienteCcsRabbit gerarCliente() {
        Random gerador = new Random();

        ClienteCcsRabbit cliente = new ClienteCcsRabbit();
        cliente.setDataAtualizacao(LocalDate.now());
        cliente.setCpf(gerador.nextInt(10000) + 1);
        cliente.setEmail("a@.com" + gerador.nextInt(100));
        cliente.setEndereco("endereco: " + gerador.nextInt(100));
        cliente.setNome("nome: " + gerador.nextInt(100));
        cliente.setTelefone(gerador.nextInt(10000) + 1);
        salvarCliente(cliente);

        return cliente;
    }

    private void salvarCliente(ClienteCcsRabbit cliente) {
        clienteRepository.save(cliente);
    }
}
