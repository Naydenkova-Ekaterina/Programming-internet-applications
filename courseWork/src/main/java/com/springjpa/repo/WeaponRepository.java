package com.springjpa.repo;

import com.springjpa.models.Weapon;
import org.springframework.data.repository.CrudRepository;

public interface WeaponRepository extends CrudRepository<Weapon,Integer> {
    public Weapon findByName(String name);
    // Weapon findByPicture(byte[]picture);

}
