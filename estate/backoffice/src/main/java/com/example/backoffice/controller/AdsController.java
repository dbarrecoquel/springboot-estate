package com.example.backoffice.controller;

import com.example.ads.dto.AdsDto;
import com.example.ads.mapper.AdsMapper;
import com.example.ads.model.Ads;
import com.example.ads.model.AdsType;
import com.example.ads.service.AdsService;
import com.example.ads.service.AdsTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ads")
public class AdsController {

    private final AdsService adsService;
    private final AdsTypeService adsTypeService;
    private final AdsMapper adsMapper;

    public AdsController(AdsService adsService,
                         AdsTypeService adsTypeService,
                         AdsMapper adsMapper) {
        this.adsService    = adsService;
        this.adsTypeService = adsTypeService;
        this.adsMapper     = adsMapper;
    }

    /* ── LIST ─────────────────────────────────────────────── */
    @GetMapping
    public String list(Model model) {
        List<AdsDto> adsDtos = adsService.getAllAds()
                .stream()
                .map(adsMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("ads", adsDtos);
        return "ads/list";
    }

    /* ── CREATE FORM ──────────────────────────────────────── */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("ads", new Ads());
        model.addAttribute("adstypes", adsTypeService.getAllAdsType());
        model.addAttribute("formAction", "/ads/save");
        model.addAttribute("pageTitle", "Nouvelle annonce");
        return "ads/form";
    }

    /* ── EDIT FORM ────────────────────────────────────────── */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model,
                           RedirectAttributes ra) {
        return adsService.getAdsById(id)
                .map(ads -> {
                    model.addAttribute("ads", ads);
                    model.addAttribute("adstypes", adsTypeService.getAllAdsType());
                    
                    model.addAttribute("formAction", "/ads/save");
                    model.addAttribute("pageTitle", "Modifier l'annonce");
                    return "ads/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("errorMessage", "Annonce introuvable (id=" + id + ").");
                    return "redirect:/ads";
                });
    }

    /* ── SAVE (create + update) ───────────────────────────── */
    @PostMapping("/save")
    public String save(@ModelAttribute Ads ads,
                       @RequestParam(name = "adstypeId", required = false) Long adstypeId,
                       RedirectAttributes ra) {
    	if (ads.getId() != null) {
            adsService.getAdsById(ads.getId()).ifPresent(existing -> {
                ads.setCreatedAt(existing.getCreatedAt());
            });
        }
    	ads.setAdstypeId(adstypeId);
        boolean isNew = ads.getId() == null;
        adsService.saveAds(ads);
        ra.addFlashAttribute("successMessage",
                isNew ? "Annonce créée avec succès." : "Annonce modifiée avec succès.");
        return "redirect:/ads";
    }

    /* ── DELETE ───────────────────────────────────────────── */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        adsService.deleteAds(id);
        ra.addFlashAttribute("successMessage", "Annonce supprimée.");
        return "redirect:/ads";
    }
}