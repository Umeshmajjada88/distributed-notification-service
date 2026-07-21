package com.umesh.delivery_service.domain.deadletter.repository;

import com.umesh.delivery_service.domain.deadletter.entity.DeadLetter;
import com.umesh.delivery_service.domain.deadletter.enums.DeadLetterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeadLetterRepository
        extends JpaRepository<DeadLetter, Long> {

    List<DeadLetter> findByStatus(DeadLetterStatus status);


}