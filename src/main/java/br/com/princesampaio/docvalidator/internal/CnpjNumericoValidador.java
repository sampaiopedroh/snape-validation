package br.com.princesampaio.docvalidator.internal;

import java.util.Arrays;

public class CnpjNumericoValidador extends AbstractCnpjValidador {
    @Override
    public boolean isValido(String cnpj) {
        if (cnpj == null || cnpj.length() != TAMANHO_CNPJ || !cnpj.matches("\\d{" + TAMANHO_CNPJ + "}")) {
            return false;
        }

        if (CNPJS_INVALIDOS.contains(cnpj)) {
            return false;
        }

        int[] valores = cnpj.chars().map(Character::getNumericValue).toArray();

        int[] baseParaPrimeiroDv = Arrays.copyOfRange(valores, 0, 12);
        int primeiroDvCalculado = calcularDigitoVerificador(baseParaPrimeiroDv, PESOS_PRIMEIRO_DV);

        if (primeiroDvCalculado != valores[12]) {
            return false;
        }

        int[] baseParaSegundoDv = Arrays.copyOfRange(valores, 0, 13);
        int segundoDvCalculado = calcularDigitoVerificador(baseParaSegundoDv, PESOS_SEGUNDO_DV);

        return segundoDvCalculado == valores[13];
    }
}
