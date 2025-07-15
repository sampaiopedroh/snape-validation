# 🇧🇷 Validador de Documentos Brasileiros

[![Versão do Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Versão do Maven](https://img.shields.io/badge/Maven-3.8%2B-brightgreen.svg)](https://maven.apache.org/)

Uma biblioteca Java moderna, leve e sem dependências para validar os principais documentos brasileiros: **CPF**, **CNPJ** e o novo **CNPJ Alfanumérico**.

Projetada para ser simples, performática e fácil de integrar em qualquer projeto Java que precise garantir a integridade de dados cadastrais.

## ✨ Funcionalidades

*   ✅ **Validação de CPF:** Suporte completo ao cálculo de dígitos verificadores de CPF.
*   ✅ **Validação de CNPJ:** Valida CNPJs numéricos padrão.
*   ✅ **Validação de CNPJ Alfanumérico:** Suporte ao novo formato de CNPJ que inclui letras, conforme especificações do SERPRO.
*   🚀 **Alta Performance:** Lógica otimizada e uso de `Pattern` pré-compilado para limpeza de strings.
*   ⚙️ **API Fluente e Simples:** Dois métodos principais para atender a todas as necessidades, do uso simples ao detalhado.
*   💅 **Flexibilidade de Formato:** Valida documentos com ou sem formatação (ex: `123.456.789-00` e `12345678900`).
*   📦 **Zero Dependências:** Biblioteca "pura", não adiciona nenhum peso ou dependência transitiva ao seu projeto.
*   ☕ **Java 21:** Escrita com recursos modernos do Java para garantir um código limpo e eficiente.

## 🛠️ Instalação

Para usar a biblioteca em seu projeto Maven, adicione a seguinte dependência ao seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>br.com.princesampaio</groupId>
    <artifactId>doc-validator</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 💡 Como Usar
A biblioteca oferece uma única classe de entrada, DocumentoValidador, com métodos estáticos para facilitar o uso.
### Uso Básico: isValido()
Para uma verificação rápida, que retorna apenas true ou false.
```java
import br.com.princesampaio.docvalidator.DocumentoValidador;

public class ExemploSimples {
    public static void main(String[] args) {
        String cpfValido = "852.893.980-44";
        String cnpjInvalido = "11.111.111/1111-11";

        if (DocumentoValidador.isValido(cpfValido)) {
            System.out.println("O CPF " + cpfValido + " é válido!");
            // >> O CPF 852.893.980-44 é válido!
        }

        if (!DocumentoValidador.isValido(cnpjInvalido)) {
            System.out.println("O CNPJ " + cnpjInvalido + " é inválido!");
            // >> O CNPJ 11.111.111/1111-11 é inválido!
        }
    }
}
```

### Uso Avançado: validarDocumento()
Para obter um resultado detalhado, incluindo o tipo do documento e o status da validação, em um objeto ResultadoValidacao.
```java
import br.com.princesampaio.docvalidator.DocumentoValidador;
import br.com.princesampaio.docvalidator.model.ResultadoValidacao;
import br.com.princesampaio.docvalidator.model.TipoDocumento;

public class ExemploDetalhado {
    public static void main(String[] args) {
        // Exemplo 1: CNPJ Alfanumérico Válido
        ResultadoValidacao resultadoAlfa = DocumentoValidador.validarDocumento("12ABC34501DE35");

        System.out.println(resultadoAlfa.tipoDocumento()); // >> CNPJ_ALFANUMERICO
        System.out.println(resultadoAlfa.isValido());      // >> true
        System.out.println(resultadoAlfa);                  // >> ResultadoValidacao[valorOriginal=12ABC34501DE35, tipoDocumento=CNPJ_ALFANUMERICO, isValido=true]

        System.out.println("---");

        // Exemplo 2: CPF Inválido
        ResultadoValidacao resultadoCpf = DocumentoValidador.validarDocumento("123.456.789-00");
        
        if (resultadoCpf.tipoDocumento() == TipoDocumento.CPF && !resultadoCpf.isValido()) {
            System.out.println("O documento foi identificado como CPF, mas é inválido.");
            // >> O documento foi identificado como CPF, mas é inválido.
        }
    }
}
```

## 📖 API de Referência

A API pública é composta por uma classe principal e dois objetos de modelo.

### Classe `DocumentoValidador`

| Método                                                      | Retorno                  | Descrição                                                                                             |
| ----------------------------------------------------------- | ------------------------ | ----------------------------------------------------------------------------------------------------- |
| ``public static boolean isValido(String doc)``                | ``boolean``              | Retorna `true` se o documento for válido e `false` caso contrário.                                    |
| ``public static ResultadoValidacao validarDocumento(String doc)`` | ``ResultadoValidacao``   | Retorna um objeto com detalhes sobre o tipo de documento, o valor original e o status de validação. |

### Record `ResultadoValidacao`

Este é um `record` imutável que contém o resultado detalhado da validação.

| Atributo          | Tipo              | Descrição                                                                 |
| ----------------- | ----------------- | ------------------------------------------------------------------------- |
| ``valorOriginal`` | ``String``        | O documento original, exatamente como foi passado para o método.          |
| ``tipoDocumento`` | ``TipoDocumento`` | O tipo de documento identificado (CPF, CNPJ, etc.).                       |
| ``isValido``      | ``boolean``       | O resultado da validação (`true` para válido, `false` para inválido).   |

### Enum `TipoDocumento`

Representa os possíveis tipos de documento que a biblioteca pode identificar.

| Valor                 | Descrição                                         |
| --------------------- | ------------------------------------------------- |
| ``CPF``               | Documento identificado como um CPF.               |
| ``CNPJ``              | Documento identificado como um CNPJ numérico.       |
| ``CNPJ_ALFANUMERICO`` | Documento identificado como um CNPJ alfanumérico. |
| ``INVALIDO``          | A entrada não corresponde a nenhum formato conhecido. |

---

## 🤝 Contribuindo

Contribuições são muito bem-vindas! Se você encontrar um bug ou tiver uma sugestão de melhoria, sinta-se à vontade para abrir uma *Issue* ou um *Pull Request*.

1.  Faça um **Fork** deste repositório.
2.  Crie uma nova branch (`git checkout -b feature/sua-feature`).
3.  Faça suas alterações e adicione testes.
4.  Certifique-se de que todos os testes estão passando (`mvn clean install`).
5.  Envie um **Pull Request**.