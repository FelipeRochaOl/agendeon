package br.com.agendaon.address;

import lombok.Data;
import java.util.UUID;

@Data
public class AddressDTO {
    private UUID id;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String uf;
    private String country;
    private String zip;

    public void validZip() throws Exception {
        String zipcode = this.formatZipcode();
        if (zipcode.length() != 8) {
            throw new Exception("Zipcode must be 8 digits long");
        }
        this.zip = zipcode;
    }

    private String formatZipcode() {
        return this.zip.replaceAll("[^0-9]", "");
    }
}
