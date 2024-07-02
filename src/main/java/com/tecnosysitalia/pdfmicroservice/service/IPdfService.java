package com.tecnosysitalia.pdfmicroservice.service;

import com.tecnosysitalia.pdfmicroservice.model.DocumentoPdf;
import org.json.JSONObject;

import java.io.IOException;

public interface IPdfService {
    DocumentoPdf caricaPdf(byte[] pdfBytes, String nomeFile) throws IOException;
    JSONObject estraiCampi(String id) throws IOException;
    byte[] compilaPdf(String id, JSONObject dati) throws IOException;
}