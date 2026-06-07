package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.SettingsRequestDto;
import com.discord.Dashboard.dto.SettingsResponseDto;
import com.discord.Dashboard.service.SettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    public SettingsResponseDto getMySettings() {
        return settingsService.getMySettings();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public SettingsResponseDto updateSettings(
            @Valid @RequestBody SettingsRequestDto requestDto
    ) {
        return settingsService.saveOrUpdate(requestDto);
    }

}
