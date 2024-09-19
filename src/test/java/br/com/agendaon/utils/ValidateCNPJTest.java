package br.com.agendaon.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateCNPJTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "00000000000000",
            "11111111111111",
            "22222222222222",
            "33333333333333",
            "44444444444444",
            "55555555555555",
            "66666666666666",
            "77777777777777",
            "88888888888888",
            "99999999999999"
    })
    public void test_ShouldReturnExceptionForRepeatNumbers(String cnpj) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ValidateCNPJ(cnpj).isValidCNPJ(),
                "Error validation CNPJ: CNPJ with numbers repeat"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "000000",
            "01.228.888.000/a7"
    })
    public void test_ShouldReturnExceptionForMinChars(String cnpj) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ValidateCNPJ(cnpj).isValidCNPJ(),
                "Error validation CNPJ: CNPJ invalid length characters"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "24.242.501/0001-42",
            "72.524.310/0001-16",
            "67.034.509/0001-26 ",
            "87705858000130",
    })
    public void test_ShouldReturnValid(String cnpj) {
        assertDoesNotThrow(() -> new ValidateCNPJ(cnpj).isValidCNPJ());
        assertEquals(
                cnpj.replaceAll("\\D", ""),
                new ValidateCNPJ(cnpj).isValidCNPJ().getCnpj()
        );
    }
}
