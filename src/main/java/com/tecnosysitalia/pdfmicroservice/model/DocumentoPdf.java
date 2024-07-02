package com.tecnosysitalia.pdfmicroservice.model;

import java.time.LocalDateTime;

public class DocumentoPdf {
    private String id;
    private String nomeOriginale;
    private LocalDateTime dataCaricamento;
    private String percorsoFile;

// Costruttori, getter e setter
public String getPercorsoFile() {
    return percorsoFile;
}

    public void setPercorsoFile(String percorsoFile) {
        this.percorsoFile = percorsoFile;
    }

    public LocalDateTime getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(LocalDateTime dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public String getNomeOriginale() {
        return nomeOriginale;
    }

    public void setNomeOriginale(String nomeOriginale) {
        this.nomeOriginale = nomeOriginale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}