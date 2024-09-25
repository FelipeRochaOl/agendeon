package br.com.agendaon.address;

import br.com.agendaon.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<AddressPresenter> findAll() {
        List<AddressModel> addresses = this.addressRepository.findAll();
        List<AddressPresenter> result = new ArrayList<>();
        addresses.forEach(address -> result.add(new AddressPresenter(address)));
        return result;
    }

    public AddressModel findOne(UUID id) {
        return this.addressRepository.findById(id).orElseThrow(() -> null);
    }

    public AddressPresenter findById(UUID id) {
        AddressModel address = this.findOne(id);
        return new AddressPresenter(address);
    }

    public AddressPresenter create(AddressDTO addressDTO) {
        Optional<AddressModel> addressSearch = this.addressRepository.findByZip(addressDTO.getZip());
        if (addressSearch.isPresent()) {
            return new AddressPresenter(addressSearch.get());
        }
        AddressModel addressModel = new AddressModel(addressDTO);
        AddressModel address = this.addressRepository.save(addressModel);
        return new AddressPresenter(address);
    }

    public AddressPresenter update(AddressDTO addressDTO, UUID id) throws Exception {
        AddressModel address = this.addressRepository.findById(id).orElseThrow(() -> null);
        if (addressDTO.getZip() != null) addressDTO.validZip();
        Utils.copyNonNullProperties(addressDTO, address);
        this.addressRepository.save(address);
        return this.findById(id);
    }

    public Boolean delete(UUID id) {
        try {
            this.addressRepository.deleteById(id);
            return true;
        } catch (Exception error) {
            System.out.println("Error delete address " + error.getMessage());
            return false;
        }
    }
}
