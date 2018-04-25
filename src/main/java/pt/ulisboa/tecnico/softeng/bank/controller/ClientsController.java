package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.dto.ClientDto;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

@Controller
@RequestMapping(value = "/banks/bank/{code}/clients")
public class ClientsController {
	private static Logger logger = LoggerFactory.getLogger(ClientsController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String clientForm(Model model, @PathVariable String code) {
		logger.info("clientForm");
		model.addAttribute("client", new ClientDto());
		model.addAttribute("clients", Bank.getBankByCode(code).getClients());
		return "clientView";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String clientSubmit(Model model, @ModelAttribute ClientDto clientdto, @PathVariable String code) {
		logger.info("clientSubmit name:{}, age:{}", clientdto.getName(), clientdto.getAge());

		try {
			new Client(Bank.getBankByCode(code), Bank.getBankByCode(code).getCode() + Bank.getBankByCode(code).getClients().size(), clientdto.getName(), clientdto.getAge());
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the client");
			model.addAttribute("client", clientdto);
			model.addAttribute("clients", Bank.getBankByCode(code).getClients());
			return "clientView";
		}

		return "redirect:/banks/bank/{code}/clients";
	}

}
