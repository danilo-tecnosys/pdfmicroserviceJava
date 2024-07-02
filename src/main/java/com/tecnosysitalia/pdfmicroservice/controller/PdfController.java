package com.tecnosysitalia.pdfmicroservice.controller;

import com.tecnosysitalia.pdfmicroservice.model.DocumentoPdf;
import com.tecnosysitalia.pdfmicroservice.service.IPdfService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private IPdfService _I_pdfService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            DocumentoPdf documento = _I_pdfService.caricaPdf(file.getBytes(), file.getOriginalFilename());
            return ResponseEntity.ok(documento.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il caricamento del PDF");
        }
    }

    @GetMapping("/fields/{id}")
    public ResponseEntity<String> getCampi(@PathVariable String id) {
        try {
            JSONObject campi = _I_pdfService.estraiCampi(id);
            return ResponseEntity.ok(campi.toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'estrazione dei campi");
        }
    }

    @PostMapping("/compile/{id}")
    public ResponseEntity<String> compilaPdf(@PathVariable String id, @RequestBody String jsonDati) {
        try {
            JSONObject dati = new JSONObject(jsonDati);
            byte[] pdfCompilato = _I_pdfService.compilaPdf(id, dati);
            String pdfBase64 = Base64.getEncoder().encodeToString(pdfCompilato);
            JSONObject risposta = new JSONObject();
            risposta.put("pdf", pdfBase64);
            return ResponseEntity.ok(risposta.toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la compilazione del PDF");
        }
    }
}
