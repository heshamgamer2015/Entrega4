package app.service;

import app.entity.Entrada;
import app.entity.Saida;
import app.repository.CalculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculoService {

	@Autowired
	private CalculoRepository calculoRepository;

	public Saida calcular(Entrada entrada) {
		Saida saida = new Saida();
		saida.setSoma(this.somar(entrada.getLista()));
		saida.setMaiorNumeroLista(this.buscarMaiorNumero(entrada.getLista()));
		return saida;
	}

	public List<Saida> findAll() {
		return this.calculoRepository.findAll();
	}

	public int somar(List<Integer> lista) {
		int soma = 0;
		for (int num : lista) {
			soma += num;
		}
		return soma;
	}

	public int buscarMaiorNumero(List<Integer> lista) {
		int maiorNumero = Integer.MIN_VALUE;
		for (int num : lista) {
			if (num > maiorNumero) {
				maiorNumero = num;
			}
		}
		return maiorNumero;
	}
}
