package br.com.agendaon.utils;

import java.util.HashMap;
import java.util.Map;

public class ValidateCPF {
    private final String cpf;

    public ValidateCPF() {
        this.cpf = "";
    }

    public ValidateCPF(String cpf) {
        this.cpf = this.getOnlyNumbers(cpf);
    }

    private String getOnlyNumbers(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    public ValidateCPF isValidCPF() {
        try {
            if (this.cpf.length() != 11) throw new Exception("CPF invalid length characters");
            if (this.isRepeatChar()) throw new Exception("CPF with numbers repeat");
            if (!this.validLastTwoDigits()) throw new Exception("CPF invalid");
            return this;
        } catch (Exception error) {
            System.out.println("Error validation CPF: " + error.getMessage());
            throw new IllegalArgumentException(error.getMessage());
        }
    }

    public String getCpf() {
        return cpf;
    }

    private Boolean isRepeatChar() {
        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < this.cpf.length(); i++) {
            if (count.containsKey(this.cpf.charAt(i))) {
                count.put(this.cpf.charAt(i), count.get(this.cpf.charAt(i)) + 1);
            } else {
                count.put(this.cpf.charAt(i), 1);
            }
        }
        return count.values().stream().findFirst().get() == 11;
    }

    private Boolean validLastTwoDigits() {
        String lastTwoDigits = this.cpf.substring(this.cpf.length() - 2);
        String DigitOne = this.calculateDigitOne().toString();
        String DigitTwo = this.calculateDigitTwo().toString();
        return lastTwoDigits.equals(DigitOne.concat(DigitTwo));
    }

    private Integer calculateDigitOne() {
        int calc = 0;
        String cpf = this.cpf.substring(0, this.cpf.length() - 2);
        for (int i = 1; i <= cpf.length(); i++) {
            int character = Character.getNumericValue(this.cpf.charAt(i - 1));
            calc += (character * i);
        }
        return this.checkResultIsTen(calc % 11);
    }

    private Integer calculateDigitTwo() {
        int calc = 0;
        String cpf = this.cpf.substring(0, this.cpf.length() - 1);
        for (int i = 0; i < cpf.length(); i++) {
            int character = Character.getNumericValue(this.cpf.charAt(i));
            calc += (character * i);
        }
        return this.checkResultIsTen(calc % 11);
    }

    private Integer checkResultIsTen(int calc) {
        if (calc == 10) return 0;
        return calc;
    }
}
