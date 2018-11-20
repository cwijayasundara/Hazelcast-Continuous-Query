package com.cham.HazelcastContinousQuery.controller;

import com.cham.HazelcastContinousQuery.PortableMultiAttribute.Employee;
import com.cham.HazelcastContinousQuery.PortableMultiAttribute.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class EmployeeSseEmitter {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/employee-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Integer>> sentServerSentEventStream(){
        System.out.println("Inside the EmployeeSseEmitter.sentServerSentEventStream()..");
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }

    @PostMapping("/employee/save")
    public ResponseEntity<String> setEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok("Saved");
    }

}
