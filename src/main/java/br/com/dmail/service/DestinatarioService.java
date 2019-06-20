package br.com.dmail.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "destinatarioService", url = "http://d-server-main:8084/destinatarios")
public interface DestinatarioService {

    @GetMapping("/find-by-grupo/{grupo}")
    String[] findByGrupo(@PathVariable String grupo);
}
