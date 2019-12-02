package com.geekbrains.bootdata.services;

import com.geekbrains.bootdata.entities.Item;
import com.geekbrains.bootdata.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItemList(){
        return itemRepository.findAll();
    }

    public Page<Item> getItemList(Specification<Item> spec, PageRequest pageRequest){
        return itemRepository.findAll(spec, pageRequest);
    }

    public Page<Item> getItemList(PageRequest pageRequest){
        return itemRepository.findAll(pageRequest);
    }

    public Page<Item> findPaginated(Specification<Item> spec, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Item> list;

        if ( itemRepository.findAll(spec).size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize,  itemRepository.findAll(spec).size());
            list =  itemRepository.findAll(spec).subList(startItem, toIndex);
        }

        Page<Item> itemPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize),  itemRepository.findAll(spec).size());

        return itemPage;
    }



}
