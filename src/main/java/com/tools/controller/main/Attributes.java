package com.tools.controller.main;

import com.tools.model.Ordering;
import com.tools.model.StatTool;
import com.tools.model.User;
import com.tools.model.enums.Category;
import com.tools.model.enums.OrderingStatus;
import com.tools.model.enums.Role;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attributes extends Main {

    protected void addAttributes(Model model) {
        model.addAttribute("role", getUserRole());
    }

    protected void addAttributesOrdering(Model model, Long idBuyer) {
        addAttributes(model);
        User selectedBuyer = userRepo.getReferenceById(idBuyer);
        model.addAttribute("buyers", userRepo.findAllByRole(Role.BUYER));
        model.addAttribute("selectedBuyer", selectedBuyer);
        List<Ordering> orderingList = selectedBuyer.getOrderingList();
        List<Ordering> res = new ArrayList<>();

        for (Ordering i : orderingList) if (i.getOrderingStatus() == OrderingStatus.NOT_CONFIRMED) res.add(i);
        for (Ordering i : orderingList) if (i.getOrderingStatus() == OrderingStatus.CONFIRMED) res.add(i);
        for (Ordering i : orderingList) if (i.getOrderingStatus() == OrderingStatus.SHIPPED) res.add(i);

        model.addAttribute("orderings", res);
    }

    protected void addAttributesCart(Model model) {
        addAttributes(model);
        model.addAttribute("carts", orderingRepo.findAllByUserAndOrderingStatus(getUser(), OrderingStatus.ISSUED));
    }

    protected void addAttributesIndex(Model model) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("tools", toolRepo.findAll());
    }

    protected void addAttributesIndex(Model model, Category category) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("tools", toolRepo.findAllByCategory(category));
    }

    protected void addAttributesStat(Model model) {
        addAttributes(model);
        List<StatTool> list = statToolRepo.findAll();
        for (StatTool i : list) {
            i.setIncome(round(i.getIncome(), 2));
        }
        statToolRepo.saveAll(list);
        list.sort(Comparator.comparing(StatTool::getIncome));
        Collections.reverse(list);
        double sum = 0;
        for (StatTool i : list) sum += i.getIncome();
        model.addAttribute("sum", Main.round(sum, 2));
        model.addAttribute("stats", list);

    }

    protected void addAttributesToolAdd(Model model) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
    }

    protected void addAttributesToolEdit(Model model, Long id) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("tool", toolRepo.getReferenceById(id));
    }

    protected void addAttributesTool(Model model, Long id) {
        addAttributes(model);
        model.addAttribute("tool", toolRepo.getReferenceById(id));
    }

    protected void addAttributesUsers(Model model) {
        addAttributes(model);
        model.addAttribute("actual", getUser());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
    }
}
