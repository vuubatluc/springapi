package com.vu.springapi.mapper;

import com.vu.springapi.dto.request.AddressCreateRequest;
import com.vu.springapi.dto.request.AddressUpdateRequest;
import com.vu.springapi.dto.response.AddressResponse;
import com.vu.springapi.model.Address;
import com.vu.springapi.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-26T02:40:30+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toAddress(AddressCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.label( request.getLabel() );
        address.street( request.getStreet() );
        address.city( request.getCity() );
        address.state( request.getState() );
        address.postalCode( request.getPostalCode() );
        address.country( request.getCountry() );

        return address.build();
    }

    @Override
    public AddressResponse toAddressResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponse addressResponse = new AddressResponse();

        addressResponse.setUserId( addressUserId( address ) );
        addressResponse.setId( address.getId() );
        addressResponse.setLabel( address.getLabel() );
        addressResponse.setStreet( address.getStreet() );
        addressResponse.setCity( address.getCity() );
        addressResponse.setState( address.getState() );
        addressResponse.setPostalCode( address.getPostalCode() );
        addressResponse.setCountry( address.getCountry() );

        return addressResponse;
    }

    @Override
    public void updateAddress(Address address, AddressUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        if ( request.getLabel() != null ) {
            address.setLabel( request.getLabel() );
        }
        if ( request.getStreet() != null ) {
            address.setStreet( request.getStreet() );
        }
        if ( request.getCity() != null ) {
            address.setCity( request.getCity() );
        }
        if ( request.getState() != null ) {
            address.setState( request.getState() );
        }
        if ( request.getPostalCode() != null ) {
            address.setPostalCode( request.getPostalCode() );
        }
        if ( request.getCountry() != null ) {
            address.setCountry( request.getCountry() );
        }
    }

    private Long addressUserId(Address address) {
        User user = address.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
