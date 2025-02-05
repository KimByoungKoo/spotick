package com.app.spotick.controller.place;

import com.app.spotick.domain.dto.place.PlaceDetailDto;
import com.app.spotick.domain.dto.place.PlaceEditDto;
import com.app.spotick.domain.dto.place.PlaceListDto;
import com.app.spotick.domain.dto.place.PlaceRegisterDto;
import com.app.spotick.domain.dto.place.reservation.PlaceReserveBasicInfoDto;
import com.app.spotick.domain.dto.place.reservation.PlaceReserveRegisterDto;
import com.app.spotick.domain.dto.user.UserDetailsDto;
import com.app.spotick.service.place.PlaceService;
import com.app.spotick.service.place.reservation.PlaceReservationService;
import com.app.spotick.util.type.SortType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/place/*")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    private final PlaceReservationService reservationService;

    @GetMapping("/detail/{placeId}")
    public String placeDetail(@PathVariable("placeId") Long placeId,
                              @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                              Model model) {
        Long userId = userDetailsDto == null ? null : userDetailsDto.getId();
        PlaceDetailDto place = placeService.findPlaceDetailById(placeId, userId);

        model.addAttribute("place", place);
        return "place/detail";
    }

    @GetMapping("/list")
    public String placeList(Model model,@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                            @RequestParam(name = "sort",defaultValue = "POPULARITY") String sort,
                            @RequestParam(name = "keyword", required = false) String keyword,
                            @PageableDefault(page =0,
                                    size = 12, sort = "id",
                                    direction = Sort.Direction.DESC
                            ) Pageable pageable){

        System.out.println("========================");
        System.out.println(keyword);
        System.out.println("========================");
        Long userId = userDetailsDto==null? null: userDetailsDto.getId();
        SortType sortType = SortType.valueOf(sort);

        Slice<PlaceListDto> placeList = placeService.findPlaceListPagination(pageable,userId,sortType,null,keyword);
        model.addAttribute("placeList",placeList);
        model.addAttribute("sortTypes", SortType.values());
        return "place/list";
    }

    @GetMapping("/register")
    public String placeRegister(@ModelAttribute PlaceRegisterDto placeRegisterDto) {
        return "place/register";
    }

    @PostMapping("/register")
    public String placeRegister(@Valid PlaceRegisterDto placeRegisterDto,
                                BindingResult result,
                                @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        if (result.hasErrors()) {
            return "place/register";
        }

        try {
            placeService.registerPlace(placeRegisterDto, userDetailsDto.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return "place/register";
        }

        return "redirect:/place/list";
    }

    @GetMapping("/check/reserve")
    public String placeReserve(@ModelAttribute PlaceReserveRegisterDto registerDto, Model model) {
        PlaceReserveBasicInfoDto placeReserveDefaultInfo = placeService
                .findPlaceReserveDefaultInfo(registerDto.getPlaceId());

        model.addAttribute("place", placeReserveDefaultInfo);
        return "place/reserve";
    }

    @PostMapping("/reserve/register")
    public String placeReservationRegister(@ModelAttribute PlaceReserveRegisterDto placeReserveRegisterDto,
                                           @AuthenticationPrincipal UserDetailsDto userDetailsDto, Model model) {

        if (!reservationService.isReservationAvailable(placeReserveRegisterDto)) {
            PlaceReserveBasicInfoDto placeReserveDefaultInfo = placeService
                    .findPlaceReserveDefaultInfo(placeReserveRegisterDto.getPlaceId());
            model.addAttribute("place", placeReserveDefaultInfo);
            model.addAttribute("invalidReservation", true);
            return "place/reserve";
        }

        reservationService.registerPlaceReservation(
                placeReserveRegisterDto, userDetailsDto.getId());
        return "redirect:/place/detail/" + placeReserveRegisterDto.getPlaceId();
    }

    @GetMapping("/edit/{placeId}")
    public String goToPlaceEdit(@PathVariable("placeId") Long placeId,
                                @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                Model model) {

        PlaceEditDto placeEditDto = placeService.findPlaceInfo(placeId, userDetailsDto.getId()).orElseThrow(
                NoSuchFieldError::new
        );

        model.addAttribute("placeEditDto", placeEditDto);
        return "/place/edit";
    }

    @PostMapping("/edit/{placeId}")
    public String placeEdit(@PathVariable("placeId") Long placeId,
                            @Valid @ModelAttribute("placeEditDto") PlaceEditDto placeEditDto,
                            BindingResult result,
                            @AuthenticationPrincipal UserDetailsDto userDetailsDto,
                            RedirectAttributes redirectAttributes) {

        System.out.println("placeEditDto = " + placeEditDto);

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                redirectAttributes.addFlashAttribute(error.getField() + "Error", error.getDefaultMessage());
            }
            return "redirect:/place/edit/" + placeId;
        }

        if (placeEditDto.getSaveFileIdList().size() +
                (hasFiles(placeEditDto.getPlaceNewFiles()) ? placeEditDto.getPlaceNewFiles().size() : 0) < 5) {
            redirectAttributes.addFlashAttribute("fileError", "파일은 총 5개 이상이어야 합니다.");
            return "redirect:/place/edit/" + placeId;
        }


        try {
            placeService.updatePlace(placeEditDto, userDetailsDto.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/place/edit/" + placeId;
        }

        return "redirect:/mypage/places";
    }

    private boolean hasFiles(List<MultipartFile> files) {
        return files != null && files.stream().anyMatch(file -> file.getSize() > 0);
    }


}










