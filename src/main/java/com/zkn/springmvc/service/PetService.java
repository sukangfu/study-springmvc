package com.zkn.springmvc.service;

import com.zkn.springmvc.domain.entity.Pet;

public interface PetService {

	public abstract Pet findPet(Integer ownerId, Integer petId);

}