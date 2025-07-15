package br.com.princesampaio;

import br.com.princesampaio.docvalidator.DocumentoValidador;
import br.com.princesampaio.docvalidator.model.ResultadoValidacao; // Assumi que o nome foi traduzido

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("==  INICIANDO TESTES DA BIBLIOTECA DE VALIDAÇÃO  ==");
        System.out.println("==============================================\n");

        // Lista de documentos para testar
        // --- INÍCIO DA CORREÇÃO ---
        // Removido o 'null' da lista, pois List.of() não permite elementos nulos.
        List<String> documentosParaTestar = List.of(
                // --- CPFs ---
                "85289398044",
                "123.456.789-09",
                "111.111.111-11",
                "123.456.789-00",

                // --- CNPJs Numéricos ---
                "33000167000101",
                "51.946.973/0001-70",
                "22222222222222",
                "51.946.973/0001-71",

                // --- CNPJs Alfanuméricos ---
                "12ABC34501DE35",
                "1A.2B3.C4D/5E6F-34",
                "12ABC34501DE36",

                // --- Entradas Inválidas ---
                "12345",
                "documento invalido",
                "",
                "   "
        );
        // --- FIM DA CORREÇÃO ---

        // Itera sobre a lista e testa cada documento
        for (String doc : documentosParaTestar) {
            testarDocumento(doc);
        }

        // --- INÍCIO DA CORREÇÃO ---
        // Teste dedicado para o caso nulo, que foi removido da lista.
        testarDocumento(null);
        // --- FIM DA CORREÇÃO ---
    }

    /**
     * Método auxiliar para testar um único documento e imprimir os resultados de forma organizada.
     * @param documento O documento a ser testado.
     */
    private static void testarDocumento(String documento) {
        // Usa aspas para deixar claro quando a string é nula ou vazia
        String docFormatado = (documento == null) ? "null" : "\"" + documento + "\"";
        System.out.println("----------------------------------------------");
        System.out.println("Testando Documento: " + docFormatado);
        System.out.println("----------------------------------------------");

        // Teste 1: Usando o método simples isValido()
        boolean isValido = DocumentoValidador.isValido(documento);
        System.out.println("Resultado de isValido(): " + isValido);

        // Teste 2: Usando o método detalhado validarDocumento()
        // OBS: Renomeei ValidationResult para ResultadoValidacao para corresponder ao seu import
        ResultadoValidacao resultado = DocumentoValidador.validarDocumento(documento);
        System.out.println("Resultado de validarDocumento(): " + resultado);
        System.out.println();
    }
}