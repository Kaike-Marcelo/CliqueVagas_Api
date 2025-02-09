package com.pi.clique_vagas_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.model.AddressModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.repository.AddressUserRepository;
import com.pi.clique_vagas_api.resources.dto.address.PostAddressDto;
import com.pi.clique_vagas_api.resources.dto.address.GetAddressDto;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class AddressService {

    @Autowired
    private AddressUserRepository addressRepository;

    public AddressModel createAddress(PostAddressDto addressDto, UserModel user) {
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

    public GetAddressDto getAddressDtoByUserId(UserModel user) {
        var address = addressRepository.findByUser(user);
        return new GetAddressDto(
                address.getId(),
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState());
    }

    public AddressModel updateAddress(Long addressId, PostAddressDto addressDto) {

        var addressModel = addressRepository.findById(addressId);

        if (!addressModel.isPresent())
            throw new RuntimeException("Address not found");

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

        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}
