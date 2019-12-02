package com.geekbrains.bootdata.repositories;

import com.geekbrains.bootdata.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.rmi.CORBA.UtilDelegate;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

}