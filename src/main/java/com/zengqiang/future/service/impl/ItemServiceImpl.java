package com.zengqiang.future.service.impl;

import com.zengqiang.future.common.ServerResponse;
import com.zengqiang.future.dao.ItemMapper;
import com.zengqiang.future.form.ItemForm;
import com.zengqiang.future.pojo.Item;
import com.zengqiang.future.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ItemMapper itemMapper;

    public ServerResponse update(ItemForm form){
        try{
            Item item=formToItem(form);
            int rowCount=itemMapper.updateByPrimaryKeySelective(item);
            if(rowCount>0){
                return ServerResponse.createBySuccess();
            }else{
                return ServerResponse.createByErrorMessage("更新失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }

    }

    public ServerResponse delete(int itemId){
        try{
            int rowCount=itemMapper.deleteByPrimaryKey(itemId);
            if(rowCount>0){
                return ServerResponse.createBySuccess();
            }else{
                return ServerResponse.createByErrorMessage("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

    private Item formToItem(ItemForm form){
        Item item=new Item();
        item.setId(form.getId());
        item.setTitle(form.getTitle());
        item.setNumber(form.getNumber());
        item.setPrice(form.getPrice());
        item.setItemDescribe(form.getItemDescribe());
        return item;
    }
}
