package main.java.br.com.princesampaio.docvalidator;

import br.com.princesampaio.docvalidator.model.TipoDocumento;
import br.com.princesampaio.docvalidator.model.ResultadoValidacao;
import br.com.princesampaio.docvalidator.DocumentoValidador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Validador de Documento")
class DocumentoValidadorTest {
    private static final String CPF_VALIDO = "39403006838"; // CPF VÁLIDO REAL
    private static final String CPF_VALIDO_FORMATADO = "394.030.068-38"; // CPF VÁLIDO REAL
    private static final String CPF_INVALIDO = "11111111111";

    private static final String CNPJ_VALIDO = "03229759000139";
    private static final String CNPJ_VALIDO_FORMATADO = "03.229.759/0001-39";
    private static final String CNPJ_INVALIDO = "00000000000000";

    private static final String CNPJ_ALFA_VALIDO = "12ABC34501DE35";
    private static final String CNPJ_ALFA_VALIDO_FORMATADO = "12.ABC.345/01DE-35";
    private static final String CNPJ_ALFA_INVALIDO = "12ABC34501DE36";


    @Nested
    @DisplayName("Testes para o método isValido")
    class IsValidoTestes {

        @ParameterizedTest
        @ValueSource(strings = {CPF_VALIDO, CPF_VALIDO_FORMATADO})
        @DisplayName("Deve retornar true para CPF válido")
        void deveRetornarTrueParaCpfValido(String cpf) {
            assertThat(DocumentoValidador.isValido(cpf)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {CNPJ_VALIDO, CNPJ_VALIDO_FORMATADO})
        @DisplayName("Deve retornar true para CNPJ numérico válido")
        void deveRetornarTrueParaCnpjNumericoValido(String cnpj) {
            assertThat(DocumentoValidador.isValido(cnpj)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {CNPJ_ALFA_VALIDO, CNPJ_ALFA_VALIDO_FORMATADO})
        @DisplayName("Deve retornar true para CNPJ alfanumérico válido")
        void deveRetornarTrueParaCnpjAlfanumericoValido(String cnpj) {
            assertThat(DocumentoValidador.isValido(cnpj)).isTrue();
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "})
        @DisplayName("Deve retornar false para entrada nula, vazia ou em branco")
        void deveRetornarFalseParaEntradaInvalida(String entrada) {
            assertThat(DocumentoValidador.isValido(entrada)).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {CPF_INVALIDO, CNPJ_INVALIDO, CNPJ_ALFA_INVALIDO, "12345678900", "12345", "documento_invalido"})
        @DisplayName("Deve retornar false para documentos com formato ou dígito verificador inválido")
        void deveRetornarFalseParaDocumentoInvalido(String documento) {
            assertThat(DocumentoValidador.isValido(documento)).isFalse();
        }
    }

    @Nested
    @DisplayName("Testes para o método validarDocumento")
    class ValidarDocumentoTestes {

        @Test
        @DisplayName("Deve retornar resultado correto para CPF válido")
        void deveRetornarResultadoCorretoParaCpfValido() {
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(CPF_VALIDO_FORMATADO);

            assertThat(resultado.isValido()).isTrue();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.CPF);
            assertThat(resultado.valorOriginal()).isEqualTo(CPF_VALIDO_FORMATADO);
        }

        @Test
        @DisplayName("Deve retornar resultado correto para CPF inválido")
        void deveRetornarResultadoCorretoParaCpfInvalido() {
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(CPF_INVALIDO);

            assertThat(resultado.isValido()).isFalse();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.CPF);
            assertThat(resultado.valorOriginal()).isEqualTo(CPF_INVALIDO);
        }

        @Test
        @DisplayName("Deve retornar resultado correto para CNPJ numérico válido")
        void deveRetornarResultadoCorretoParaCnpjNumericoValido() {
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(CNPJ_VALIDO_FORMATADO);

            assertThat(resultado.isValido()).isTrue();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.CNPJ);
            assertThat(resultado.valorOriginal()).isEqualTo(CNPJ_VALIDO_FORMATADO);
        }

        @Test
        @DisplayName("Deve retornar resultado correto para CNPJ numérico inválido")
        void deveRetornarResultadoCorretoParaCnpjNumericoInvalido() {
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(CNPJ_INVALIDO);

            assertThat(resultado.isValido()).isFalse();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.CNPJ);
            assertThat(resultado.valorOriginal()).isEqualTo(CNPJ_INVALIDO);
        }

        @Test
        @DisplayName("Deve retornar resultado correto para CNPJ alfanumérico válido")
        void deveRetornarResultadoCorretoParaCnpjAlfanumericoValido() {
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(CNPJ_ALFA_VALIDO_FORMATADO);

            assertThat(resultado.isValido()).isTrue();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.CNPJ_ALFANUMERICO);
            assertThat(resultado.valorOriginal()).isEqualTo(CNPJ_ALFA_VALIDO_FORMATADO);
        }

        @Test
        @DisplayName("Deve retornar resultado correto para CNPJ alfanumérico inválido")
        void deveRetornarResultadoCorretoParaCnpjAlfanumericoInvalido() {
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(CNPJ_ALFA_INVALIDO);

            assertThat(resultado.isValido()).isFalse();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.CNPJ_ALFANUMERICO);
            assertThat(resultado.valorOriginal()).isEqualTo(CNPJ_ALFA_INVALIDO);
        }

        @Test
        @DisplayName("Deve retornar resultado INVALIDO para entrada nula")
        void deveRetornarResultadoInvalidoParaEntradaNula() {
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(null);

            assertThat(resultado.isValido()).isFalse();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.INVALIDO);
            assertThat(resultado.valorOriginal()).isEmpty();
        }

        @Test
        @DisplayName("Deve retornar resultado INVALIDO para entrada com tamanho incorreto")
        void deveRetornarResultadoInvalidoParaEntradaComTamanhoIncorreto() {
            String entrada = "12345";
            ResultadoValidacao resultado = DocumentoValidador.validarDocumento(entrada);

            assertThat(resultado.isValido()).isFalse();
            assertThat(resultado.tipoDocumento()).isEqualTo(TipoDocumento.INVALIDO);
            assertThat(resultado.valorOriginal()).isEqualTo(entrada);
        }
    }
}