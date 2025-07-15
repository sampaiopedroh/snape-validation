package main.java.br.com.princesampaio.docvalidator.internal;

import br.com.princesampaio.docvalidator.internal.CnpjNumericoValidador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Validador de CNPJ Numérico Interno")
class CnpjNumericoValidadorTest {

    private CnpjNumericoValidador validador;

    @BeforeEach
    void setUp() {
        validador = new CnpjNumericoValidador();
    }

    @Nested
    @DisplayName("Quando o CNPJ é válido")
    class CnpjValido {

        @ParameterizedTest(name = "Deve retornar true para o CNPJ válido {0}")
        @ValueSource(strings = {"03229759000139", "60701190000104", "42267898000109"})
        void deveRetornarTrueParaCnpjValido(String cnpj) {
            assertThat(validador.isValido(cnpj)).isTrue();
        }
    }

    @Nested
    @DisplayName("Quando o CNPJ é inválido")
    class CnpjInvalido {

        @Test
        @DisplayName("Deve retornar false para CNPJ com dígito verificador incorreto")
        void deveRetornarFalseParaDigitoVerificadorIncorreto() {
            assertThat(validador.isValido("33000167000102")).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para CNPJ com todos os dígitos repetidos: {0}")
        @ValueSource(strings = {
                "00000000000000", "11111111111111", "22222222222222", "33333333333333",
                "44444444444444", "55555555555555", "66666666666666", "77777777777777",
                "88888888888888", "99999999999999"
        })
        void deveRetornarFalseParaCnpjComDigitosRepetidos(String cnpj) {
            assertThat(validador.isValido(cnpj)).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada com tamanho incorreto: [{0}]")
        @ValueSource(strings = {"1234567890123", "123456789012345"})
        void deveRetornarFalseParaTamanhoIncorreto(String cnpj) {
            assertThat(validador.isValido(cnpj)).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada com caracteres não numéricos: [{0}]")
        @ValueSource(strings = {"abcdefghijklmn", "1234567890123a", "33.000.167/0001-01"})
        void deveRetornarFalseParaCaracteresNaoNumericos(String cnpj) {
            assertThat(validador.isValido(cnpj)).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada nula, vazia ou em branco")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        void deveRetornarFalseParaEntradaNulaOuVazia(String cnpj) {
            assertThat(validador.isValido(cnpj)).isFalse();
        }
    }
}