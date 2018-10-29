package com.springjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@EnableAutoConfiguration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/","/reg.html","/stylesheets/**","/css/**","/images/**", "/home", "/school.html","/schools","/crud.html","/crudJob.html","/jobs","/crudCity.html","/cities","/crudAnimal.html","/animals","/crudArena.html","/arenas","/crudArmor.html","/armors","/crudFight.html","/fights","/crudGladiator.html","/gladiators","/crudGladiatorialSchool.html","/crudMaster.html","/masters","/crudTicket.html","/tickets","/crudWeapon.html","/weapons","/users","/schools/findSchoolNames","/weapons/findWeaponByName/","/cities/name/","/cities/getCityNames","/arenas/findArenaByName/","/animals/findAnimalByName/","/gladiators/name/","/arenas/findArenaNames","/animals/findAnimalNames","/gladiators/findGladiatorNames","/gladiators/sortByWins","/animals/findAnimalByName/","/gladiators/name/","/schools/findSchoolByName/","/weapons/findWeaponByName/","/armors/findArmorByName/","/masters/findMasterByName/","/jobs/findJobByName/","/schools/findSchoolNames","/weapons/findWeaponNames","/armors/findArmorNames","/masters/findMasterNames","/jobs/findJobNames","/gladiators/findOne/**","/schools/update/**","/cities/name/**","/animals/findOne/**","/arenas/findOne/**","/armors/findOne/**","/cities/findOne/id/**","/fights/findOne/**","/schools/findOne/**","/jobs/findOne/**","/masters/findOne/**","/tickets/findOne/**","/weapons/findOne/**","/fights/getIdFights","/arenas/findArenaByName/**").permitAll()
                .antMatchers("/addJob/","/gladiators/save","/gladiators/delete/**","/gladiators/update/**","/cities/delete/**","/cities/update/**","/cities/save", "/animals/update/**","/animals/save","/animals/delete/**","/arenas/update/**","/arenas/save","/arenas/delete/**","/armors/update/**","/armors/save","/armors/delete/**","/fights/update/**","/fights/save","/fights/delete/**","/schools/save","/schools/delete/**","/jobs/update/**","/jobs/save","/jobs/delete/**","/masters/update/**","/masters/save","/masters/delete/","/tickets/delete/**","/tickets/save","/tickets/update/**","/weapons/update/**","/weapons/save","/weapons/delete/**").hasAnyRole("ADMIN")
                .antMatchers("users/buyTicket","/BuyTickets","/PersonalArea").hasAnyRole("USER")
                .antMatchers("/BuyGladiators","/BuyTickets","/masters/sellGladiator","users/buyTicket","/BuyTickets","/PersonalArea","/masters/buyGladiator/").hasAnyRole("MASTER")
                .antMatchers("/gladiators/buyWeapon","/BuyArmors","/gladiators/buyArmor","/gladiators/applyToSchool","users/buyTicket","/BuyTickets","/PersonalArea").hasAnyRole("GLADIATOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/reg")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private javax.sql.DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from user_roles where username=?");



    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
