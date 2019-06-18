package br.com.dmail.service;

import br.com.dmail.model.PagamentoSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private DestinatarioService destinatarioService;

    public void sendDAlert() {

        File dir = new File("/sistema/d-alert/" + new SimpleDateFormat("yyyy-MM-dd'T'HH").format(new Date()));

        if (dir.exists()) {
            File files[] = dir.listFiles();
            String text = "";

            for (int i = 0; i < files.length; i++) {
                try {
                    if (files[i].getAbsolutePath().endsWith(".txt")) {
                        try (BufferedReader br = new BufferedReader(new FileReader(files[i]))) {
                            String st;
                            while ((st = br.readLine()) != null) {
                                text += "\n" + st;
                            }
                        }
                    }
                } catch (IOException ex) {
                    log.error(ex.getMessage());
                }
            }
            String[] to = destinatarioService.findByGrupo("d-alert");
            sendMessageWithAttachment(to, "msg", text, files);
        }
    }

    private void sendMessageWithAttachment(String[] to, String subject, String text, File[] files) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            for (File f : files) {
                FileSystemResource file = new FileSystemResource(f);
                helper.addAttachment(file.getFilename(), file);
            }

            emailSender.send(message);
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
        }
    }

    public void sendPayment() {

        LocalDateTime hoje = LocalDateTime.now();
        PagamentoSearchDto pagSearchDto = PagamentoSearchDto.builder().dataInicial(new Date()).dataFinal(new Date()).build();

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String[] to = destinatarioService.findByGrupo("d-pagamento");
            helper.setTo(to);
            helper.setSubject("Pagamento");
            helper.setText("Pagamentos");

            byte[] bytes = pagamentoService.pagamentosToExcell(pagSearchDto);
            InputStream is = new ByteArrayInputStream(bytes);
            helper.addAttachment(
                    "pagamentos.xlsx",
                    new ByteArrayResource(IOUtils.toByteArray(is)));


            emailSender.send(message);
        } catch (MessagingException | IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void sendSimpleMessage(String subject, String text, String... to) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
