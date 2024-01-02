package org.cmo.cmoportal.biodata;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cmo.cmoportal.cmoUser.MyUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/biodata")
@SessionAttributes("biodata")
@RequiredArgsConstructor
public class BiodataController {

    private final BiodataService biodataService;

    @ModelAttribute(name = "biodata")
    public Biodata biodata() {
        return new Biodata();
    }

    @GetMapping
    public String getBiodataForm(@AuthenticationPrincipal MyUserPrincipal userDetails, @ModelAttribute Biodata biodata, Model model) {
        Optional<Biodata> userBiodata = biodataService.findByUserId(userDetails.getCmoUser().getId());
        if(userBiodata.isPresent()) {
            model.addAttribute("biodataPresent", true);
        }
        biodata.setUser(userDetails.getCmoUser());
        return "biodata";
    }

    @PostMapping
    public String addBiodata(@Valid Biodata biodata, @AuthenticationPrincipal MyUserPrincipal userDetails) {
        biodata.setUser(userDetails.getCmoUser());
        log.info("submitted: {}", biodata.toString());
        biodataService.addBiodata(biodata);
        return "success";
    }
}
