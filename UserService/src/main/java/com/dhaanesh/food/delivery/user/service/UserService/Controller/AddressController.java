package com.dhaanesh.food.delivery.user.service.UserService.Controller;

import com.dhaanesh.food.delivery.user.service.UserService.Entity.Address;
import com.dhaanesh.food.delivery.user.service.UserService.Service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{id}/addresses")
    public Address addAddress(@PathVariable Long id, @RequestBody Address address) {
        return addressService.addAddress(id, address);
    }

    @GetMapping("/{id}/addresses")
    public List<Address> getAddresses(@PathVariable Long id) {
        return addressService.getAddresses(id);
    }

    @PutMapping("/{id}/addresses/{addressId}")
    public Address updateAddress(@PathVariable Long addressId, @RequestBody Address address) {
        return addressService.updateAddress(addressId, address);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public String deleteAddress(@PathVariable Long addressId) {
        return addressService.deleteAddress(addressId);
    }
}
