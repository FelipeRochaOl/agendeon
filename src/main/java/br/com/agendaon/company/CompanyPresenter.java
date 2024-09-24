package br.com.agendaon.company;

import br.com.agendaon.address.AddressPresenter;
import br.com.agendaon.user.UserPresenter;
import lombok.Data;

import java.util.UUID;

@Data
public class CompanyPresenter {
    private UUID id;
    private String name;
    private String companyName;
    private String cnpj;
    private AddressPresenter address;
    private UserPresenter user;

    public CompanyPresenter(CompanyModel companyModel) {
        this.id = companyModel.getId();
        this.name = companyModel.getTradeName();
        this.companyName = companyModel.getCompanyName();
        this.cnpj = companyModel.getCnpj();
        this.setAddress(companyModel);
        this.setUser(companyModel);
    }

    private void setAddress(CompanyModel companyModel) {
        this.address = new AddressPresenter(companyModel.getAddress());
    }

    private void setUser(CompanyModel companyModel) {
        this.user = new UserPresenter(companyModel.getUser());
    }
}
