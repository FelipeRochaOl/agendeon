package br.com.agendaon.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateCPFTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "00000000000",
            "11111111111",
            "22222222222",
            "33333333333",
            "44444444444",
            "55555555555",
            "66666666666",
            "77777777777",
            "88888888888",
            "99999999999"
    })
    public void test_ShouldReturnExceptionForRepeatNumbers(String cpf) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ValidateCPF(cpf).isValidCPF(),
                "Error validation CPF: CPF with numbers repeat"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "000000",
            "012.288.a.7"
    })
    public void test_ShouldReturnExceptionForMinChars(String cpf) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ValidateCPF(cpf).isValidCPF(),
                "Error validation CPF: CPF invalid length characters"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "460.205.110-30",
            "00770212077",
            "780.639.760-45 ",
            "061.026.639-00",
    })
    public void test_ShouldReturnValid(String cpf) {
        assertDoesNotThrow(() -> new ValidateCPF(cpf).isValidCPF());
        assertEquals(
                cpf.replaceAll("\\D", ""),
                new ValidateCPF(cpf).isValidCPF().getCpf()
        );
    }
}
