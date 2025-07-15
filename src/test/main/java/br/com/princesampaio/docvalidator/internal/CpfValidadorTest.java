package main.java.br.com.princesampaio.docvalidator.internal;


import br.com.princesampaio.docvalidator.internal.CpfValidador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Validador de CPF Interno")
class CpfValidadorTest {

    private CpfValidador validador;

    @BeforeEach
    void setUp() {
        validador = new CpfValidador();
    }

    @Nested
    @DisplayName("Quando o CPF é válido")
    class CpfValido {

        @ParameterizedTest(name = "Deve retornar true para o CPF válido {0}")
        @ValueSource(strings = {"39403006838", "31533788863", "25662637873"})
        void deveRetornarTrueParaCpfValido(String cpf) {
            assertThat(validador.isValido(cpf)).isTrue();
        }
    }

    @Nested
    @DisplayName("Quando o CPF é inválido")
    class CpfInvalido {

        @Test
        @DisplayName("Deve retornar false para CPF com dígito verificador incorreto")
        void deveRetornarFalseParaDigitoVerificadorIncorreto() {
            assertThat(validador.isValido("50367575081")).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para CPF com todos os dígitos repetidos: {0}")
        @ValueSource(strings = {
                "00000000000", "11111111111", "22222222222", "33333333333",
                "44444444444", "55555555555", "66666666666", "77777777777",
                "88888888888", "99999999999"
        })
        void deveRetornarFalseParaCpfComDigitosRepetidos(String cpf) {
            assertThat(validador.isValido(cpf)).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada com tamanho incorreto: [{0}]")
        @ValueSource(strings = {"1234567890", "123456789012"})
        void deveRetornarFalseParaTamanhoIncorreto(String cpf) {
            assertThat(validador.isValido(cpf)).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada com caracteres não numéricos: [{0}]")
        @ValueSource(strings = {"abcdefghijk", "1234567890a", "123.456.789-00"})
        void deveRetornarFalseParaCaracteresNaoNumericos(String cpf) {
            assertThat(validador.isValido(cpf)).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada nula, vazia ou em branco")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        void deveRetornarFalseParaEntradaNulaOuVazia(String cpf) {
            assertThat(validador.isValido(cpf)).isFalse();
        }
    }
}