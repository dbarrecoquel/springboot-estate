package com.example.ads.mapper;

import org.springframework.stereotype.Component;

import com.example.ads.dto.AdsTypeDto;
import com.example.ads.model.AdsType;

@Component
public class AdsTypeMapper {
	
	public AdsTypeDto toDto(AdsType adsType)
	{
		if (adsType ==  null)
			return null;
		
		AdsTypeDto dto = new AdsTypeDto();
		dto.setId(adsType.getId());
		dto.setName(adsType.getName());
		
		return dto;
	}
}
