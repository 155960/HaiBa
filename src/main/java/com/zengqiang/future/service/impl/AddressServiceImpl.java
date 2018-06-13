package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.AddressMapper;
import com.zengqiang.future.form.AddressForm;
import com.zengqiang.future.pojo.Address;
import com.zengqiang.future.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    public ServerResponse add(AddressForm form){
        Address address=addressFormToAddress(form);
        int rowCount =addressMapper.insert(address);
        return ServerResponse.createBySuccess();
    }

    private Address addressFormToAddress(AddressForm form){
        Address address=new Address();
        address.setAddrDetail(form.getAddrDetail());
        address.setName(form.getName());
        address.setUserId(form.getUserId());
        address.setId(form.getId());
        return address;
    }

    public ServerResponse update(AddressForm form){
        Address address=addressFormToAddress(form);
        int rowCount=addressMapper.updateByPrimaryKeySelective(address);
        return ServerResponse.createBySuccess();
    }

    public ServerResponse delete(int addressId){
        int rowCount=addressMapper.deleteByPrimaryKey(addressId);
        return ServerResponse.createBySuccess();
    }
}
