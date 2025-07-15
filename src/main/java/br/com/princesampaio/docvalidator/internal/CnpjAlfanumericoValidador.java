package br.com.princesampaio.docvalidator.internal;

public class CnpjAlfanumericoValidador extends AbstractCnpjValidador {
    @Override
    public boolean isValido(String cnpj) {
        if (cnpj == null || cnpj.length() != TAMANHO_CNPJ) {
            return false;
        }

        if (cnpj.substring(0, 12).matches("\\d{12}")) {
            return false;
        }

        if (!cnpj.matches("[a-zA-Z0-9]{12}\\d{2}")) {
            return false;
        }

        int[] valoresBase = converterParaValores(cnpj.substring(0, 12));

        int primeiroDvCalculado = calcularDigitoVerificador(valoresBase, PESOS_PRIMEIRO_DV);
        int primeiroDvReal = Character.getNumericValue(cnpj.charAt(12));

        if (primeiroDvCalculado != primeiroDvReal) {
            return false;
        }

        int[] baseParaSegundoDv = new int[13];
        System.arraycopy(valoresBase, 0, baseParaSegundoDv, 0, 12);
        baseParaSegundoDv[12] = primeiroDvReal;

        int segundoDvCalculado = calcularDigitoVerificador(baseParaSegundoDv, PESOS_SEGUNDO_DV);
        int segundoDvReal = Character.getNumericValue(cnpj.charAt(13));

        return segundoDvCalculado == segundoDvReal;
    }

    private int[] converterParaValores(String base) {
        return base.chars().map(caractere -> caractere - 48).toArray();
    }
}