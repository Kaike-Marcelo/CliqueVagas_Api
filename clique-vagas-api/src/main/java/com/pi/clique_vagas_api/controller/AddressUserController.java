package com.pi.clique_vagas_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.clique_vagas_api.model.AddressModel;
import com.pi.clique_vagas_api.resources.dto.address.AddressDto;
import com.pi.clique_vagas_api.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressUserController {

    @Autowired
    private AddressService addressService;

    @PutMapping("/{id}")
    public Long updateAddress(@PathVariable Long id, AddressDto addressDto) {
        AddressModel address = addressService.updateAddress(id, addressDto);
        return address.getId();
    }
}
