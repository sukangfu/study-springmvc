package com.zkn.springmvc.service.impl;

import org.springframework.stereotype.Service;

import com.zkn.springmvc.domain.entity.Pet;
import com.zkn.springmvc.service.PetService;

@Service("petService")
public class PetServiceImpl implements PetService {

	/* (non-Javadoc)
	 * @see com.zkn.springmvc.service.impl.PetService#findPet(java.lang.Integer, java.lang.Integer)
	 */
	public Pet findPet(Integer ownerId, Integer petId){
		System.out.println("寻找宠物……ownerId="+ownerId + ",  petId=" +petId);
		
		Pet pet = new Pet();
		pet.setOwnerId(ownerId);
		pet.setPetId(petId);
		pet.setPetName("旺财");
		
		return pet;
	}
}
