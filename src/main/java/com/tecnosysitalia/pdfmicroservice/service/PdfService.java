package com.tecnosysitalia.pdfmicroservice.service;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.tecnosysitalia.pdfmicroservice.model.DocumentoPdf;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PdfService implements IPdfService {

    private static final String PDF_DIRECTORY = "/home/danilo/temp";

    @Override
    public DocumentoPdf caricaPdf(byte[] pdfBytes, String nomeFile) throws IOException {
        String id = UUID.randomUUID().toString();
        String percorsoFile = PDF_DIRECTORY + "/" + id + ".pdf";
        Path path = Paths.get(percorsoFile);
        Files.write(path, pdfBytes);

        DocumentoPdf documento = new DocumentoPdf();
        documento.setId(id);
        documento.setNomeOriginale(nomeFile);
        documento.setDataCaricamento(LocalDateTime.now());
        documento.setPercorsoFile(percorsoFile);

        return documento;
    }

    @Override
    public JSONObject estraiCampi(String id) throws IOException {
        String percorsoFile = PDF_DIRECTORY + "/" + id + ".pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(percorsoFile));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);

        JSONObject campi = new JSONObject();
        if (form != null) {
            for (String nomeCampo : form.getFormFields().keySet()) {
                PdfFormField campo = form.getField(nomeCampo);
                if (campo != null) {
                    String valore = campo.getValueAsString();
                    campi.put(nomeCampo, valore != null ? valore : "");
                }
            }
        }

        pdfDoc.close();
        return campi;
    }

    @Override
    public byte[] compilaPdf(String id, JSONObject dati) throws IOException {
        String percorsoFile = PDF_DIRECTORY + "/" + id + ".pdf";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(percorsoFile), new PdfWriter(baos));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        dati.keys().forEachRemaining(chiave -> {
            form.getField(chiave).setValue(dati.getString(chiave));
        });

        pdfDoc.close();
        return baos.toByteArray();
    }
}
