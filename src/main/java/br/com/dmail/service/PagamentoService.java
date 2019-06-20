package br.com.dmail.service;

import br.com.dmail.model.PagamentoSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "pagamentoService", url = "http://d-server-main:8084/pagamentos")
public interface PagamentoService {

    @PostMapping("/pagamentos-to-excell")
    byte[] pagamentosToExcell(@RequestBody PagamentoSearchDto pagSearchDto);
}
