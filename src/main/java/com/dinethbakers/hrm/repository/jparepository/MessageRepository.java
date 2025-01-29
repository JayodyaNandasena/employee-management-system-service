package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity,Integer> {
    List<MessageEntity> findByReceiverEmployeeIdOrderByMessageIdDesc(String employeeId);
}
