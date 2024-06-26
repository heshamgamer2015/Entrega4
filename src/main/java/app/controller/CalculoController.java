package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Entrada;
import app.entity.Saida;
import app.service.CalculoService;

@RestController
@RequestMapping("/api/calculo")
public class CalculoController {
	
	@Autowired
	private CalculoService calculoService;
	
	@PostMapping
	public ResponseEntity<Saida> calcular(@RequestBody Entrada entrada){
		try {
			Saida saida = this.calculoService.calcular(entrada);
			return new ResponseEntity<>(saida,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_GATEWAY);
		}
	}
	
	
	@GetMapping
	public ResponseEntity<List<Saida>> findAll(){
		try {
			List<Saida> lista = this.calculoService.findAll();
			return new ResponseEntity<>(lista,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
