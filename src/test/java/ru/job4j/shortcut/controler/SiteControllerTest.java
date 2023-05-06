package ru.job4j.shortcut.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.Application;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.model.Url;
import ru.job4j.shortcut.service.SiteService;
import ru.job4j.shortcut.service.UrlService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SiteControllerTest {

    @MockBean
    private SiteService siteService;

    @MockBean
    private UrlService urlService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void whenConvert() throws Exception {
        var body = new HashMap<>() {{
            put("url", "sap.com");
        }};
        var om = new ObjectMapper().writeValueAsString(body);
        this.mvc.perform(post("/sites/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om))
                .andDo(print())
                .andExpect(status().isCreated());
        ArgumentCaptor<Url> argument = ArgumentCaptor.forClass(Url.class);
        verify(urlService).save(argument.capture());
        assertThat(argument.getValue().getUrl(), is("sap.com"));
    }

    @Test
    @WithMockUser
    public void whenRedirect() throws Exception {
        when(urlService.incrementIfPresentByCode("a")).thenReturn(true);
        this.mvc.perform(get("/sites/redirect/{code}", "a"))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    public void whenRegistration() throws Exception {
        var body = Map.of("site", "sap.com");
        var om = new ObjectMapper().writeValueAsString(body);
        this.mvc.perform(post("/sites/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om))
                .andDo(print())
                .andExpect(status().isCreated());
        ArgumentCaptor<Site> argument = ArgumentCaptor.forClass(Site.class);
        verify(siteService).save(argument.capture());
        assertThat(argument.getValue().getSite(), is("sap.com"));
    }

    @Test
    @WithMockUser
    public void whenAll() throws Exception {
        var site = new Site();
        site.setId(1);
        site.setLogin("login");
        HashMap body = new HashMap<>() {{
            put("1", "login");
        }};
        var json = new ObjectMapper().writeValueAsString(body);
        var sites = List.of(site);
        when(siteService.findAll()).thenReturn(sites);
        when(siteService.toMapByIdLogin(sites)).thenReturn(body);
        when(siteService.toJson(body)).thenReturn(json);
        this.mvc.perform(get("/sites/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    @WithMockUser
    public void whenStatistic() throws Exception {
        HashMap body = new HashMap<>() {{
            put("site.com", "total : 1");
        }};
        var list = List.of(new Url());
        var json = new ObjectMapper().writeValueAsString(body);
        when(urlService.findAll()).thenReturn(list);
        when(urlService.mapBuUrlCount(list)).thenReturn(body);
        when(siteService.toJson(body)).thenReturn(json);
        this.mvc.perform(get("/sites/statistic"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}