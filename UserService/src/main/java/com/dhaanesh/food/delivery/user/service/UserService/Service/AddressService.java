package com.dhaanesh.food.delivery.user.service.UserService.Service;

import com.dhaanesh.food.delivery.user.service.UserService.Entity.Address;
import com.dhaanesh.food.delivery.user.service.UserService.Entity.User;
import com.dhaanesh.food.delivery.user.service.UserService.Repository.AddressRepository;
import com.dhaanesh.food.delivery.user.service.UserService.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository
    ) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address addAddress(Long userId, Address address) {

        User user = userRepository.findById(userId).orElseThrow();

        address.setUser(user);

        return addressRepository.save(address);
    }

    public List<Address> getAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address updateAddress(
            Long addressId,
            Address updatedAddress
    ) {

        Address address = addressRepository
                .findById(addressId)
                .orElseThrow();

        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setPincode(updatedAddress.getPincode());

        return addressRepository.save(address);
    }

    public String deleteAddress(Long addressId) {

        addressRepository.deleteById(addressId);

        return "Address Deleted Successfully";
    }
}
