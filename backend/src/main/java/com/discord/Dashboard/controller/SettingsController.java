package com.discord.Dashboard.controller;

import com.discord.Dashboard.dto.ApiResponse;
import com.discord.Dashboard.dto.SettingsRequestDto;
import com.discord.Dashboard.dto.SettingsResponseDto;
import com.discord.Dashboard.service.SettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    public ResponseEntity<ApiResponse<SettingsResponseDto>> getMySettings() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                HttpStatus.OK.value(),
                                "Settings...",
                                settingsService.getMySettings()
                        )
                );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<SettingsResponseDto>> updateSettings(
            @Valid @RequestBody SettingsRequestDto requestDto
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                HttpStatus.CREATED.value(),
                                "Updated Settings",
                                settingsService.saveOrUpdate(requestDto)
                        )
                );
    }


}
