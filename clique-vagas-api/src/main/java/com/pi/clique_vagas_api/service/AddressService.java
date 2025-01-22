package com.pi.clique_vagas_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.AddressModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.repository.AddressUserRepository;
import com.pi.clique_vagas_api.resources.dto.address.AddressDto;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class AddressService {

    @Autowired
    private AddressUserRepository addressRepository;

    public AddressModel createAddress(AddressDto addressDto, UserModel user) {
        var address = new AddressModel(
                null,
                user,
                addressDto.cep(),
                addressDto.street(),
                addressDto.number(),
                addressDto.neighborhood(),
                addressDto.city(),
                addressDto.state(),
                addressDto.city(),
                DateUtils.nowInZone(),
                null);
        return addressRepository.save(address);
    }

    public List<AddressModel> listAllAdresses() {
        return addressRepository.findAll();
    }

    public AddressModel getAddressById(AddressModel address) {
        return addressRepository.findById(address.getId())
                .orElseThrow(() -> new RuntimeException("Address not found" + address.getId()));
    }

    public AddressModel getByUserId(UserModel user) {
        return addressRepository.findByUser(user);
    }

    public void updateAddress(Long addressId, AddressDto addressDto) {

        var addressModel = addressRepository.findById(addressId);

        if (addressModel.isPresent()) {
            var address = addressModel.get();

            if (addressDto.cep() != null)
                address.setCep(addressDto.cep());
            if (addressDto.city() != null)
                address.setCity(addressDto.city());
            if (addressDto.neighborhood() != null)
                address.setNeighborhood(addressDto.neighborhood());
            if (addressDto.state() != null)
                address.setState(addressDto.state());
            if (addressDto.street() != null)
                address.setStreet(addressDto.street());
            if (addressDto.country() != null)
                address.setCountry(addressDto.country());

            address.setUpdatedAt(DateUtils.nowInZone());

            addressRepository.save(address);
        }
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}
