package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.beans.FlashCard;

@Service
public class FlashCardService {
	
	private static ArrayList<FlashCard> cards;
	static int count;
	
	static {
		System.out.println("[Debug] - FlashCardService instantiated");
		
		cards = new ArrayList<>();
		
		cards.add(new FlashCard(1, "test", "answer"));
        cards.add(new FlashCard(2, "What does DI promote?", "loose-coupling"));
        cards.add(new FlashCard(3, "What is the primary IOC container in Spring?", "ApplicationContext"));
        cards.add(new FlashCard(4, "What are the Spring stereotype annotations?", "@Component, @Controller, @Service, and @Repository"));
        cards.add(new FlashCard(5, "What is the difference between ApplicationContext and BeanFactory with regard to bean instantiation?",
                "BeanFactory lazily instantiates beans, while ApplicationContext eagerly loads beans"));
        
        count = 5;
	}
	
	public List<FlashCard> getAll() {
		return cards;
	}
	
	public FlashCard getById(int id) {
		
		return cards.stream().filter(card -> card.getId() == id).findFirst().get();
		
	}
	
	public FlashCard addCard(FlashCard fc) {
		fc.setId(++count);
		cards.add(fc);
		return fc;
	}
	
	public FlashCard updateCard(FlashCard fc) {
		if (getById(fc.getId()) == null) {
			return null;
		}
		getById(fc.getId()).setQuestion(fc.getQuestion());
		getById(fc.getId()).setAnswer(fc.getAnswer());
		return getById(fc.getId());
	}
	
	public void deleteCard(int id) {
		cards.removeIf(card -> card.getId() == id);
	}

}
