package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import app.entity.Entrada;
import app.entity.Saida;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculoServiceTest {

	@Autowired
	CalculoService calculoService;

	@Test
	@DisplayName("TESTE UNITÁRIO DE SOMA DE 2 + 3")
	void testarSoma() {
		List<Integer> lista = new ArrayList<>();
		lista.add(2);
		lista.add(3);
		int retorno = this.calculoService.somar(lista);
		assertEquals(5, retorno);
	}

	@Test
	@DisplayName("TESTE UNITÁRIO DE LANÇAMENTO DE EXCEÇÃO/ERRO")
	void testarSoma2() {
		List<Integer> lista = new ArrayList<>();
		lista.add(null);
		lista.add(4);
		assertThrows(Exception.class, () -> {
			int retorno = this.calculoService.somar(lista);
		});
	}

	@Test
	@DisplayName("TESTANDO MAIOR NÚMERO ENTRE 4,5,8,1")
	void testarMaiorNumero() {
		List<Integer> lista = new ArrayList<>();
		lista.add(4);
		lista.add(5);
		lista.add(8);
		lista.add(1);
		int retorno = this.calculoService.buscarMaiorNumero(lista);
		assertEquals(8, retorno);
	}

	@Test
	@DisplayName("TESTE UNITÁRIO DE SOMA COM LISTA VAZIA")
	void testarSomaListaVazia() {
		List<Integer> lista = new ArrayList<>();
		int retorno = this.calculoService.somar(lista);
		assertEquals(0, retorno);
	}


	@Test
	@DisplayName("TESTE UNITÁRIO PARA O MÉTODO CALCULAR")
	void testarCalcular() {
		Entrada entrada = new Entrada(1, List.of(1, 2, 3, 4, 5));
		Saida saida = calculoService.calcular(entrada);
		assertEquals(15, saida.getSoma());
		assertEquals(5, saida.getMaiorNumeroLista());
	}

	@Test
	@DisplayName("TESTE UNITÁRIO PARA O MÉTODO CALCULAR COM NÚMEROS NEGATIVOS")
	void testarCalcularComNegativos() {
		Entrada entrada = new Entrada(1, List.of(-1, -2, -3, -4, -5));
		Saida saida = calculoService.calcular(entrada);
		assertEquals(-15, saida.getSoma());
		assertEquals(-1, saida.getMaiorNumeroLista());
	}


	@Test
	@DisplayName("TESTE UNITÁRIO PARA O MÉTODO CALCULAR COM NÚMEROS NEGATIVOS E POSITIVOS")
	void testarCalcularComNegativosEPositivos() {
		Entrada entrada = new Entrada(1, List.of(-10, 20, -30, 40));
		Saida saida = calculoService.calcular(entrada);
		assertEquals(20, saida.getSoma());
		assertEquals(40, saida.getMaiorNumeroLista());
	}
}
