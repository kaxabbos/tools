package com.tools.controller;

import com.tools.controller.main.Attributes;
import com.tools.model.Ordering;
import com.tools.model.StatTool;
import com.tools.model.Tool;
import com.tools.model.User;
import com.tools.model.enums.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/tool")
public class ToolCont extends Attributes {
    @GetMapping("/{id}")
    public String tool(Model model, @PathVariable Long id) {
        addAttributesTool(model, id);
        return "tool";
    }

    @GetMapping("/add")
    public String toolAdd(Model model) {
        addAttributesToolAdd(model);
        return "toolAdd";
    }

    @PostMapping("/add")
    public String toolAddNew(Model model, @RequestParam String name, @RequestParam double price, @RequestParam Category category, @RequestParam int warranty, @RequestParam String country, @RequestParam String firm, @RequestParam int quantity, @RequestParam String date, @RequestParam MultipartFile file) {
        Tool tool = new Tool(name, price, category, warranty, country, firm, quantity, date);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось загрузить изображение");
                addAttributesToolAdd(model);
                return "toolAdd";
            }

            tool.setFile(res);
        }
        StatTool statTool = new StatTool();
        tool.setStatTool(statTool);
        statTool.setTool(tool);
        toolRepo.save(tool);
        statToolRepo.save(statTool);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String toolEdit(Model model, @PathVariable Long id) {
        addAttributesToolEdit(model, id);
        return "toolEdit";
    }

    @PostMapping("/edit/{id}")
    public String toolEditOld(Model model, @RequestParam String name, @RequestParam double price, @RequestParam Category category, @RequestParam int warranty, @RequestParam String country, @RequestParam String firm, @RequestParam int quantity, @RequestParam String date, @RequestParam MultipartFile file, @PathVariable Long id) {
        Tool tool = toolRepo.getReferenceById(id);
        tool.update(name, price, category, warranty, country, firm, quantity, date);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось загрузить изображение");
                addAttributesToolEdit(model, id);
                return "toolEdit";
            }
            tool.setFile(res);
        }

        toolRepo.save(tool);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String toolDelete(@PathVariable Long id) {
        toolRepo.deleteById(id);
        return "redirect:/";
    }
}
