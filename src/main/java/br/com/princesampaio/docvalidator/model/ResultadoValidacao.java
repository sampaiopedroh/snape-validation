package br.com.princesampaio.docvalidator.model;

public record ResultadoValidacao(
        String valorOriginal,
        TipoDocumento tipoDocumento,
        boolean isValido
) {}
