package app.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.entity.Entrada;
import app.entity.Saida;
import app.service.CalculoService;

@WebMvcTest(CalculoController.class)
public class CalculoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CalculoService calculoService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		List<Saida> lista = new ArrayList<>();
		lista.add(new Saida(1, 10, 3));
		lista.add(new Saida(2, 15, 5));

		when(this.calculoService.findAll()).thenReturn(lista);
	}

	@Test
	@DisplayName("TESTE DE INTEGRAÇÃO MOCANDO O REPOSITORY PARA O MÉTODO FINDALL")
	void cenario1() throws Exception {
		mockMvc.perform(get("/api/calculo"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	@DisplayName("Testar o método calcular com uma entrada válida")
	void testarCalcularSucesso() throws Exception {
		Entrada entrada = new Entrada(1, List.of(1, 2, 3, 4, 5));
		Saida saida = new Saida(1, 15, 5);

		when(calculoService.calcular(any(Entrada.class))).thenReturn(saida);

		mockMvc.perform(post("/api/calculo")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(entrada)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.soma").value(15))
				.andExpect(jsonPath("$.maiorNumeroLista").value(5));
	}

	@Test
	@DisplayName("Testar o método calcular quando ocorre uma exceção")
	void testarCalcularErro() throws Exception {
		Entrada entrada = new Entrada(1, List.of(-1, 2, 3));

		when(calculoService.calcular(any(Entrada.class))).thenThrow(new RuntimeException("Erro no cálculo"));

		mockMvc.perform(post("/api/calculo")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(entrada)))
				.andExpect(status().isBadGateway());
	}

	@Test
	@DisplayName("Testar o método calcular com lista vazia")
	void testarCalcularComListaVazia() throws Exception {
		Entrada entrada = new Entrada(1, List.of());

		when(calculoService.calcular(any(Entrada.class))).thenReturn(new Saida(1, 0, 0));

		mockMvc.perform(post("/api/calculo")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(entrada)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.soma").value(0))
				.andExpect(jsonPath("$.maiorNumeroLista").value(0));
	}

	@Test
	@DisplayName("Testar o método calcular com números negativos e positivos")
	void testarCalcularComNegativosEPositivos() throws Exception {
		Entrada entrada = new Entrada(1, List.of(-10, 20, -30, 40));
		Saida saida = new Saida(1, 20, 40);

		when(calculoService.calcular(any(Entrada.class))).thenReturn(saida);

		mockMvc.perform(post("/api/calculo")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(entrada)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.soma").value(20))
				.andExpect(jsonPath("$.maiorNumeroLista").value(40));
	}

}
