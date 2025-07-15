package br.com.princesampaio.docvalidator;

import br.com.princesampaio.docvalidator.internal.CnpjAlfanumericoValidador;
import br.com.princesampaio.docvalidator.internal.CnpjNumericoValidador;
import br.com.princesampaio.docvalidator.internal.CpfValidador;
import br.com.princesampaio.docvalidator.model.TipoDocumento;
import br.com.princesampaio.docvalidator.model.ResultadoValidacao;

import java.util.regex.Pattern;

public final class DocumentoValidador {
    private static final CpfValidador VALIDADOR_CPF = new CpfValidador();
    private static final CnpjNumericoValidador VALIDADOR_CNPJ_NUMERICO = new CnpjNumericoValidador();
    private static final CnpjAlfanumericoValidador VALIDADOR_CNPJ_ALFANUMERICO = new CnpjAlfanumericoValidador();

    private static final Pattern NAO_ALFANUMERICO = Pattern.compile("[^a-zA-Z0-9]");

    private DocumentoValidador() {
    }

    public static boolean isValido(String documento) {
        if (documento == null || documento.isBlank()) {
            return false;
        }

        String documentoLimpo = NAO_ALFANUMERICO.matcher(documento).replaceAll("");

        return switch (documentoLimpo.length()) {
            case 11 -> VALIDADOR_CPF.isValido(documentoLimpo);
            case 14 -> validarDocumentoDe14Caracteres(documentoLimpo);
            default -> false;
        };
    }

    public static ResultadoValidacao validarDocumento(String documento) {
        String valorOriginal = (documento == null) ? "" : documento;

        if (documento == null || documento.isBlank()) {
            return new ResultadoValidacao(valorOriginal, TipoDocumento.INVALIDO, false);
        }

        String documentoLimpo = NAO_ALFANUMERICO.matcher(documento).replaceAll("");

        if (documentoLimpo.length() == 11) {
            boolean eValido = VALIDADOR_CPF.isValido(documentoLimpo);
            return new ResultadoValidacao(valorOriginal, TipoDocumento.CPF, eValido);
        }

        if (documentoLimpo.length() == 14) {
            boolean eNumerico = documentoLimpo.chars().allMatch(Character::isDigit);
            if (eNumerico) {
                boolean eValido = VALIDADOR_CNPJ_NUMERICO.isValido(documentoLimpo);
                return new ResultadoValidacao(valorOriginal, TipoDocumento.CNPJ, eValido);
            } else {
                boolean eValido = VALIDADOR_CNPJ_ALFANUMERICO.isValido(documentoLimpo);
                return new ResultadoValidacao(valorOriginal, TipoDocumento.CNPJ_ALFANUMERICO, eValido);
            }
        }

        return new ResultadoValidacao(valorOriginal, TipoDocumento.INVALIDO, false);
    }

    private static boolean validarDocumentoDe14Caracteres(String documentoLimpo) {
        boolean eNumerico = documentoLimpo.chars().allMatch(Character::isDigit);
        if (eNumerico) {
            return VALIDADOR_CNPJ_NUMERICO.isValido(documentoLimpo);
        } else {
            return VALIDADOR_CNPJ_ALFANUMERICO.isValido(documentoLimpo);
        }
    }
}
