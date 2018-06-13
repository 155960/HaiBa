package com.zengqiang.future.service;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.AddressForm;

public interface IAddressService {
    ServerResponse add(AddressForm form);
    ServerResponse update(AddressForm form);
    ServerResponse delete(int addressId);
}
