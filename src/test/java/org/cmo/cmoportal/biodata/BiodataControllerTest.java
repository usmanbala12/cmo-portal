package org.cmo.cmoportal.biodata;

import org.cmo.cmoportal.cmoUser.CmoUser;
import org.cmo.cmoportal.cmoUser.CmoUserRepository;
import org.cmo.cmoportal.cmoUser.MyUserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BiodataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CmoUserRepository cmoUserRepository;

    @Test
    public void testBiodataForm() throws Exception {

        CmoUser cmoUser = new CmoUser();
        cmoUser.setEnabled(true);
        cmoUser.setEmail("usmanxp12@gmail.com");
        cmoUser.setPassword("Gh@ni2001");
        cmoUser.setFirstName("usman");
        cmoUser.setLastName("bala");
        cmoUser.setRoles("user");
        CmoUser saved = cmoUserRepository.save(cmoUser);
        MyUserPrincipal userdetail = new MyUserPrincipal(saved);

        mockMvc.perform(post("/biodata").with(user(userdetail))
                .param("dateOfBirth","2023-07-29")
                .param("nationality","Nigerian")
                .param("stateOfOrigin","kano")
                .param("localGovernment","tarauni")
                .param("religion","islam")
                .param("contactNumber","07019299481")
                .param("nameOfSchool","bayero university")
                .param("schoolAddress","unguwa uku")
                .param("schoolClass","year 3")
                .param("fathersName","bala usman")
                .param("mothersName","zainab muhd")

                .with(csrf())
        ).andExpect(status().isOk());
    }

}