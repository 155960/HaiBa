package com.zengqiang.future.controller;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.form.AddressForm;
import com.zengqiang.future.service.IAddressService;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/address")
@ResponseBody
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("add")
    public ServerResponse add(@RequestBody AddressForm form){
        try{
            return addressService.add(form);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

    @RequestMapping("/update")
    public ServerResponse update(@RequestBody AddressForm form){
        try{
            return addressService.update(form);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

    @RequestMapping("/delete")
    public ServerResponse delete(int addressId){
        try{
            return addressService.delete(addressId);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }


}
