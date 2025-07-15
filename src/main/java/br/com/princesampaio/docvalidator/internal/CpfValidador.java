package br.com.princesampaio.docvalidator.internal;

import java.util.Set;

public class CpfValidador {
    private static final int TAMANHO_CPF = 11;

    private static final Set<String> CPFS_INVALIDOS = Set.of(
            "00000000000", "11111111111", "22222222222", "33333333333",
            "44444444444", "55555555555", "66666666666", "77777777777",
            "88888888888", "99999999999"
    );

    public boolean isValido(String cpf) {
        if (cpf == null || cpf.length() != TAMANHO_CPF || !cpf.matches("\\d{" + TAMANHO_CPF + "}")) {
            return false;
        }

        if (CPFS_INVALIDOS.contains(cpf)) {
            return false;
        }

        int[] digitos = cpf.chars().map(Character::getNumericValue).toArray();

        int primeiroDigitoVerificador = calcularDigitoVerificador(digitos, 9);
        if (primeiroDigitoVerificador != digitos[9]) {
            return false;
        }

        int segundoDigitoVerificador = calcularDigitoVerificador(digitos, 10);
        return segundoDigitoVerificador == digitos[10];
    }

    private int calcularDigitoVerificador(int[] digitos, int tamanhoBase) {
        int soma = 0;
        for (int i = 0; i < tamanhoBase; i++) {
            soma += digitos[i] * ((tamanhoBase + 1) - i);
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}
