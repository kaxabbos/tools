package com.tools.controller;

import com.tools.controller.main.Attributes;
import com.tools.model.Ordering;
import com.tools.model.StatTool;
import com.tools.model.Tool;
import com.tools.model.User;
import com.tools.model.enums.OrderingStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartCont extends Attributes {

    @GetMapping
    public String Cart(Model model) {
        addAttributesCart(model);
        return "cart";
    }

    @PostMapping("/add/{idTool}")
    public String CartAdd(@PathVariable Long idTool, @RequestParam int quantity) {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();

        for (Ordering i : orderingList) {
            if (i.getTool().getId().equals(idTool) && i.getOrderingStatus() == OrderingStatus.ISSUED) {
                i.addQuantity(quantity);
                orderingRepo.save(i);
                return "redirect:/tool/{idTool}";
            }
        }

        Tool tool = toolRepo.getReferenceById(idTool);
        Ordering ordering = new Ordering();
        ordering.addToolAndUser(tool, user);
        ordering.addQuantity(quantity);
        orderingRepo.save(ordering);
        return "redirect:/tool/{idTool}";
    }

    @PostMapping("/buy/{id}")
    public String CartBuy(Model model, @PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        if (ordering.getQuantity() > ordering.getTool().getQuantity()) {
            addAttributesCart(model);
            model.addAttribute("message", "Недостаточно инструментов!");
            return "cart";
        }
        StatTool statTool = ordering.getTool().getStatTool();
        statTool.addIncome(ordering.getQuantity(), ordering.getSum());
        ordering.setOrderingStatus(OrderingStatus.NOT_CONFIRMED);
        orderingRepo.save(ordering);
        return "redirect:/cart";
    }

    @GetMapping("/buy/all")
    public String CartBuyAll(Model model) {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();
        for (Ordering i : orderingList) {
            if (i.getQuantity() > i.getTool().getQuantity()) {
                addAttributesCart(model);
                model.addAttribute("message", "Недостаточно инструментов!");
                return "cart";
            }
        }
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.ISSUED) {
                StatTool statTool = i.getTool().getStatTool();
                statTool.addIncome(i.getQuantity(), i.getSum());
                i.setOrderingStatus(OrderingStatus.NOT_CONFIRMED);
            }
        }
        orderingRepo.saveAll(orderingList);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String CartDelete(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.removeQuantity();
        User user = ordering.getUser();
        user.removeOrdering(ordering);
        userRepo.save(user);
        return "redirect:/cart";
    }

    @GetMapping("/delete/all")
    public String CartDeleteAll() {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();
        List<Ordering> list = new ArrayList<>();
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.ISSUED) {
                list.add(i);
            }
        }
        for (Ordering i : list) {
            i.removeQuantity();
            orderingRepo.save(i);
            user.removeOrdering(i);
        }
        userRepo.save(user);
        return "redirect:/cart";
    }
}
