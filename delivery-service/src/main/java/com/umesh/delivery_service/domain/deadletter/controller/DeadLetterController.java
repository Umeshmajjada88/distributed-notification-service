package com.umesh.delivery_service.domain.deadletter.controller;

import com.umesh.delivery_service.domain.deadletter.dto.response.DeadLetterResponse;
import com.umesh.delivery_service.domain.deadletter.entity.DeadLetter;
import com.umesh.delivery_service.domain.deadletter.mapper.DeadLetterMapper;
import com.umesh.delivery_service.domain.deadletter.service.DeadLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dead-letters")
@RequiredArgsConstructor
public class DeadLetterController {

    private final DeadLetterService deadLetterService;

    private final DeadLetterMapper deadLetterMapper;

    @GetMapping
    public List<DeadLetterResponse> findAll() {

        return deadLetterMapper.toResponseList(
                deadLetterService.findAll());

    }

    @GetMapping("/{id}")
    public DeadLetterResponse findById(
            @PathVariable Long id) {

        DeadLetter deadLetter = deadLetterService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Dead Letter not found"));

        return deadLetterMapper.toResponse(deadLetter);

    }

    @PostMapping("/{id}/replay")
    public DeadLetterResponse replay(
            @PathVariable Long id) {

        return deadLetterMapper.toResponse(
                deadLetterService.replay(id));

    }

}