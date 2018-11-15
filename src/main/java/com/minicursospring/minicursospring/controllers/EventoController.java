package com.minicursospring.minicursospring.controllers;

import com.minicursospring.minicursospring.models.Convidado;
import com.minicursospring.minicursospring.models.Evento;
import com.minicursospring.minicursospring.repository.ConvidadoRepository;
import com.minicursospring.minicursospring.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String form(){
        return "evento/formEvento";
    }

    @PostMapping(value = "/cadastrarEvento")
    public String form(@Valid Evento evento,  BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Preencher os campos corretamente!");
            return "redirect:/cadastrarEvento";
        }
        eventoRepository.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = eventoRepository.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @RequestMapping(value="/{codigo}", method=RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
        mv.addObject("convidado", convidados);
        return mv;
    }

    @PostMapping(value="/{codigo}")
    public String detalhesEventoPost(@PathVariable long codigo, @Valid Convidado convidado,
                    BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Preencher os campos corretamente!");
            return "redirect:/{codigo}";
        }
        Evento evento = eventoRepository.findByCodigo(codigo);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
        return "redirect:/{codigo}";
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        eventoRepository.delete(evento);
        return "redirect:/eventos";
    }

    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(long codigo){
        Convidado convidado = convidadoRepository.findById(codigo);
        convidadoRepository.delete(convidado);

        Evento evento = convidado.getEvento();
        long codigoLong = evento.getCodigo();
        String codigoEvento = "" + codigoLong;
        return "redirect:/" + codigoEvento ;
    }

}
