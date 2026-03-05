package com.gp.solutions.test.mapper;

import com.gp.solutions.test.dto.AddressDto;
import com.gp.solutions.test.dto.ArrivalTimeDto;
import com.gp.solutions.test.dto.ContactsDto;
import com.gp.solutions.test.dto.HotelCreateDto;
import com.gp.solutions.test.dto.HotelDetailedDto;
import com.gp.solutions.test.dto.HotelShortDto;
import com.gp.solutions.test.entity.Address;
import com.gp.solutions.test.entity.Amenity;
import com.gp.solutions.test.entity.ArrivalTime;
import com.gp.solutions.test.entity.Contacts;
import com.gp.solutions.test.entity.Hotel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public HotelShortDto toShortDto(Hotel hotel) {
        if (hotel == null) {
            return null;
        }

        String formattedAddress = "N/A";
        if (hotel.getAddress() != null) {
            Address address = hotel.getAddress();
            formattedAddress = String.format("%d %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry());
        }

        String phone = (hotel.getContacts() != null) ? hotel.getContacts().getPhone() : "N/A";

        return new HotelShortDto(
            hotel.getId(),
            hotel.getName(),
            hotel.getDescription(),
            formattedAddress,
            phone
        );
    }

    public HotelDetailedDto toDetailedDto(Hotel hotel) {
        if (hotel == null) {
            return null;
        }

        List<String> amenityNames = new ArrayList<>();
        if (hotel.getAmenities() != null) {
            amenityNames = hotel.getAmenities().stream()
                .map(Amenity::getName)
                .toList();
        }

        return new HotelDetailedDto(
            hotel.getId(),
            hotel.getName(),
            hotel.getDescription(),
            hotel.getBrand(),
            mapAddressToDto(hotel.getAddress()),
            mapContactsToDto(hotel.getContacts()),
            mapArrivalTimeToDto(hotel.getArrivalTime()),
            amenityNames
        );
    }

    public Hotel toEntity(HotelCreateDto dto) {
        if (dto == null) {
            return null;
        }

        Hotel hotel = new Hotel();
        hotel.setName(dto.name());
        hotel.setDescription(dto.description());
        hotel.setBrand(dto.brand());

        hotel.setAddress(mapDtoToAddress(dto.address()));
        hotel.setContacts(mapDtoToContacts(dto.contacts()));
        hotel.setArrivalTime(mapDtoToArrivalTime(dto.arrivalTime()));

        return hotel;
    }

    private AddressDto mapAddressToDto(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(address.getHouseNumber(), address.getStreet(), address.getCity(),
            address.getCountry(), address.getPostCode());
    }

    private Address mapDtoToAddress(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setHouseNumber(dto.houseNumber());
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setCountry(dto.country());
        address.setPostCode(dto.postCode());
        return address;
    }

    private ContactsDto mapContactsToDto(Contacts c) {
        if (c == null) {
            return null;
        }
        return new ContactsDto(c.getPhone(), c.getEmail());
    }

    private Contacts mapDtoToContacts(ContactsDto dto) {
        if (dto == null) {
            return null;
        }
        Contacts contacts = new Contacts();
        contacts.setPhone(dto.phone());
        contacts.setEmail(dto.email());
        return contacts;
    }

    private ArrivalTimeDto mapArrivalTimeToDto(ArrivalTime arrivalTime) {
        if (arrivalTime == null) {
            return null;
        }
        return new ArrivalTimeDto(arrivalTime.getCheckIn(), arrivalTime.getCheckOut());
    }

    private ArrivalTime mapDtoToArrivalTime(ArrivalTimeDto dto) {
        if (dto == null) {
            return null;
        }
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(dto.checkIn());
        arrivalTime.setCheckOut(dto.checkOut());
        return arrivalTime;
    }
}