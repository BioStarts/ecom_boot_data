package com.geekbrains.bootdata.controllers;

import com.geekbrains.bootdata.entities.Item;
import com.geekbrains.bootdata.repositories.ItemRepository;
import com.geekbrains.bootdata.repositories.ItemSpecifications;
import com.geekbrains.bootdata.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {


    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /*@GetMapping("/items")
    @ResponseBody
    public List<Item> getAllItems() {
        return itemService.getItemList();
    }*/

    @GetMapping("/main")
    public String getAllProducts(Model model) {
        int currentPage = (1);
        int pageSize = (itemService.getItemList().size());
        Page<Item> page = itemService.getItemList(PageRequest.of(currentPage - 1, pageSize, Sort.Direction.ASC, "price"));
        model.addAttribute("items", page.getContent());
        model.addAttribute("itemsCount", page.getTotalElements());
        return "main_page";
    }

    @GetMapping("/filteringAndPaging")
    public String pagingExample(Model model,
                                @RequestParam(required = false, name = "min_price") Integer minPrice,
                                @RequestParam(required = false, name = "max_price") Integer maxPrice,
                                @RequestParam(required = false, name = "word") String word,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Specification<Item> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ItemSpecifications.priceGreaterThanOrEq(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ItemSpecifications.priceLesserThanOrEq(maxPrice));
        }
        if (word != null) {
            spec = spec.and(ItemSpecifications.titleContains(word));
        }

        Page<Item> pageItem = itemService.findPaginated(spec, PageRequest.of(currentPage - 1, pageSize, Sort.Direction.ASC, "price"));
        model.addAttribute("items", pageItem.getContent());
        model.addAttribute("itemsCount", pageItem.getTotalElements());
        model.addAttribute("pageItem", pageItem);
        //добавляет в модель страницы для пагинции
        int totalPages = pageItem.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "list";
    }


}
