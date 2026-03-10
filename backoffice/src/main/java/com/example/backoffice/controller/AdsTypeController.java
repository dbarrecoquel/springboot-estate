package com.example.backoffice.controller;

import com.example.ads.model.AdsType;
import com.example.ads.service.AdsTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adstypes")
public class AdsTypeController {

    private final AdsTypeService adsTypeService;

    public AdsTypeController(AdsTypeService adsTypeService) {
        this.adsTypeService = adsTypeService;
    }

    /* ── LIST ─────────────────────────────────────────────── */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("adstypes", adsTypeService.getAllAdsType());
        return "adstype/list";
    }

    /* ── CREATE FORM ──────────────────────────────────────── */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("adsType", new AdsType());
        model.addAttribute("formAction", "/adstypes/save");
        model.addAttribute("pageTitle", "Nouveau type d'annonce");
        return "adstype/form";
    }

    /* ── EDIT FORM ────────────────────────────────────────── */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model,
                           RedirectAttributes ra) {
        return adsTypeService.getAdsTypeById(id)
                .map(adsType -> {
                    model.addAttribute("adsType", adsType);
                    model.addAttribute("formAction", "/adstypes/save");
                    model.addAttribute("pageTitle", "Modifier le type d'annonce");
                    return "adstype/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("errorMessage", "Type introuvable (id=" + id + ").");
                    return "redirect:/adstypes";
                });
    }

    /* ── SAVE (create + update) ───────────────────────────── */
    @PostMapping("/save")
    public String save(@ModelAttribute AdsType adsType, RedirectAttributes ra) {
        boolean isNew = adsType.getId() == null;
        adsTypeService.saveAdsType(adsType);
        ra.addFlashAttribute("successMessage",
                isNew ? "Type créé avec succès." : "Type modifié avec succès.");
        return "redirect:/adstypes";
    }

    /* ── DELETE ───────────────────────────────────────────── */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        adsTypeService.deleteAdsType(id);
        ra.addFlashAttribute("successMessage", "Type supprimé.");
        return "redirect:/adstypes";
    }
}