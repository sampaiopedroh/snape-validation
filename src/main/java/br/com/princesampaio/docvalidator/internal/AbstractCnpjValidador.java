package br.com.princesampaio.docvalidator.internal;

import java.util.Set;

abstract class AbstractCnpjValidador {
    protected static final int TAMANHO_CNPJ = 14;

    protected static final Set<String> CNPJS_INVALIDOS = Set.of(
            "00000000000000", "11111111111111", "22222222222222", "33333333333333",
            "44444444444444", "55555555555555", "66666666666666", "77777777777777",
            "88888888888888", "99999999999999"
    );

    protected static final int[] PESOS_PRIMEIRO_DV = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    protected static final int[] PESOS_SEGUNDO_DV = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    abstract boolean isValido(String cnpj);

    protected int calcularDigitoVerificador(int[] valores, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < valores.length; i++) {
            soma += valores[i] * pesos[i];
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}
