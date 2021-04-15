package br.com.zupeacademy.wagner.mercadolivre.produto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// classe contendo a logica de enviar imagens para o produto / arquivos

@Component
public class EnvioDeArquivos{

	// metodo para enviar as imagens para o local de armazenamneto e receber os links das imagens. o set vai garantir
	// que receba links diferentes
	
	public Set<String> envia(List<MultipartFile> imagens) {
		
		// logica aqui
		
		return imagens.stream()
				.map(imagem -> "http://bucket.io/" + imagem.getOriginalFilename())
				.collect(Collectors.toSet());
	}

}
