package br.com.agendaon.company;

import br.com.agendaon.address.AddressModel;
import br.com.agendaon.address.AddressService;
import br.com.agendaon.category.CategoryModel;
import br.com.agendaon.category.CategoryService;
import br.com.agendaon.session.SessionModel;
import br.com.agendaon.session.SessionService;
import br.com.agendaon.user.UserModel;
import br.com.agendaon.user.UserService;
import br.com.agendaon.utils.Utils;
import br.com.agendaon.utils.ValidateCNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CategoryService categoryService;


    public List<CompanyPresenter> findAll() {
        List<CompanyModel> companies = this.companyRepository.findAll();
        List<CompanyPresenter> result = new ArrayList<>();
        companies.forEach(company -> result.add(new CompanyPresenter(company)));
        return result;
    }

    public CompanyPresenter findOneByUser(UUID userId) {
        Optional<CompanyModel> company = this.companyRepository.findByUserId(userId);
        if (company.isEmpty()) return null;
        return new CompanyPresenter(company.get());
    }

    public List<CompanyPresenter> findBySession(UUID sessionId) {
        List<CompanyModel> companies = this.companyRepository.findBySessionCode(sessionId);
        List<CompanyPresenter> result = new ArrayList<>();
        companies.forEach(company -> result.add(new CompanyPresenter(company)));
        return result;
    }

    public List<CompanyPresenter> findByCategory(UUID categoryId) {
        List<CompanyModel> companies = this.companyRepository.findByCategoryCode(categoryId);
        List<CompanyPresenter> result = new ArrayList<>();
        companies.forEach(company -> result.add(new CompanyPresenter(company)));
        return result;
    }

    public List<CompanyPresenter> findByFilter(FilterDTO filter) {
        List<CompanyModel> companies = this.companyRepository.filterCompany(
                filter.getCategory(),
                filter.getZip(),
                filter.getCity(),
                filter.getNeighborhood()
        );
        List<CompanyPresenter> result = new ArrayList<>();
        companies.forEach(company -> result.add(new CompanyPresenter(company)));
        return result;
    }

    public CompanyModel findOne(UUID id) {
        return this.companyRepository.findById(id).orElse(null);
    }

    public CompanyPresenter findById(UUID id) {
        CompanyModel company = this.companyRepository.findById(id).orElse(null);
        if (company == null) return null;
        return new CompanyPresenter(company);
    }

    public CompanyPresenter create(CompanyDTO companyDTO) {
        String cnpj = new ValidateCNPJ(companyDTO.getCnpj()).isValidCNPJ().getCnpj();
        companyDTO.setCnpj(cnpj);
        CompanyModel companyModel = new CompanyModel(companyDTO);
        SessionModel session = this.sessionService.findById(companyDTO.getSessionId());
        companyModel.setSession(session);
        CategoryModel category = this.categoryService.findOne(companyDTO.getCategoryId());
        companyModel.setCategory(category);
        AddressModel address = this.addressService.findOne(companyDTO.getAddressId());
        companyModel.setAddress(address);
        UserModel user = this.userService.findById(companyDTO.getUserId());
        companyModel.setUser(user);
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
