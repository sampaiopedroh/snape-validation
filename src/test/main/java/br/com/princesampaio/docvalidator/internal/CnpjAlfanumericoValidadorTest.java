package main.java.br.com.princesampaio.docvalidator.internal;

import br.com.princesampaio.docvalidator.internal.CnpjAlfanumericoValidador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Validador de CNPJ Alfanumérico Interno")
class CnpjAlfanumericoValidadorTest {

    private CnpjAlfanumericoValidador validador;

    @BeforeEach
    void setUp() {
        validador = new CnpjAlfanumericoValidador();
    }

    @Nested
    @DisplayName("Quando o CNPJ é válido")
    class CnpjValido {

        @ParameterizedTest(name = "Deve retornar true para o CNPJ válido {0}")
        @ValueSource(strings = {"12ABC34501DE35", "1A2B3C4D5E6F34"})
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
            assertThat(validador.isValido("12ABC34501DE36")).isFalse();
        }

        @Test
        @DisplayName("Deve retornar false quando os dígitos verificadores não são numéricos")
        void deveRetornarFalseQuandoDigitosVerificadoresNaoSaoNumericos() {
            assertThat(validador.isValido("12ABC34501DEFA")).isFalse();
        }

        @Test
        @DisplayName("Deve retornar false quando a base do CNPJ não é alfanumérica")
        void deveRetornarFalseQuandoBaseNaoAlfanumerica() {
            assertThat(validador.isValido("12.ABC.345/01DE-35")).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada com tamanho incorreto: [{0}]")
        @ValueSource(strings = {"12ABC34501DE3", "12ABC34501DE355"})
        void deveRetornarFalseParaTamanhoIncorreto(String cnpj) {
            assertThat(validador.isValido(cnpj)).isFalse();
        }

        @ParameterizedTest(name = "Deve retornar false para entrada nula, vazia ou em branco")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        void deveRetornarFalseParaEntradaNulaOuVazia(String cnpj) {
            assertThat(validador.isValido(cnpj)).isFalse();
        }

        @Test
        @DisplayName("Deve retornar false para CNPJ numérico, pois não é sua responsabilidade")
        void deveRetornarFalseParaCnpjNumerico() {
            assertThat(validador.isValido("33000167000101")).isFalse();
        }
    }
}