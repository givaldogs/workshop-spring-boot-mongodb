package com.educandoweb.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.workshopmongo.domain.Post;
import com.educandoweb.workshopmongo.repository.PostRepository;
import com.educandoweb.workshopmongo.services.exeception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;

	public Post findById(String id) {
		Optional<Post> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public List<Post> findByTitle(String Text) {
		return repo.searchTitle(Text);
		// return repo.findByTitleContainingIgnoreCase(Text);
	}

	/**
	 * acrescentar um dia na data maxima maxDate, porque vai ser gerada naquele dia
	 * informada pelo usuario so' que a meia noite. Na verdade se quer buscar os
	 * Posts ate' aquela data, nao pode ser ate' a meia noite daquele dia, tem que
	 * ser ate' as 24hs daquele dia , porque a nossa data ela e' armazenada em forma
	 * de milesegundos ela e'um instante, nao simplemente uma data DIA MES e ANO,
	 * ela tem hora,minuto,segundo e milesegundo. Entao se queremos buscar um Post
	 * ate' uma certa data, temos que considerar ate' o final daquele dia, ate as
	 * 24hs daquele dia. como eu estou fazendo para encontar uma data maxima uma
	 * comparacao menor ou igual, entao tem que fazer a comparacao ate' a meia noite
	 * do proximo dia. entao fazemos o macete de acrescentar um dia da data maxDate,
	 * da seguinte forma: maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 *
	 * 1000)
	 * 
	 */

	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.fullSearch(text, minDate, maxDate);
	}
}
