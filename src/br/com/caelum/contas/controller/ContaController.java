package br.com.caelum.contas.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.contas.dao.ContaDAO;
import br.com.caelum.contas.modelo.Conta;

@Controller
public class ContaController {

    private final ContaDAO dao;

    @Autowired
    public ContaController(final ContaDAO dao) {
        this.dao = dao;
    }

    @RequestMapping
    public String form() {
        return "conta/formulario";
    }

    @RequestMapping("/adicionaConta")
    public String adiciona(@Valid final Conta conta, final BindingResult result) {
        if(result.hasErrors()) {
            return "conta/formulario";
        }
        dao.adiciona(conta);
        return "conta/conta-adicionada";
    }

    @RequestMapping("/listaContas")
    public ModelAndView listaContas() {
        final List<Conta> contas = dao.lista();
        final ModelAndView modelAndView = new ModelAndView("conta/listaContas");
        modelAndView.addObject("contas", contas);
        return modelAndView;
    }

    @RequestMapping("/removeConta")
    public String remove(final Conta conta) {
        dao.remove(conta);
        return "redirect:listaContas";
    }

    @RequestMapping("/mostraConta")
    public String mostra(final Long id, final Model model) {
        model.addAttribute("conta", dao.buscaPorId(id));
        return "conta/mostra";
    }

    @RequestMapping("/alteraConta")
    public String altera(final Conta conta) {
        dao.altera(conta);
        return "redirect:listaContas";
    }

    @RequestMapping("/pagaConta")
    public void paga(final Long id, final HttpServletResponse response) {
        dao.paga(id);
        response.setStatus(200);
    }

}
