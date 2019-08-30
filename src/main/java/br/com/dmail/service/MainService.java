package br.com.dmail.service;

import br.com.dmail.model.PagamentoSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient("d-server-main")
public interface MainService {

    @GetMapping("/destinatarios/find-by-grupo/{grupo}")
    String[] findByGrupo(@PathVariable String grupo);

    @PostMapping("/pagamentos/pagamentos-to-excell")
    byte[] pagamentosToExcell(@RequestBody PagamentoSearchDto pagSearchDto);
}
