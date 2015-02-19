package corn.controllers;

import corn.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alvaro
 * @since 16/02/2015
 */
@RestController
@RequestMapping(value = "/rest/play")
public class PlayController {

	@Autowired
	private QuestionService questionService;

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> play(){
		Map<String, Object> map = new HashMap<String, Object>();

		//Buscar as perguntas salvas para iniciar o jogo
		map.put("questions", questionService.findAll());

		return map;
	}//fim play()

}//fim class PlayController

