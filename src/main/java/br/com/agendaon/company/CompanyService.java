package br.com.agendaon.company;

import br.com.agendaon.address.AddressModel;
import br.com.agendaon.address.AddressService;
import br.com.agendaon.utils.Utils;
import br.com.agendaon.utils.ValidateCNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;


    public List<CompanyPresenter> findAll() {
        List<CompanyModel> companies = this.companyRepository.findAll();
        List<CompanyPresenter> result = new ArrayList<>();
        companies.forEach(company -> result.add(new CompanyPresenter(company)));
        return result;
    }

    public CompanyPresenter findById(UUID id) {
        CompanyModel company = this.companyRepository.findById(id).orElse(null);
        if (company == null) return null;
        return new CompanyPresenter(company);
    }

    public CompanyPresenter create(CompanyDTO companyDTO) {
        String cnpj = new ValidateCNPJ(companyDTO.getCnpj()).isValidCNPJ().getCnpj();
        companyDTO.setCnpj(cnpj);
        AddressModel address = this.addressService.findOne(companyDTO.getAddressId());
        CompanyModel companyModel = new CompanyModel(companyDTO);
        companyModel.setAddress(address);
        CompanyModel company = this.companyRepository.save(companyModel);
        return new CompanyPresenter(company);
    }

    public CompanyPresenter update(CompanyDTO companyDTO) {
        CompanyModel company = this.companyRepository.findById(companyDTO.getId()).orElseThrow(() -> null);
        Utils.copyNonNullProperties(companyDTO, company);
        this.companyRepository.save(company);
        return this.findById(companyDTO.getId());
    }


    public Boolean delete(UUID id) {
        try {
            this.companyRepository.deleteById(id);
            return true;
        } catch (Exception error) {
            System.out.println("Error delete company " + error.getMessage());
            return false;
        }
    }

}
