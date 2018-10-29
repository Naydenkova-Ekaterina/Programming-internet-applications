package com.springjpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/school").setViewName("school");
        registry.addViewController("/crud").setViewName("crud");
        registry.addViewController("/crudJob").setViewName("crudJob");
        registry.addViewController("/crudArena").setViewName("crudArena");
        registry.addViewController("/crudCity").setViewName("crudCity");
        registry.addViewController("/crudWeapon").setViewName("crudWeapon");
        registry.addViewController("/crudAnimal").setViewName("crudAnimal");
        registry.addViewController("/crudArmor").setViewName("crudArmor");
        registry.addViewController("/crudGladiator").setViewName("crudGladiator");
        registry.addViewController("/crudFight").setViewName("crudFight");
        registry.addViewController("/crudTicket").setViewName("crudTicket");
        registry.addViewController("/reg").setViewName("reg");
        registry.addViewController("/BuyWeapons").setViewName("BuyWeapons");
        registry.addViewController("/BuyArmors").setViewName("BuyArmors");
        registry.addViewController("/BuyGladiators").setViewName("BuyGladiators");
        registry.addViewController("/BuyTickets").setViewName("BuyTickets");

        registry.addViewController("/PersonalArea").setViewName("PersonalArea");

        registry.addViewController("/crudMaster").setViewName("crudMaster");
        registry.addViewController("/crudGladiatorialSchool").setViewName("crudGladiatorialSchool");
    }

}
