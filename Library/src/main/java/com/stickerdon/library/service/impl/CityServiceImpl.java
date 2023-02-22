package com.stickerdon.library.service.impl;

import com.stickerdon.library.model.City;
import com.stickerdon.library.repository.CityRepository;
import com.stickerdon.library.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
