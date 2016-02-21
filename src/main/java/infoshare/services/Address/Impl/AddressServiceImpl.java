package infoshare.services.Address.Impl;

import infoshare.restapi.RestApiConnectorClass;
import infoshare.restapi.UrlPath;
import infoshare.domain.Address;
import infoshare.services.Address.AddressService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user9 on 2015/07/28.
 */
public class AddressServiceImpl implements AddressService {
    private static Map<String,Address> addressMap = null;

    public AddressServiceImpl() {
        if(addressMap == null){
            addressMap = new HashMap<>();
            Address address = new Address.Builder("24 killarney street")
                    .postalAddress("p.o box").addressType("fdfd").postalCode("7530").id("1").build();

            addressMap.put(address.getId(),address);
        }
    }

    @Override
    public Address find(String s) {
        //return addressMap.get(s);
       return RestApiConnectorClass.read(UrlPath.AddressLinks.GET_ID,s, Address.class);
    }

    @Override
    public Address save(Address entity) {
        return RestApiConnectorClass.create(UrlPath.AddressLinks.POST,entity,Address.class);
    }

    @Override
    public Address merge(Address entity) {
        addressMap.put(entity.getId(),entity);
        return addressMap.get(entity.getId());
    }

    @Override
    public void remove(Address entity) {
    }

    @Override
    public List<Address> findAll() {
        return new ArrayList<>(addressMap.values());
    }
}
