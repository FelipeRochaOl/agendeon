package br.com.agendaon.address;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressPresenter {
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

    public AddressPresenter(AddressModel addressModel) {
        this.id = addressModel.getId();
        this.street = addressModel.getStreet();
        this.number = addressModel.getHouseNumber();
        this.complement = addressModel.getComplement();
        this.neighborhood = addressModel.getNeighborhood();
        this.city = addressModel.getCity();
        this.state = addressModel.getState();
        this.uf = addressModel.getUf();
        this.country = addressModel.getCountry();
        this.zip = addressModel.getZip();
    }
}
