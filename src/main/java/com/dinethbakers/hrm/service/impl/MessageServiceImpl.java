package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.MessageEntity;
import com.dinethbakers.hrm.model.Message;
import com.dinethbakers.hrm.repository.jparepository.MessageRepository;
import com.dinethbakers.hrm.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ObjectMapper mapper;
    @Override
    public List<Message> getAllByEmployee(String employeeId) {
        List<MessageEntity> byReceiver = messageRepository.findByReceiverEmployeeIdOrderByMessageIdDesc(employeeId);

        List<Message> messageList = new ArrayList<>();

        for (MessageEntity entity:byReceiver) {
            messageList.add(
                 mapper.convertValue(entity, Message.class)
            );
        }

        return messageList;
    }
}
