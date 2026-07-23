package com.umesh.delivery_service.domain.deadletter.service;

import com.umesh.delivery_service.domain.deadletter.dto.response.DeadLetterResponse;
import com.umesh.delivery_service.domain.deadletter.dto.response.DeadLetterStatisticsResponse;
import com.umesh.delivery_service.domain.deadletter.entity.DeadLetter;
import com.umesh.delivery_service.domain.delivery.entity.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeadLetterService {

    DeadLetter saveFailedDelivery(Delivery delivery);

    Optional<DeadLetter> findById(Long id);

    List<DeadLetter> findAll();

    DeadLetter replay(Long deadLetterId);

    List<DeadLetter> findPending();

    DeadLetterStatisticsResponse getStatistics();

    List<DeadLetterResponse> getAllDeadLetters();

}