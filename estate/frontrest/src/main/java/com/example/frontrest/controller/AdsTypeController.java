package com.example.frontrest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ads.dto.AdsTypeDto;
import com.example.ads.mapper.AdsTypeMapper;
import com.example.ads.service.AdsTypeService;

@RestController
@RequestMapping("/api/adstypes")
@CrossOrigin(origins = "*")
public class AdsTypeController {

	private final AdsTypeService adsTypeService;
	private final AdsTypeMapper adsTypeMapper;
	
	public AdsTypeController(AdsTypeService adsTypeService, AdsTypeMapper adsTypeMapper) {
		
		this.adsTypeService = adsTypeService;
		this.adsTypeMapper = adsTypeMapper;
	}
	
	@GetMapping
	public ResponseEntity<List<AdsTypeDto>> getAllAdsType() {
		List<AdsTypeDto> list = adsTypeService.getAllAdsType().stream()
				.map(adsTypeMapper::toDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
		
	}
}
