package br.com.caelum.contas.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.contas.dao.UsuarioDAO;
import br.com.caelum.contas.modelo.Usuario;

@Controller
public class LoginController {

    @RequestMapping("/loginForm")
    public String loginForm() {
        return "formulario-login";
    }

    @RequestMapping("/efetuaLogin")
    public String efetuaLogin(final Usuario usuario, final HttpSession session) {
        if(new UsuarioDAO().existeUsuario(usuario)) {
            session.setAttribute("usuarioLogado", usuario);
            return "menu";
        }
        return "formulario-login";
    }

    @RequestMapping("/logout")
    public String logout(final HttpSession session) {
        session.invalidate();
        return "redirect:loginForm";
    }

}
