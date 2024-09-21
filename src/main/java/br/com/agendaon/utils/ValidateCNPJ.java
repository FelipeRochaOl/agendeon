package br.com.agendaon.utils;

import java.util.HashMap;
import java.util.Map;

public class ValidateCNPJ {
    private final String cnpj;

    public ValidateCNPJ(String cnpj) {
        this.cnpj = this.getOnlyNumbers(cnpj);
    }

    private String getOnlyNumbers(String cnpj) {
        return cnpj.replaceAll("\\D", "");
    }

    public ValidateCNPJ isValidCNPJ() {
        try {
            if (this.cnpj.length() != 14) throw new Exception("CNPJ invalid length characters");
            if (this.isRepeatChar()) throw new Exception("CNPJ with numbers repeat");
            if (!this.validLastTwoDigits()) throw new Exception("CNPJ invalid");
            return this;
        } catch (Exception error) {
            System.out.println("Error validation CNPJ: " + error.getMessage());
            throw new IllegalArgumentException(error.getMessage());
        }
    }

    public String getCnpj() {
        return this.cnpj;
    }

    private Boolean isRepeatChar() {
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < this.cnpj.length(); i++) {
            if (count.containsKey(this.cnpj.charAt(i))) {
                count.put(this.cnpj.charAt(i), count.get(this.cnpj.charAt(i)) + 1);
            } else {
                count.put(this.cnpj.charAt(i), 1);
            }
        }
        return count.values().stream().findFirst().get() == 14;
    }

    private Boolean validLastTwoDigits() {
        String lastTwoDigits = this.cnpj.substring(this.cnpj.length() - 2);
        String DigitOne = this.calculateDigitOne().toString();
        String DigitTwo = this.calculateDigitTwo().toString();
        return lastTwoDigits.equals(DigitOne.concat(DigitTwo));
    }

    private Integer calculateDigitOne() {
        int calc = 0;
        int count = 5;
        String cnpj = this.cnpj.substring(0, this.cnpj.length() - 2);
        for (int i = 1; i <= cnpj.length(); i++) {
            if (count == 1) count = 9;
            int character = Character.getNumericValue(this.cnpj.charAt(i - 1));
            calc += (character * count);
            count--;
        }
        return this.checkResultIsTen(calc % 11);
    }

    private Integer calculateDigitTwo() {
        int calc = 0;
        int count = 6;
        String cnpj = this.cnpj.substring(0, this.cnpj.length() - 1);
        for (int i = 0; i < cnpj.length(); i++) {
            if (count == 1) count = 9;
            int character = Character.getNumericValue(this.cnpj.charAt(i));
            calc += (character * count);
            count--;
        }
        return this.checkResultIsTen(calc % 11);
    }

    private Integer checkResultIsTen(int calc) {
        if (calc < 2) return 0;
        return 11 - calc;
    }
}
